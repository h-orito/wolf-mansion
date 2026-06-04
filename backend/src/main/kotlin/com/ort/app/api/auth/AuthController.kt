package com.ort.app.api.auth

import com.ort.app.api.auth.request.LoginRequest
import com.ort.app.api.auth.response.MeResponse
import com.ort.app.application.coordinator.AuthCoordinator
import com.ort.app.application.coordinator.AuthTokens
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.security.jwt.AuthCookieFactory
import com.ort.app.fw.security.jwt.JwtPrincipal
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authCoordinator: AuthCoordinator,
    private val authCookieFactory: AuthCookieFactory,
) {
    /** ID / パスワードで認証し、access + refresh Cookie をセットする。失敗は 401。 */
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated request: LoginRequest,
        response: HttpServletResponse,
    ): MeResponse {
        val tokens = authCoordinator.login(request.userId!!, request.password!!)
        writeAuthCookies(response, tokens)
        return MeResponse(tokens.principal)
    }

    /** refresh Cookie から新しい access + refresh を発行する (使い捨て rotation)。 */
    @PostMapping("/refresh")
    fun refresh(
        @CookieValue(name = AuthCookieFactory.REFRESH_TOKEN, required = false) refreshToken: String?,
        response: HttpServletResponse,
    ): MeResponse {
        if (refreshToken.isNullOrBlank()) throw WolfMansionAuthException("リフレッシュトークンがありません")
        val tokens = authCoordinator.refresh(refreshToken)
        writeAuthCookies(response, tokens)
        return MeResponse(tokens.principal)
    }

    /** 両 Cookie を消去し、DB 側の refresh token も失効させる。 */
    @PostMapping("/logout")
    fun logout(
        @CookieValue(name = AuthCookieFactory.REFRESH_TOKEN, required = false) refreshToken: String?,
        response: HttpServletResponse,
    ): ResponseEntity<Void> {
        authCoordinator.logout(refreshToken)
        response.addHeader(HttpHeaders.SET_COOKIE, authCookieFactory.clearAccessTokenCookie().toString())
        response.addHeader(HttpHeaders.SET_COOKIE, authCookieFactory.clearRefreshTokenCookie().toString())
        return ResponseEntity.noContent().build()
    }

    /** 現在のログインプレイヤー情報。未認証は SecurityConfig により 401 (ProblemDetail)。 */
    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal principal: JwtPrincipal?,
    ): MeResponse {
        val authenticated = principal ?: throw WolfMansionAuthException("認証が必要です")
        return MeResponse(authenticated)
    }

    private fun writeAuthCookies(
        response: HttpServletResponse,
        tokens: AuthTokens,
    ) {
        response.addHeader(
            HttpHeaders.SET_COOKIE,
            authCookieFactory.accessTokenCookie(tokens.accessToken, tokens.accessTokenMaxAge).toString(),
        )
        response.addHeader(
            HttpHeaders.SET_COOKIE,
            authCookieFactory.refreshTokenCookie(tokens.refreshToken, tokens.refreshTokenMaxAge).toString(),
        )
    }
}
