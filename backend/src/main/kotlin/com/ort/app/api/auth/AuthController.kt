package com.ort.app.api.auth

import com.ort.app.api.auth.request.LoginRequest
import com.ort.app.api.auth.request.PasswordChangeRequest
import com.ort.app.api.auth.request.SignupRequest
import com.ort.app.api.auth.response.MeResponse
import com.ort.app.application.coordinator.AuthCoordinator
import com.ort.app.application.coordinator.AuthTokens
import com.ort.app.application.service.PlayerService
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.app.fw.security.jwt.AuthCookieFactory
import com.ort.app.fw.security.jwt.JwtPrincipal
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
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
    private val playerService: PlayerService,
) {
    /** principal からプレイヤーを引いて MeResponse を組み立てる (canCreateVillage 込み)。 */
    private fun toMeResponse(principal: JwtPrincipal): MeResponse = MeResponse(principal, playerService.findPlayer(principal.name))

    /** ID / パスワードで認証し、access + refresh Cookie をセットする。失敗は 401、レート制限超過は 429。 */
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated request: LoginRequest,
        httpRequest: HttpServletRequest,
        response: HttpServletResponse,
    ): MeResponse {
        val tokens = authCoordinator.login(request.userId!!, request.password!!, httpRequest.getIpAddress())
        writeAuthCookies(response, tokens)
        return toMeResponse(tokens.principal)
    }

    /**
     * 新規登録 + 自動ログイン。成功で access + refresh + id_register(cooldown) Cookie をセットする。
     * 重複 ID は 400、連続登録 (cooldown) は 429。
     */
    @PostMapping("/signup")
    fun signup(
        @RequestBody @Validated request: SignupRequest,
        @CookieValue(name = AuthCookieFactory.ID_REGISTER, required = false) recentlyRegistered: Boolean?,
        response: HttpServletResponse,
    ): MeResponse {
        val tokens = authCoordinator.signup(request.userId!!, request.password!!, recentlyRegistered == true)
        writeAuthCookies(response, tokens)
        response.addHeader(HttpHeaders.SET_COOKIE, authCookieFactory.idRegisterCookie().toString())
        return toMeResponse(tokens.principal)
    }

    /** ログイン中ユーザー自身のパスワード変更 (認証必須)。確認用不一致は 400。 */
    @PostMapping("/password")
    fun changePassword(
        @RequestBody @Validated request: PasswordChangeRequest,
        @AuthenticationPrincipal principal: JwtPrincipal?,
    ): ResponseEntity<Void> {
        val authenticated = principal ?: throw WolfMansionAuthException("認証が必要です")
        if (request.password != request.confirmPassword) {
            throw WolfMansionBusinessException("確認用パスワードが一致しません")
        }
        authCoordinator.changePassword(authenticated.name, request.password!!)
        return ResponseEntity.noContent().build()
    }

    /** refresh Cookie から新しい access + refresh を発行する (使い捨て rotation)。 */
    @PostMapping("/refresh")
    fun refresh(
        @CookieValue(name = AuthCookieFactory.REFRESH_TOKEN, required = false) refreshToken: String?,
        response: HttpServletResponse,
    ): ResponseEntity<Any> {
        if (refreshToken.isNullOrBlank()) {
            val problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "リフレッシュトークンがありません")
            problem.setProperty("error", "authentication_failed")
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem)
        }
        val tokens = authCoordinator.refresh(refreshToken)
        writeAuthCookies(response, tokens)
        return ResponseEntity.ok(toMeResponse(tokens.principal))
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
        return toMeResponse(authenticated)
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
