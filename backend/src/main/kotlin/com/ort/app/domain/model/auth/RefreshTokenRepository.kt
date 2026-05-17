package com.ort.app.domain.model.auth

import java.time.LocalDateTime

interface RefreshTokenRepository {

    fun findByTokenHash(tokenHash: String): RefreshToken?

    fun register(playerId: Int, tokenHash: String, expiresAt: LocalDateTime): RefreshToken

    fun revoke(id: Int)

    fun revokeAllByPlayerId(playerId: Int)

    fun deleteExpired(now: LocalDateTime)
}
