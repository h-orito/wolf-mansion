package com.ort.app.fw.security.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

/**
 * access token (JWT/HS256) の発行・検証を担う。refresh token は JWT ではなく不透明な乱数 ([RefreshTokenFactory])。
 */
@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.access-token-validity-minutes:15}") private val accessTokenValidityMinutes: Long,
    @Value("\${jwt.refresh-token-validity-days:14}") private val refreshTokenValidityDays: Long,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun accessTokenValidity(): Duration = Duration.ofMinutes(accessTokenValidityMinutes)

    fun refreshTokenValidity(): Duration = Duration.ofDays(refreshTokenValidityDays)

    fun issueAccessToken(
        playerId: Int,
        name: String,
        authorities: List<String>,
        now: Instant = Instant.now(),
    ): String =
        Jwts
            .builder()
            .subject(playerId.toString())
            .claim(CLAIM_NAME, name)
            .claim(CLAIM_AUTHORITIES, authorities)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(accessTokenValidity())))
            // HS256 を明示 (signWith(key) は鍵長から alg を自動選択するため非決定的になる)。鍵は 256bit 以上必須
            .signWith(key, Jwts.SIG.HS256)
            .compact()

    /** access token を検証し principal を返す。署名不正・期限切れ・不正形式は null。 */
    fun parseAccessToken(token: String): JwtPrincipal? {
        return try {
            val claims =
                Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .payload
            val playerId = claims.subject?.toIntOrNull() ?: return null

            @Suppress("UNCHECKED_CAST")
            val authorities = (claims[CLAIM_AUTHORITIES] as? List<String>) ?: emptyList()
            val name = claims[CLAIM_NAME] as? String ?: ""
            JwtPrincipal(playerId = playerId, name = name, authorities = authorities)
        } catch (e: JwtException) {
            logger.debug("invalid access token: {}", e.message)
            null
        } catch (e: IllegalArgumentException) {
            logger.debug("invalid access token: {}", e.message)
            null
        }
    }

    companion object {
        private const val CLAIM_NAME = "name"
        private const val CLAIM_AUTHORITIES = "authorities"
    }
}
