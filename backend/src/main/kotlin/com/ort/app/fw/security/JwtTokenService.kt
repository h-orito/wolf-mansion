package com.ort.app.fw.security

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.ort.dbflute.allcommon.CDef
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.crypto.spec.SecretKeySpec

@Service
class JwtTokenService(
    @Value("\${app.jwt.secret}") private val secret: String,
    @Value("\${app.jwt.access-token-ttl-seconds:900}") private val accessTtlSeconds: Long,
    @Value("\${app.jwt.refresh-token-ttl-seconds:1209600}") private val refreshTtlSeconds: Long,
) {
    companion object {
        const val ISSUER = "wolf-mansion"
        const val CLAIM_AUTHORITY = "authority"
    }

    private val secretKey: SecretKeySpec by lazy {
        require(secret.toByteArray(Charsets.UTF_8).size >= 32) {
            "JWT secret must be at least 32 bytes"
        }
        SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256")
    }

    private val signer: MACSigner by lazy { MACSigner(secretKey) }
    private val verifier: JWSVerifier by lazy { MACVerifier(secretKey) }

    fun issueAccessToken(playerName: String, authority: CDef.Authority): String {
        val now = Instant.now()
        val claims = JWTClaimsSet.Builder()
            .issuer(ISSUER)
            .subject(playerName)
            .issueTime(Date.from(now))
            .expirationTime(Date.from(now.plusSeconds(accessTtlSeconds)))
            .jwtID(UUID.randomUUID().toString())
            .claim(CLAIM_AUTHORITY, authority.code())
            .build()
        return sign(claims)
    }

    /** refresh_token は不透明トークン（ランダム文字列）。JWT ではない。 */
    fun generateRefreshToken(): String {
        val bytes = ByteArray(48)
        java.security.SecureRandom().nextBytes(bytes)
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    fun hashRefreshToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(token.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun accessTokenTtlSeconds(): Long = accessTtlSeconds
    fun refreshTokenTtlSeconds(): Long = refreshTtlSeconds

    fun jwtDecoder(): JwtDecoder =
        NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build()

    private fun sign(claims: JWTClaimsSet): String {
        val header = JWSHeader.Builder(JWSAlgorithm.HS256).build()
        val jwt = SignedJWT(header, claims)
        try {
            jwt.sign(signer)
        } catch (e: JOSEException) {
            throw IllegalStateException("Failed to sign JWT", e)
        }
        return jwt.serialize()
    }

    // unused but kept for future use (decoder bean is what spring uses)
    @Suppress("unused")
    private fun secretSource() = ImmutableSecret<com.nimbusds.jose.proc.SecurityContext>(secretKey)
}
