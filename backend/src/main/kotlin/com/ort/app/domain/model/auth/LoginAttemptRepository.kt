package com.ort.app.domain.model.auth

import java.time.LocalDateTime

/**
 * ログイン失敗試行の記録。ブルートフォース対策のレート制限に用いる。
 * カウントは時間窓 (`since` 以降) で絞る。掃除はオポチュニスティック (記録時に窓外を削除) で行うため、
 * テーブルは常に「直近窓分の失敗」に有界となる。
 */
interface LoginAttemptRepository {
    /** `since` 以降の、当該ログイン ID の失敗回数。 */
    fun countByLoginName(
        loginName: String,
        since: LocalDateTime,
    ): Int

    /** `since` 以降の、当該 IP の失敗回数。 */
    fun countByIpAddress(
        ipAddress: String,
        since: LocalDateTime,
    ): Int

    /** 失敗を 1 件記録する。 */
    fun recordFailure(
        loginName: String,
        ipAddress: String,
        attemptAt: LocalDateTime,
    )

    /** 当該ログイン ID の失敗履歴を全削除する (ログイン成功時のリセット用)。 */
    fun deleteByLoginName(loginName: String)

    /** `threshold` より古い失敗行を全削除する (肥大化抑制)。 */
    fun deleteOlderThan(threshold: LocalDateTime)
}
