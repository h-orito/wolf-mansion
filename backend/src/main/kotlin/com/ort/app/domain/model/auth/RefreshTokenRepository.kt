package com.ort.app.domain.model.auth

import java.time.LocalDateTime

interface RefreshTokenRepository {
    fun insert(
        playerId: Int,
        tokenHash: String,
        issuedDatetime: LocalDateTime,
        expiresDatetime: LocalDateTime,
    ): RefreshToken

    fun findByHash(tokenHash: String): RefreshToken?

    /**
     * rotation: 未使用のものだけを使用済みにする (条件付き更新)。
     * 実際に未使用→使用済みへ遷移できたら true。並行リクエストに先を越されていたら false。
     * (findByHash → markUsed 間の TOCTOU 競合で二重消費しないためのアトミック更新)
     */
    fun markUsed(
        id: Int,
        usedDatetime: LocalDateTime,
    ): Boolean

    /** 失効させる (ログアウト等)。 */
    fun revoke(
        id: Int,
        revokedDatetime: LocalDateTime,
    )

    /** 漏洩疑い時: 当該プレイヤーの未失効トークンを全て失効させる。 */
    fun revokeAllByPlayer(
        playerId: Int,
        revokedDatetime: LocalDateTime,
    )

    /**
     * 当該プレイヤーの期限切れトークンを物理削除する (テーブル肥大化の抑制)。
     * 使用済みでも未期限切れのものは漏洩検知に必要なため残す (期限切れは isUsable で弾かれるので削除して安全)。
     */
    fun deleteExpiredByPlayer(
        playerId: Int,
        now: LocalDateTime,
    )
}
