package com.ort.app.domain.model.auth

import java.time.LocalDateTime

/**
 * リフレッシュトークン。値そのものは保持せず、SHA-256 ハッシュ ([tokenHash]) のみ DB 管理する。
 * 使い捨て rotation: refresh のたびに [usedDatetime] を立てて旧トークンを無効化し、新トークンを発行する。
 */
data class RefreshToken(
    val id: Int,
    val playerId: Int,
    val tokenHash: String,
    val issuedDatetime: LocalDateTime,
    val expiresDatetime: LocalDateTime,
    val usedDatetime: LocalDateTime?,
    val revokedDatetime: LocalDateTime?,
) {
    /** rotation 済 (= 一度使われた)。使用済みトークンの再提示は漏洩疑い。 */
    val isUsed: Boolean get() = usedDatetime != null

    val isRevoked: Boolean get() = revokedDatetime != null

    fun isExpired(now: LocalDateTime): Boolean = !now.isBefore(expiresDatetime)

    /** refresh に使える状態か (未使用・未失効・未期限切れ)。 */
    fun isUsable(now: LocalDateTime): Boolean = !isUsed && !isRevoked && !isExpired(now)
}
