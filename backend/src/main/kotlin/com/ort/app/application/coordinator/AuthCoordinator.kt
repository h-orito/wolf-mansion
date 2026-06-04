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
import java.time.LocalDateTime
import java.time.ZoneId

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

    // 漏洩検知の revokeAllByPlayer は throw 後もコミットさせる必要があるため、
    // WolfMansionAuthException ではロールバックしない (それ以外の例外ではロールバックする)。
    @Transactional(rollbackFor = [Exception::class], noRollbackFor = [WolfMansionAuthException::class])
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
        // プレイヤー取得は markUsed より前に行う (null なら旧トークンを消費せずに弾けるため、
        // noRollbackFor 下でも "旧トークンだけ使用済み・新トークン未発行" の中途半端なコミットを避けられる)。
        // FK 上 REFRESH_TOKEN は PLAYER に従属し削除フローも無いため、通常 null にはならない防御的処理。
        val playerAuth =
            playerAuthRepository.findById(refreshToken.playerId)
                ?: throw WolfMansionAuthException(INVALID_REFRESH)
        // 使い捨て: 未使用のものだけをアトミックに使用済みにする。
        // 並行リクエストに先を越された (= 二重消費) 場合は false → このリクエストは拒否する。
        if (!refreshTokenRepository.markUsed(refreshToken.id, now)) {
            throw WolfMansionAuthException(INVALID_REFRESH)
        }
        return issueTokens(playerAuth)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun logout(rawRefreshToken: String?) {
        // access token (JWT) は stateless のためサーバー側では失効できない (発行から 15 分間は有効なまま)。
        // ここで失効させるのは DB 管理下の refresh token のみ。クライアントは両 Cookie を消す。
        if (rawRefreshToken.isNullOrBlank()) return
        val refreshToken = refreshTokenRepository.findByHash(refreshTokenFactory.hash(rawRefreshToken)) ?: return
        if (refreshToken.revokedDatetime == null) {
            refreshTokenRepository.revoke(refreshToken.id, LocalDateTime.now())
        }
    }

    private fun issueTokens(playerAuth: PlayerAuth): AuthTokens {
        // 1 トランザクション内で時刻を統一 (JWT の iat と refresh の issued_datetime を揃える)
        val now = LocalDateTime.now()
        val nowInstant = now.atZone(ZoneId.systemDefault()).toInstant()
        // 肥大化抑制: 当該プレイヤーの期限切れトークンを掃除してから新規発行する
        refreshTokenRepository.deleteExpiredByPlayer(playerAuth.playerId, now)
        val accessToken =
            jwtTokenProvider.issueAccessToken(
                playerId = playerAuth.playerId,
                name = playerAuth.name,
                authorities = playerAuth.authorities,
                now = nowInstant,
            )
        val rawRefreshToken = refreshTokenFactory.generate()
        val refreshValidity = jwtTokenProvider.refreshTokenValidity()
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
