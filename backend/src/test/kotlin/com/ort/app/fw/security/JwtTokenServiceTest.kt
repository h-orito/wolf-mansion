package com.ort.app.fw.security

import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** JwtTokenService の Spring を介さない単体テスト。 */
class JwtTokenServiceTest {

    private val secret = "test-secret-32bytes-minimum-required-for-hs256!"
    private val service = JwtTokenService(
        secret = secret,
        accessTtlSeconds = 900,
        refreshTtlSeconds = 1209600,
    )

    @Test
    fun `access token は decoder で検証可能で sub と authority を保持する`() {
        val token = service.issueAccessToken("alice", CDef.Authority.プレイヤー)
        val decoded = service.jwtDecoder().decode(token)

        assertEquals("alice", decoded.subject)
        assertEquals(CDef.Authority.プレイヤー.code(), decoded.getClaimAsString(JwtTokenService.CLAIM_AUTHORITY))
        assertEquals(JwtTokenService.ISSUER, decoded.getClaimAsString("iss"))
        assertNotNull(decoded.expiresAt)
        assertNotNull(decoded.issuedAt)
    }

    @Test
    fun `異なる secret で署名された token は検証で失敗する`() {
        val token = service.issueAccessToken("alice", CDef.Authority.管理者)
        val other = JwtTokenService(
            secret = "another-secret-32bytes-min-for-hs256-XXXXXXX!",
            accessTtlSeconds = 900,
            refreshTtlSeconds = 1209600,
        )
        assertThrows(Exception::class.java) {
            other.jwtDecoder().decode(token)
        }
    }

    @Test
    fun `generateRefreshToken は呼び出し毎に異なる値を返す`() {
        val t1 = service.generateRefreshToken()
        val t2 = service.generateRefreshToken()
        assertNotEquals(t1, t2)
        assertTrue(t1.isNotBlank())
    }

    @Test
    fun `hashRefreshToken は同じ入力に対して同じ hex 64 文字を返す`() {
        val token = "some-refresh-token"
        val h1 = service.hashRefreshToken(token)
        val h2 = service.hashRefreshToken(token)
        assertEquals(h1, h2)
        assertEquals(64, h1.length)
        assertTrue(h1.all { it.isDigit() || it in 'a'..'f' })
    }

    @Test
    fun `hashRefreshToken は異なる入力で異なるハッシュを返す`() {
        val h1 = service.hashRefreshToken("token-1")
        val h2 = service.hashRefreshToken("token-2")
        assertNotEquals(h1, h2)
    }

    @Test
    fun `secret が 32 bytes 未満ならトークン発行時に IllegalArgumentException`() {
        val invalid = JwtTokenService(
            secret = "tooshort",
            accessTtlSeconds = 900,
            refreshTtlSeconds = 1209600,
        )
        assertThrows(IllegalArgumentException::class.java) {
            invalid.issueAccessToken("alice", CDef.Authority.プレイヤー)
        }
    }

    @Test
    fun `TTL accessor が設定値をそのまま返す`() {
        assertEquals(900L, service.accessTokenTtlSeconds())
        assertEquals(1209600L, service.refreshTokenTtlSeconds())
    }

    @Test
    fun `管理者 authority も正しく claim に格納される`() {
        val token = service.issueAccessToken("admin", CDef.Authority.管理者)
        val decoded = service.jwtDecoder().decode(token)
        assertEquals(CDef.Authority.管理者.code(), decoded.getClaimAsString(JwtTokenService.CLAIM_AUTHORITY))
        assertFalse(decoded.subject.isBlank())
    }
}
