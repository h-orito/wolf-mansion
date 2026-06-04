package com.ort.app.application.coordinator

import com.ort.app.domain.model.auth.PlayerAuth
import com.ort.app.domain.model.auth.PlayerAuthRepository
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.security.jwt.JwtPrincipal
import com.ort.app.fw.security.jwt.JwtTokenProvider
import com.ort.app.fw.security.jwt.RefreshTokenFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDateTime

/**
 * 認証ユースケースのトランザクション境界。login / refresh / logout を担う。
 * - access token = JWT (短命)、refresh token = 不透明な乱数 (DB にハッシュ保存、使い捨て rotation)
 * - 資格情報誤りはユーザー存在を区別しない文言で 401
 */
@Service
class AuthCoordinator(
    private val playerAuthRepository: PlayerAuthRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenFactory: RefreshTokenFactory,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional(rollbackFor = [Exception::class])
    fun login(
        userId: String,
        rawPassword: String,
    ): AuthTokens {
        val playerAuth = playerAuthRepository.findByName(userId)
        if (playerAuth == null || !passwordEncoder.matches(rawPassword, playerAuth.passwordHash)) {
            throw WolfMansionAuthException("ユーザIDまたはパスワードが違います")
        }
        return issueTokens(playerAuth)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun refresh(rawRefreshToken: String): AuthTokens {
        val now = LocalDateTime.now()
        val tokenHash = refreshTokenFactory.hash(rawRefreshToken)
        val refreshToken =
            refreshTokenRepository.findByHash(tokenHash)
                ?: throw WolfMansionAuthException(INVALID_REFRESH)
        if (refreshToken.isUsed) {
            // rotation 済みトークンの再提示 = 漏洩疑い。当該プレイヤーの未失効トークンを全て失効させる
            refreshTokenRepository.revokeAllByPlayer(refreshToken.playerId, now)
            throw WolfMansionAuthException(INVALID_REFRESH)
        }
        if (!refreshToken.isUsable(now)) throw WolfMansionAuthException(INVALID_REFRESH)
        // 使い捨て: 旧トークンを使用済みにし、新トークンを発行する
        refreshTokenRepository.markUsed(refreshToken.id, now)
        val playerAuth =
            playerAuthRepository.findById(refreshToken.playerId)
                ?: throw WolfMansionAuthException(INVALID_REFRESH)
        return issueTokens(playerAuth)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun logout(rawRefreshToken: String?) {
        if (rawRefreshToken.isNullOrBlank()) return
        val refreshToken = refreshTokenRepository.findByHash(refreshTokenFactory.hash(rawRefreshToken)) ?: return
        if (refreshToken.revokedDatetime == null) {
            refreshTokenRepository.revoke(refreshToken.id, LocalDateTime.now())
        }
    }

    private fun issueTokens(playerAuth: PlayerAuth): AuthTokens {
        val accessToken =
            jwtTokenProvider.issueAccessToken(
                playerId = playerAuth.playerId,
                name = playerAuth.name,
                authorities = playerAuth.authorities,
                now = Instant.now(),
            )
        val rawRefreshToken = refreshTokenFactory.generate()
        val refreshValidity = jwtTokenProvider.refreshTokenValidity()
        val now = LocalDateTime.now()
        refreshTokenRepository.insert(
            playerId = playerAuth.playerId,
            tokenHash = refreshTokenFactory.hash(rawRefreshToken),
            issuedDatetime = now,
            expiresDatetime = now.plus(refreshValidity),
        )
        return AuthTokens(
            principal = JwtPrincipal(playerAuth.playerId, playerAuth.name, playerAuth.authorities),
            accessToken = accessToken,
            accessTokenMaxAge = jwtTokenProvider.accessTokenValidity(),
            refreshToken = rawRefreshToken,
            refreshTokenMaxAge = refreshValidity,
        )
    }

    companion object {
        private const val INVALID_REFRESH = "リフレッシュトークンが無効です"
    }
}
