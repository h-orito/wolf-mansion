package com.ort.app.domain.model.auth

import java.time.LocalDateTime

interface RefreshTokenRepository {

    fun findByTokenHash(tokenHash: String): RefreshToken?

    fun register(playerId: Int, tokenHash: String, expiresAt: LocalDateTime): RefreshToken

    fun revoke(id: Int)

    fun revokeAllByPlayerId(playerId: Int)

    /** 有効期限切れ または revoked 済みのトークンをまとめて削除 (定期クリーンアップ用)。 */
    fun deleteExpiredOrRevoked(now: LocalDateTime)
}
