package com.ort.app.fw.security.jwt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class JwtTokenProviderTest {
    // HS256 は 256bit(32 バイト)以上の鍵が必要
    private val secret = "test-secret-key-for-jwt-hs256-aaaaaaaaaaaaaaaaaaaa"
    private val provider = JwtTokenProvider(secret, accessTokenValidityMinutes = 15, refreshTokenValidityDays = 14)

    @Test
    fun issue_then_parse_roundtrip() {
        val token = provider.issueAccessToken(42, "alice", listOf("ROLE_PLAYER"))
        val principal = provider.parseAccessToken(token)
        assertNotNull(principal)
        assertEquals(42, principal!!.playerId)
        assertEquals("alice", principal.name)
        assertEquals(listOf("ROLE_PLAYER"), principal.authorities)
    }

    @Test
    fun parse_returns_null_for_expired_token() {
        // 30 分前に発行 → exp(発行+15分) は既に過去
        val past = Instant.now().minus(30, ChronoUnit.MINUTES)
        val token = provider.issueAccessToken(1, "bob", listOf("ROLE_PLAYER"), now = past)
        assertNull(provider.parseAccessToken(token))
    }

    @Test
    fun parse_returns_null_for_tampered_token() {
        val token = provider.issueAccessToken(1, "bob", listOf("ROLE_PLAYER"))
        val tampered = token.dropLast(3) + "xyz"
        assertNull(provider.parseAccessToken(tampered))
    }

    @Test
    fun parse_returns_null_for_token_signed_with_other_key() {
        val other = JwtTokenProvider("another-secret-key-totally-different-bbbbbbbbbbbb", 15, 14)
        val token = other.issueAccessToken(1, "bob", listOf("ROLE_PLAYER"))
        assertNull(provider.parseAccessToken(token))
    }

    @Test
    fun parse_returns_null_for_garbage() {
        assertNull(provider.parseAccessToken("not-a-jwt"))
    }
}
