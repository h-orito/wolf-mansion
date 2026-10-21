package com.ort.app.domain.service.auth

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.transaction.support.TransactionTemplate
import java.net.Inet6Address
import java.net.InetAddress
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

/**
 * ログイン試行のレート制限。ブルートフォース対策として 2 軸で時間窓内の試行数を制限する。
 * - (アカウント, IP) の組: 攻撃者は自分の IP からしか締め出せず、本人は別の IP からログインできる
 *   (アカウント単体で数えると、ログイン名さえ分かれば誰でも本人を締め出せてしまう)
 * - IP: 1 つの IP から多数のアカウントを試す攻撃を抑える
 *
 * IPv6 は 1 台でも大量のアドレスを持てるため、/64 単位にまとめて数える。
 * アカウント単体の試行数は締め出しの原因になるので制限には使わず、多いときに管理者へ通知するだけにする。
 *
 * 共有ストア (DB) でカウントするため stateless・複数インスタンスでも機能する。
 * 同時に大量の試行が来ても上限を超えないよう、試行を **先に記録してコミットしてから** 数える
 * (数えてから記録する方式だと、並行リクエストが全員記録前の件数を見て通ってしまう)。
 */
@Component
class LoginRateLimiter(
    private val loginAttemptRepository: LoginAttemptRepository,
    private val discordRepository: DiscordRepository,
    transactionManager: PlatformTransactionManager,
    @Value("\${login-rate-limit.window-minutes:15}") private val windowMinutes: Long,
    @Value("\${login-rate-limit.max-per-account:5}") private val maxPerAccount: Int,
    @Value("\${login-rate-limit.max-per-ip:30}") private val maxPerIp: Int,
    @Value("\${login-rate-limit.alert-per-account:50}") private val alertPerAccount: Int,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    // 掃除は全件スキャンの DELETE になるので、毎回ではなく間隔をあけて行う (このインスタンス内で)
    private val lastSweptAt = AtomicReference<LocalDateTime>(LocalDateTime.MIN)

    // 同時送信で同じ通知が大量に飛ばないよう、通知の種類とキーごとに時間窓内 1 回へ間引く (このインスタンス内で)
    private val lastNotifiedAt = ConcurrentHashMap<String, LocalDateTime>()

    // 呼び出し側とは独立にコミットし、並行リクエストから見えるようにする
    private val requiresNew =
        TransactionTemplate(transactionManager).apply {
            propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
        }

    private data class Counts(
        val pair: Int,
        val ip: Int,
        val account: Int,
    )

    /**
     * 試行を 1 件記録し、記録後の試行数がいずれかの軸で上限を超えていれば 429 にする。資格情報を検証する前に呼ぶこと。
     * 記録は呼び出し側がロールバックしても残る (失敗した試行として数える)。成功したら [reset] で消す。
     *
     * トランザクションの外から呼ぶこと。トランザクション内で呼ぶと 1 リクエストが接続を 2 本使い、
     * ログインが殺到すると接続プールを使い切って止まるため、その場合は例外にする。
     */
    fun acquire(
        loginName: String,
        ipAddress: String,
        now: LocalDateTime,
    ) {
        check(!TransactionSynchronizationManager.isActualTransactionActive()) {
            "LoginRateLimiter.acquire はトランザクションの外から呼ぶこと"
        }
        val ipKey = toRateLimitKey(ipAddress)
        val since = now.minusMinutes(windowMinutes)

        // 既に上限に達していれば記録せずに弾く (ブロック中に行が増え続けたり、ブロックが延び続けたりしないように)
        val before = count(loginName, ipKey, since)
        if (before.pair >= maxPerAccount || before.ip >= maxPerIp) throw tooManyRequests()

        requiresNew.executeWithoutResult { loginAttemptRepository.recordAttempt(loginName, ipKey, now) }
        sweep(now, since)
        // 記録のコミット後に新しいトランザクションで数え、並行リクエストの記録も確実に含める
        val after = count(loginName, ipKey, since)

        // 上限・通知の閾値に達した試行で通知する (以降の試行は事前チェックで弾かれるので通知しない)。
        // 同時送信では複数のリクエストが閾値に達したと判定しうるので、通知が重複することはある
        if (before.pair < maxPerAccount && after.pair >= maxPerAccount) {
            notifyMaster(
                "pair:$loginName:$ipKey",
                now,
                "⚠️ 同じIPから同じアカウントへのログイン試行が上限に達しました。\n" +
                    "アカウント: $loginName\n" +
                    "IP: $ipKey\n" +
                    "試行: ${after.pair}回 / 直近${windowMinutes}分",
            )
        }
        if (before.ip < maxPerIp && after.ip >= maxPerIp) {
            notifyMaster(
                "ip:$ipKey",
                now,
                "⚠️ 同じIPからのログイン試行が上限に達しました。\n" +
                    "IP: $ipKey\n" +
                    "試行: ${after.ip}回 / 直近${windowMinutes}分\n" +
                    "直近アカウント: $loginName",
            )
        }
        if (before.account < alertPerAccount && after.account >= alertPerAccount) {
            notifyMaster(
                "account:$loginName",
                now,
                "⚠️ 1つのアカウントへのログイン試行が多数のIPから来ています (ブロックはしていません)。\n" +
                    "アカウント: $loginName\n" +
                    "試行: ${after.account}回 / 直近${windowMinutes}分",
            )
        }
        if (after.pair > maxPerAccount || after.ip > maxPerIp) throw tooManyRequests()
    }

    /**
     * ログイン成功時に当該アカウント・当該 IP の試行履歴 (今回の試行を含む) をリセットする。
     * 独自のトランザクションで行い、ロック競合などで失敗してもログインは成功させる (残った記録は時間窓で消える)。
     */
    fun reset(
        loginName: String,
        ipAddress: String,
    ) {
        try {
            requiresNew.executeWithoutResult {
                loginAttemptRepository.deleteByLoginNameAndIpAddress(loginName, toRateLimitKey(ipAddress))
            }
        } catch (e: Exception) {
            logger.warn("ログイン試行のリセットに失敗しました", e)
        }
    }

    private fun count(
        loginName: String,
        ipKey: String,
        since: LocalDateTime,
    ): Counts =
        requiresNew.execute {
            Counts(
                pair = loginAttemptRepository.countByLoginNameAndIpAddress(loginName, ipKey, since),
                ip = loginAttemptRepository.countByIpAddress(ipKey, since),
                account = loginAttemptRepository.countByLoginName(loginName, since),
            )
        }!!

    // 窓外の古い行の掃除。記録とは別トランザクションにし、ロック競合などで失敗してもログインは止めない
    private fun sweep(
        now: LocalDateTime,
        since: LocalDateTime,
    ) {
        val last = lastSweptAt.get()
        if (last.isAfter(now.minusMinutes(SWEEP_INTERVAL_MINUTES)) || !lastSweptAt.compareAndSet(last, now)) return
        try {
            requiresNew.executeWithoutResult { loginAttemptRepository.deleteOlderThan(since) }
        } catch (e: Exception) {
            logger.warn("ログイン試行の掃除に失敗しました", e)
        }
    }

    private fun notifyMaster(
        key: String,
        now: LocalDateTime,
        message: String,
    ) {
        val notifiable = now.minusMinutes(windowMinutes)
        val last = lastNotifiedAt[key]
        if (last != null && last.isAfter(notifiable)) return
        if (lastNotifiedAt.put(key, now) != last) return // 同時に別のリクエストが通知した
        lastNotifiedAt.entries.removeIf { it.value.isBefore(notifiable) }
        // ベストエフォート (DiscordRepository 側で例外は握り潰される)。通知失敗でログインを壊さない
        discordRepository.postToMaster(message)
    }

    private fun tooManyRequests() = WolfMansionTooManyRequestsException(TOO_MANY_MESSAGE)

    companion object {
        private const val SWEEP_INTERVAL_MINUTES = 1L
        private const val TOO_MANY_MESSAGE = "ログイン試行が多すぎます。しばらくしてから再度お試しください。"
        private val IPV6_LITERAL = Regex("^[0-9A-Fa-f:.]+$")

        /** レート制限のキーにする IP。IPv6 は /64 にまとめる (DNS を引かないよう、リテラルとして妥当なものだけ解釈する)。 */
        fun toRateLimitKey(ipAddress: String): String {
            if (!ipAddress.contains(':') || !IPV6_LITERAL.matches(ipAddress)) return ipAddress
            val address = runCatching { InetAddress.getByName(ipAddress) }.getOrNull() as? Inet6Address ?: return ipAddress
            val prefix =
                address.address.take(8).chunked(2).joinToString(":") { (hi, lo) ->
                    Integer.toHexString(((hi.toInt() and 0xff) shl 8) or (lo.toInt() and 0xff))
                }
            return "$prefix::/64"
        }
    }
}
