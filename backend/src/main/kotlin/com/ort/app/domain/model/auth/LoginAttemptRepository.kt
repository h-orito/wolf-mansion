package com.ort.app.domain.model.auth

import java.time.LocalDateTime

/**
 * ログイン試行の記録。ブルートフォース対策のレート制限に用いる。
 * 試行は資格情報を検証する前に記録し、ログイン成功時に削除するため、残るのは「失敗した (または検証中の) 試行」になる。
 * カウントは時間窓 (`since` 以降) で絞る。掃除はオポチュニスティック (記録時に窓外を削除) で行うため、
 * テーブルは常に「直近窓分の試行」に有界となる。
 */
interface LoginAttemptRepository {
    /** `since` 以降の、当該ログイン ID・当該 IP の組の試行回数。 */
    fun countByLoginNameAndIpAddress(
        loginName: String,
        ipAddress: String,
        since: LocalDateTime,
    ): Int

    /** `since` 以降の、当該 IP の試行回数。 */
    fun countByIpAddress(
        ipAddress: String,
        since: LocalDateTime,
    ): Int

    /** 試行を 1 件記録する。 */
    fun recordAttempt(
        loginName: String,
        ipAddress: String,
        attemptAt: LocalDateTime,
    )

    /** 当該ログイン ID・当該 IP の組の試行履歴を全削除する (ログイン成功時のリセット用)。 */
    fun deleteByLoginNameAndIpAddress(
        loginName: String,
        ipAddress: String,
    )

    /** `threshold` より古い試行行を全削除する (肥大化抑制)。 */
    fun deleteOlderThan(threshold: LocalDateTime)
}
