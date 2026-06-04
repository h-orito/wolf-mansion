package com.ort.app.application.coordinator

import com.ort.app.domain.model.auth.PlayerAuth
import com.ort.app.domain.model.auth.PlayerAuthRepository
import com.ort.app.domain.model.auth.RefreshToken
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.security.jwt.JwtTokenProvider
import com.ort.app.fw.security.jwt.RefreshTokenFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

internal class AuthCoordinatorTest {
    private val encoder = BCryptPasswordEncoder()
    private val jwt = JwtTokenProvider("test-secret-key-for-jwt-hs256-aaaaaaaaaaaaaaaaaaaa", 15, 14)
    private val refreshFactory = RefreshTokenFactory()

    private val alice =
        PlayerAuth(
            playerId = 1,
            name = "alice",
            passwordHash = encoder.encode("correct-horse"),
            authorities = listOf("ROLE_PLAYER"),
        )

    private fun newCoordinator(tokenRepo: FakeRefreshTokenRepository): AuthCoordinator =
        AuthCoordinator(
            playerAuthRepository = FakePlayerAuthRepository(listOf(alice)),
            refreshTokenRepository = tokenRepo,
            jwtTokenProvider = jwt,
            refreshTokenFactory = refreshFactory,
            passwordEncoder = encoder,
        )

    @Test
    fun login_success_issues_usable_refresh_token() {
        val repo = FakeRefreshTokenRepository()
        val tokens = newCoordinator(repo).login("alice", "correct-horse")

        assertEquals(1, tokens.principal.playerId)
        assertEquals("alice", tokens.principal.name)
        val stored = repo.findByHash(refreshFactory.hash(tokens.refreshToken))
        assertTrue(stored != null && stored.isUsable(LocalDateTime.now()))
    }

    @Test
    fun login_with_wrong_password_throws() {
        assertThrows<WolfMansionAuthException> {
            newCoordinator(FakeRefreshTokenRepository()).login("alice", "wrong")
        }
    }

    @Test
    fun login_with_unknown_user_throws() {
        assertThrows<WolfMansionAuthException> {
            newCoordinator(FakeRefreshTokenRepository()).login("nobody", "correct-horse")
        }
    }

    @Test
    fun refresh_rotates_old_token_and_issues_new() {
        val repo = FakeRefreshTokenRepository()
        val coordinator = newCoordinator(repo)
        val first = coordinator.login("alice", "correct-horse")

        val second = coordinator.refresh(first.refreshToken)

        // 新しい refresh token が発行される
        assertNotEquals(first.refreshToken, second.refreshToken)
        // 旧トークンは使用済み (再利用不可)
        val old = repo.findByHash(refreshFactory.hash(first.refreshToken))
        assertTrue(old != null && old.isUsed)
        // 新トークンは使用可能
        val new = repo.findByHash(refreshFactory.hash(second.refreshToken))
        assertTrue(new != null && new.isUsable(LocalDateTime.now()))
    }

    @Test
    fun reusing_rotated_token_revokes_all_player_tokens() {
        val repo = FakeRefreshTokenRepository()
        val coordinator = newCoordinator(repo)
        val first = coordinator.login("alice", "correct-horse")
        val second = coordinator.refresh(first.refreshToken) // first は使用済みに

        // 使用済みの first を再提示 → 漏洩疑いで例外 + 全失効
        assertThrows<WolfMansionAuthException> { coordinator.refresh(first.refreshToken) }

        // second (まだ有効だった) も失効している
        val new = repo.findByHash(refreshFactory.hash(second.refreshToken))
        assertTrue(new != null && new.isRevoked)
    }

    @Test
    fun logout_revokes_presented_token() {
        val repo = FakeRefreshTokenRepository()
        val coordinator = newCoordinator(repo)
        val tokens = coordinator.login("alice", "correct-horse")

        coordinator.logout(tokens.refreshToken)

        val stored = repo.findByHash(refreshFactory.hash(tokens.refreshToken))
        assertTrue(stored != null && stored.isRevoked)
    }

    @Test
    fun refresh_with_unknown_token_throws() {
        assertThrows<WolfMansionAuthException> {
            newCoordinator(FakeRefreshTokenRepository()).refresh("does-not-exist")
        }
    }

    @Test
    fun logout_with_null_token_is_noop() {
        val repo = FakeRefreshTokenRepository()
        newCoordinator(repo).logout(null)
        assertNull(repo.findByHash("anything"))
        assertFalse(repo.hasAny())
    }
}

private class FakePlayerAuthRepository(
    players: List<PlayerAuth>,
) : PlayerAuthRepository {
    private val byName = players.associateBy { it.name }
    private val byId = players.associateBy { it.playerId }

    override fun findByName(name: String): PlayerAuth? = byName[name]

    override fun findById(playerId: Int): PlayerAuth? = byId[playerId]
}

private class FakeRefreshTokenRepository : RefreshTokenRepository {
    private val tokens = mutableListOf<RefreshToken>()
    private var sequence = 0

    override fun insert(
        playerId: Int,
        tokenHash: String,
        issuedDatetime: LocalDateTime,
        expiresDatetime: LocalDateTime,
    ): RefreshToken {
        val token =
            RefreshToken(
                id = ++sequence,
                playerId = playerId,
                tokenHash = tokenHash,
                issuedDatetime = issuedDatetime,
                expiresDatetime = expiresDatetime,
                usedDatetime = null,
                revokedDatetime = null,
            )
        tokens.add(token)
        return token
    }

    override fun findByHash(tokenHash: String): RefreshToken? = tokens.firstOrNull { it.tokenHash == tokenHash }

    override fun markUsed(
        id: Int,
        usedDatetime: LocalDateTime,
    ) = replace(id) { it.copy(usedDatetime = usedDatetime) }

    override fun revoke(
        id: Int,
        revokedDatetime: LocalDateTime,
    ) = replace(id) { it.copy(revokedDatetime = revokedDatetime) }

    override fun revokeAllByPlayer(
        playerId: Int,
        revokedDatetime: LocalDateTime,
    ) {
        tokens.replaceAll {
            if (it.playerId == playerId && it.revokedDatetime == null) it.copy(revokedDatetime = revokedDatetime) else it
        }
    }

    fun hasAny(): Boolean = tokens.isNotEmpty()

    private fun replace(
        id: Int,
        transform: (RefreshToken) -> RefreshToken,
    ) {
        val index = tokens.indexOfFirst { it.id == id }
        if (index >= 0) tokens[index] = transform(tokens[index])
    }
}
