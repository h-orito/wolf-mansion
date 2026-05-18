package com.ort.app.api.auth

import com.ort.app.application.service.PlayerService
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.app.fw.security.JwtTokenService
import com.ort.app.fw.security.UserInfoService
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "auth", description = "認証 (JWT Cookie)")
class AuthController(
    private val jwtTokenService: JwtTokenService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userInfoService: UserInfoService,
    private val playerService: PlayerService,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${server.servlet.context-path:}") private val contextPath: String,
    @Value("\${app.cookie.secure:true}") private val cookieSecure: Boolean,
) {
    companion object {
        const val ACCESS_COOKIE = "access_token"
        const val REFRESH_COOKIE = "refresh_token"
        const val AUTH_PATH = "/api/v1/auth"
    }

    @Schema(description = "ログインリクエスト")
    data class LoginBody(
        @field:NotBlank
        @field:Schema(description = "プレイヤー名", example = "alice")
        val userId: String = "",
        @field:NotBlank
        @field:Schema(description = "パスワード", example = "********")
        val password: String = "",
    )

    @Schema(description = "現在ユーザ情報。未認証時は user=null")
    data class MeResponse(val user: UserPayload?) {
        @Schema(description = "ユーザペイロード")
        data class UserPayload(
            @field:Schema(description = "プレイヤー名") val userId: String,
            @field:Schema(description = "権限コード", example = "プレイヤー") val authority: String,
        )
    }

    @PostMapping("/login")
    @Transactional
    @Operation(summary = "ログイン", description = "プレイヤー名とパスワードで認証し、access_token / refresh_token Cookie を発行する")
    fun login(@Valid @RequestBody body: LoginBody, response: HttpServletResponse): ResponseEntity<MeResponse> {
        val userInfo = try {
            userInfoService.loadUserByUsername(body.userId)
        } catch (_: Exception) {
            throw BadCredentialsException("invalid credentials")
        } ?: throw BadCredentialsException("invalid credentials")
        if (!passwordEncoder.matches(body.password, userInfo.password)) {
            throw BadCredentialsException("invalid credentials")
        }
        val player = playerService.findPlayer(body.userId)
            ?: throw BadCredentialsException("invalid credentials")
        issueCookies(response, body.userId, player.authority.toCdef(), player.id)
        return ResponseEntity.ok(MeResponse(MeResponse.UserPayload(body.userId, player.authority.toCdef().code())))
    }

    @PostMapping("/refresh")
    @Transactional
    @Operation(summary = "トークン更新", description = "refresh_token Cookie を rotation し新しい access_token / refresh_token を発行する")
    fun refresh(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<MeResponse> {
        val rawRefresh = readCookie(request, REFRESH_COOKIE)
            ?: throw BadCredentialsException("missing refresh token")
        val tokenHash = jwtTokenService.hashRefreshToken(rawRefresh)
        val stored = refreshTokenRepository.findByTokenHash(tokenHash)
            ?: throw BadCredentialsException("invalid refresh token")
        if (!stored.isValid(LocalDateTime.now())) {
            throw BadCredentialsException("expired or revoked refresh token")
        }
        val player = playerService.findPlayer(stored.playerId)
        // rotation: revoke old + issue new (same transaction)
        refreshTokenRepository.revoke(stored.id)
        issueCookies(response, player.name, player.authority.toCdef(), player.id)
        return ResponseEntity.ok(MeResponse(MeResponse.UserPayload(player.name, player.authority.toCdef().code())))
    }

    @PostMapping("/logout")
    @Transactional
    @Operation(summary = "ログアウト", description = "refresh_token を revoke し Cookie をクリアする")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Void> {
        readCookie(request, REFRESH_COOKIE)?.let { raw ->
            val tokenHash = jwtTokenService.hashRefreshToken(raw)
            refreshTokenRepository.findByTokenHash(tokenHash)?.let {
                refreshTokenRepository.revoke(it.id)
            }
        }
        clearCookies(response)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/me")
    @Operation(summary = "現在ユーザ取得", description = "access_token Cookie から認証情報を返す。未認証は 200 + user=null")
    fun me(): ResponseEntity<MeResponse> {
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth?.principal
        if (principal !is Jwt) {
            return ResponseEntity.ok(MeResponse(null))
        }
        val userId = principal.subject ?: return ResponseEntity.ok(MeResponse(null))
        val authority = principal.getClaimAsString(JwtTokenService.CLAIM_AUTHORITY) ?: CDef.Authority.プレイヤー.code()
        return ResponseEntity.ok(MeResponse(MeResponse.UserPayload(userId, authority)))
    }

    private fun issueCookies(response: HttpServletResponse, userName: String, authority: CDef.Authority, playerId: Int) {
        val accessToken = jwtTokenService.issueAccessToken(userName, authority)
        val rawRefresh = jwtTokenService.generateRefreshToken()
        val refreshHash = jwtTokenService.hashRefreshToken(rawRefresh)
        val expiresAt = LocalDateTime.now().plusSeconds(jwtTokenService.refreshTokenTtlSeconds())
        refreshTokenRepository.register(playerId, refreshHash, expiresAt)

        response.addHeader("Set-Cookie", buildAccessCookie(accessToken).toString())
        response.addHeader("Set-Cookie", buildRefreshCookie(rawRefresh).toString())
    }

    private fun clearCookies(response: HttpServletResponse) {
        response.addHeader("Set-Cookie", buildAccessCookie("", maxAgeSeconds = 0).toString())
        response.addHeader("Set-Cookie", buildRefreshCookie("", maxAgeSeconds = 0).toString())
    }

    private fun buildAccessCookie(value: String, maxAgeSeconds: Long = jwtTokenService.accessTokenTtlSeconds()): ResponseCookie =
        ResponseCookie.from(ACCESS_COOKIE, value)
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite("Lax")
            .path("/")
            .maxAge(maxAgeSeconds)
            .build()

    private fun buildRefreshCookie(value: String, maxAgeSeconds: Long = jwtTokenService.refreshTokenTtlSeconds()): ResponseCookie =
        ResponseCookie.from(REFRESH_COOKIE, value)
            .httpOnly(true)
            .secure(cookieSecure)
            .sameSite("Lax")
            .path("$contextPath$AUTH_PATH")
            .maxAge(maxAgeSeconds)
            .build()

    private fun readCookie(request: HttpServletRequest, name: String): String? =
        request.cookies?.firstOrNull { it.name == name }?.value
}
