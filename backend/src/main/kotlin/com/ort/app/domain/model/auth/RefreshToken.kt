package com.ort.app.domain.model.auth

import java.time.LocalDateTime

data class RefreshToken(
    val id: Int,
    val playerId: Int,
    val tokenHash: String,
    val expiresAt: LocalDateTime,
    val revoked: Boolean,
) {
    fun isValid(now: LocalDateTime): Boolean = !revoked && now.isBefore(expiresAt)
}
