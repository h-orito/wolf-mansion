package com.ort.app.domain.service.auth

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDateTime

/**
 * ログイン試行のレート制限。ブルートフォース対策として 2 軸で時間窓内の試行数を制限する。
 * - (アカウント, IP) の組: 攻撃者は自分の IP からしか締め出せず、本人は別の IP からログインできる
 *   (アカウント単体で数えると、ログイン名さえ分かれば誰でも本人を締め出せてしまう)
 * - IP: 1 つの IP から多数のアカウントを試す攻撃を抑える
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
) {
    // 呼び出し側のトランザクションとは独立にコミットし、並行リクエストから見えるようにする
    private val requiresNew =
        TransactionTemplate(transactionManager).apply {
            propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
        }

    /**
     * 試行を 1 件記録し、記録後の試行数がいずれかの軸で上限を超えていれば 429 にする。資格情報を検証する前に呼ぶこと。
     * 記録は呼び出し側がロールバックしても残る (失敗した試行として数える)。成功したら [reset] で消す。
     */
    fun acquire(
        loginName: String,
        ipAddress: String,
        now: LocalDateTime,
    ) {
        val since = now.minusMinutes(windowMinutes)
        requiresNew.executeWithoutResult {
            loginAttemptRepository.recordAttempt(loginName, ipAddress, now)
            loginAttemptRepository.deleteOlderThan(since)
        }
        // 記録のコミット後に新しいトランザクションで数え、並行リクエストの記録も確実に含める
        val (accountAttempts, ipAttempts) =
            requiresNew.execute {
                loginAttemptRepository.countByLoginNameAndIpAddress(loginName, ipAddress, since) to
                    loginAttemptRepository.countByIpAddress(ipAddress, since)
            }!!
        if (accountAttempts <= maxPerAccount && ipAttempts <= maxPerIp) return

        // 上限を初めて超えた試行でだけ管理者へ通知する (攻撃検知)。以降の試行は件数が上限 + 1 を超えるので通知しない
        if (accountAttempts == maxPerAccount + 1) {
            notifyMaster(
                "⚠️ ログイン失敗がアカウント単位の閾値に到達しました。\n" +
                    "アカウント: $loginName\n" +
                    "IP: $ipAddress\n" +
                    "試行: ${accountAttempts}回 / 直近${windowMinutes}分",
            )
        }
        if (ipAttempts == maxPerIp + 1) {
            notifyMaster(
                "⚠️ ログイン失敗がIP単位の閾値に到達しました。\n" +
                    "IP: $ipAddress\n" +
                    "試行: ${ipAttempts}回 / 直近${windowMinutes}分\n" +
                    "直近アカウント: $loginName",
            )
        }
        throw WolfMansionTooManyRequestsException(TOO_MANY_MESSAGE)
    }

    /** ログイン成功時に当該アカウント・当該 IP の試行履歴 (今回の試行を含む) をリセットする。 */
    fun reset(
        loginName: String,
        ipAddress: String,
    ) {
        loginAttemptRepository.deleteByLoginNameAndIpAddress(loginName, ipAddress)
    }

    private fun notifyMaster(message: String) {
        // ベストエフォート (DiscordRepository 側で例外は握り潰される)。通知失敗でログインを壊さない
        discordRepository.postToMaster(message)
    }

    companion object {
        private const val TOO_MANY_MESSAGE = "ログイン試行が多すぎます。しばらくしてから再度お試しください。"
    }
}
