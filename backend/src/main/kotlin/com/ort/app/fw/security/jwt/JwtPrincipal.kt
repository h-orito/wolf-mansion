package com.ort.app.fw.security.jwt

/**
 * JWT から復元される認証済みプリンシパル。SecurityContext に格納され、`@AuthenticationPrincipal` で取得できる。
 */
data class JwtPrincipal(
    val playerId: Int,
    val name: String,
    val authorities: List<String>,
)
