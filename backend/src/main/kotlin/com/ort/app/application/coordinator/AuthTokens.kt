package com.ort.app.application.coordinator

import com.ort.app.fw.security.jwt.JwtPrincipal
import java.time.Duration

/**
 * 認証成功時に発行するトークン一式 (Controller が Cookie に載せる)。
 * [refreshToken] は生の値 (Cookie 用)。DB にはハッシュのみ保存済み。
 */
data class AuthTokens(
    val principal: JwtPrincipal,
    val accessToken: String,
    val accessTokenMaxAge: Duration,
    val refreshToken: String,
    val refreshTokenMaxAge: Duration,
)
