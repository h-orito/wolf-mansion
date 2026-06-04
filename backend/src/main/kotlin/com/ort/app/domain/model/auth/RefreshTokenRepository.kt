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

    /** rotation: 使用済みとしてマーク (再利用不可にする)。 */
    fun markUsed(
        id: Int,
        usedDatetime: LocalDateTime,
    )

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
}
