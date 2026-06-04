package com.ort.app.fw.security.jwt

import jakarta.servlet.ServletContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.time.Duration

/**
 * 認証 Cookie (access / refresh) の組み立て。
 * - access: `Path=/` (frontend と backend の両方へ送る)
 * - refresh: `Path=<contextPath>/api/v1/auth` (auth エンドポイントにのみ送る)
 * いずれも HttpOnly / SameSite=Lax。Secure は profile 連動 (`jwt.cookie-secure`)。
 */
@Component
class AuthCookieFactory(
    private val servletContext: ServletContext,
    @Value("\${jwt.cookie-secure:false}") private val secure: Boolean,
) {
    fun accessTokenCookie(
        value: String,
        maxAge: Duration,
    ): ResponseCookie = build(ACCESS_TOKEN, value, "/", maxAge)

    fun refreshTokenCookie(
        value: String,
        maxAge: Duration,
    ): ResponseCookie = build(REFRESH_TOKEN, value, refreshTokenPath(), maxAge)

    fun clearAccessTokenCookie(): ResponseCookie = build(ACCESS_TOKEN, "", "/", Duration.ZERO)

    fun clearRefreshTokenCookie(): ResponseCookie = build(REFRESH_TOKEN, "", refreshTokenPath(), Duration.ZERO)

    /**
     * 連続登録防止 (cooldown) Cookie。値 `true` が存在する間は signup を拒否する。
     * signup エンドポイントへ届けばよいので Path は contextPath に絞る。
     */
    fun idRegisterCookie(): ResponseCookie = build(ID_REGISTER, "true", servletContext.contextPath, ID_REGISTER_MAX_AGE)

    private fun refreshTokenPath(): String = "${servletContext.contextPath}/api/v1/auth"

    private fun build(
        name: String,
        value: String,
        path: String,
        maxAge: Duration,
    ): ResponseCookie =
        ResponseCookie
            .from(name, value)
            .httpOnly(true)
            .secure(secure)
            .path(path)
            .sameSite("Lax")
            .maxAge(maxAge)
            .build()

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val ID_REGISTER = "id_register"
        private val ID_REGISTER_MAX_AGE = Duration.ofMinutes(30)
    }
}
