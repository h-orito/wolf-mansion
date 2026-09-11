package com.ort.app.domain.service.auth

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * ログイン失敗のレート制限。ブルートフォース対策として 2 軸 (アカウント / IP) で時間窓内の失敗数を制限する。
 * - 共有ストア (DB) でカウントするため stateless・複数インスタンスでも機能する
 * - 閾値に到達した瞬間に管理者へ Discord 通知する (攻撃検知)。到達後の試行は [assertNotBlocked] で弾かれ
 *   [recordFailure] に到達しないため、通知はキー×窓ごとに概ね 1 回で止まる
 */
@Component
class LoginRateLimiter(
    private val loginAttemptRepository: LoginAttemptRepository,
    private val discordRepository: DiscordRepository,
    @Value("\${login-rate-limit.window-minutes:15}") private val windowMinutes: Long,
    @Value("\${login-rate-limit.max-per-account:5}") private val maxPerAccount: Int,
    @Value("\${login-rate-limit.max-per-ip:30}") private val maxPerIp: Int,
) {
    /** いずれかの軸が閾値以上なら 429。資格情報を検証する前に呼ぶこと。 */
    fun assertNotBlocked(
        loginName: String,
        ipAddress: String,
        now: LocalDateTime,
    ) {
        val since = now.minusMinutes(windowMinutes)
        if (loginAttemptRepository.countByLoginName(loginName, since) >= maxPerAccount ||
            loginAttemptRepository.countByIpAddress(ipAddress, since) >= maxPerIp
        ) {
            throw WolfMansionTooManyRequestsException(TOO_MANY_MESSAGE)
        }
    }

    /**
     * 失敗を 1 件記録し、肥大化抑制のため窓外の古い行を掃除する。
     * 記録後にいずれかの軸が閾値に到達していれば管理者へ通知する。
     *
     * **トランザクション契約**: 記録 (insert) と掃除 (deleteOlderThan) は呼び出し側のトランザクションに参加する。
     * この後に呼び出し側が認証失敗例外を投げてもこれらをコミットさせる必要があるため、呼び出し側 (
     * [com.ort.app.application.coordinator.AuthCoordinator.login]) は当該例外を `noRollbackFor` に指定すること。
     * これを怠ると記録・掃除ごとロールバックされ、レート制限が機能しなくなる。
     */
    fun recordFailure(
        loginName: String,
        ipAddress: String,
        now: LocalDateTime,
    ) {
        loginAttemptRepository.recordFailure(loginName, ipAddress, now)
        loginAttemptRepository.deleteOlderThan(now.minusMinutes(windowMinutes))

        val since = now.minusMinutes(windowMinutes)
        val accountFailures = loginAttemptRepository.countByLoginName(loginName, since)
        if (accountFailures >= maxPerAccount) {
            notifyMaster(
                "⚠️ ログイン失敗がアカウント単位の閾値に到達しました。\n" +
                    "アカウント: $loginName\n" +
                    "失敗: ${accountFailures}回 / 直近${windowMinutes}分\n" +
                    "直近IP: $ipAddress",
            )
        }
        val ipFailures = loginAttemptRepository.countByIpAddress(ipAddress, since)
        if (ipFailures >= maxPerIp) {
            notifyMaster(
                "⚠️ ログイン失敗がIP単位の閾値に到達しました。\n" +
                    "IP: $ipAddress\n" +
                    "失敗: ${ipFailures}回 / 直近${windowMinutes}分\n" +
                    "直近アカウント: $loginName",
            )
        }
    }

    /** ログイン成功時に当該アカウントの失敗履歴をリセットする。 */
    fun reset(loginName: String) {
        loginAttemptRepository.deleteByLoginName(loginName)
    }

    private fun notifyMaster(message: String) {
        // ベストエフォート (DiscordRepository 側で例外は握り潰される)。通知失敗でログインを壊さない
        discordRepository.postToMaster(message)
    }

    companion object {
        private const val TOO_MANY_MESSAGE = "ログイン試行が多すぎます。しばらくしてから再度お試しください。"
    }
}
