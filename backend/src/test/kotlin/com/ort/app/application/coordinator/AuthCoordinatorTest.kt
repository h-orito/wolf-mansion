package com.ort.app.application.coordinator

import com.ort.app.application.service.PlayerService
import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.domain.model.auth.PlayerAuth
import com.ort.app.domain.model.auth.PlayerAuthRepository
import com.ort.app.domain.model.auth.RefreshToken
import com.ort.app.domain.model.auth.RefreshTokenRepository
import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.domain.service.auth.LoginRateLimiter
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import com.ort.app.fw.security.jwt.JwtTokenProvider
import com.ort.app.fw.security.jwt.RefreshTokenFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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
    private val bob =
        PlayerAuth(
            playerId = 2,
            name = "bob",
            passwordHash = encoder.encode("irrelevant"),
            authorities = listOf("ROLE_PLAYER"),
        )

    private fun newCoordinator(
        tokenRepo: FakeRefreshTokenRepository,
        players: List<PlayerAuth> = listOf(alice),
        playerService: PlayerService = mock(PlayerService::class.java),
        attemptRepo: FakeLoginAttemptRepository = FakeLoginAttemptRepository(),
    ): AuthCoordinator =
        AuthCoordinator(
            playerAuthRepository = FakePlayerAuthRepository(players),
            refreshTokenRepository = tokenRepo,
            jwtTokenProvider = jwt,
            refreshTokenFactory = refreshFactory,
            passwordEncoder = encoder,
            playerService = playerService,
            loginRateLimiter = LoginRateLimiter(attemptRepo, FakeDiscordRepository(), 15, MAX_PER_ACCOUNT, 30),
        )

    @Test
    fun login_success_issues_usable_refresh_token() {
        val repo = FakeRefreshTokenRepository()
        val tokens = newCoordinator(repo).login("alice", "correct-horse", CLIENT_IP)

        assertEquals(1, tokens.principal.playerId)
        assertEquals("alice", tokens.principal.name)
        val stored = repo.findByHash(refreshFactory.hash(tokens.refreshToken))
        assertTrue(stored != null && stored.isUsable(LocalDateTime.now()))
    }

    @Test
    fun login_with_wrong_password_throws() {
        assertThrows<WolfMansionAuthException> {
            newCoordinator(FakeRefreshTokenRepository()).login("alice", "wrong", CLIENT_IP)
        }
    }

    @Test
    fun login_with_unknown_user_throws() {
        assertThrows<WolfMansionAuthException> {
            newCoordinator(FakeRefreshTokenRepository()).login("nobody", "correct-horse", CLIENT_IP)
        }
    }

    @Test
    fun login_blocked_after_too_many_failures() {
        val attemptRepo = FakeLoginAttemptRepository()
        val coordinator = newCoordinator(FakeRefreshTokenRepository(), attemptRepo = attemptRepo)
        // MAX_PER_ACCOUNT 回の失敗で閾値到達
        repeat(MAX_PER_ACCOUNT) {
            assertThrows<WolfMansionAuthException> { coordinator.login("alice", "wrong", CLIENT_IP) }
        }
        // 以降は正しいパスワードでも 429 (資格情報検証より前にブロック)
        assertThrows<WolfMansionTooManyRequestsException> { coordinator.login("alice", "correct-horse", CLIENT_IP) }
    }

    @Test
    fun successful_login_resets_failures() {
        val attemptRepo = FakeLoginAttemptRepository()
        val coordinator = newCoordinator(FakeRefreshTokenRepository(), attemptRepo = attemptRepo)
        // 閾値未満まで失敗させてから成功 → リセット
        repeat(MAX_PER_ACCOUNT - 1) {
            assertThrows<WolfMansionAuthException> { coordinator.login("alice", "wrong", CLIENT_IP) }
        }
        coordinator.login("alice", "correct-horse", CLIENT_IP)
        assertEquals(0, attemptRepo.rows.count { it.loginName == "alice" })
        // リセット後は再び閾値手前まで失敗できる (即ブロックされない)
        assertThrows<WolfMansionAuthException> { coordinator.login("alice", "wrong", CLIENT_IP) }
    }

    @Test
    fun signup_creates_player_and_auto_logs_in() {
        val repo = FakeRefreshTokenRepository()
        val playerService = mock(PlayerService::class.java)
        // registerPlayer は no-op (mock)、登録後に findByName("bob") が引けるよう bob を seed
        val tokens =
            newCoordinator(repo, players = listOf(alice, bob), playerService = playerService)
                .signup("bob", "new-password", false)

        assertEquals(2, tokens.principal.playerId)
        assertEquals("bob", tokens.principal.name)
        val stored = repo.findByHash(refreshFactory.hash(tokens.refreshToken))
        assertTrue(stored != null && stored.isUsable(LocalDateTime.now()))
    }

    @Test
    fun signup_with_duplicate_id_throws_business_exception() {
        val playerService = mock(PlayerService::class.java)
        given(playerService.registerPlayer("alice", "whatever"))
            .willThrow(WolfMansionBusinessException("既に登録されているIDです。"))
        assertThrows<WolfMansionBusinessException> {
            newCoordinator(FakeRefreshTokenRepository(), playerService = playerService)
                .signup("alice", "whatever", false)
        }
    }

    @Test
    fun signup_during_cooldown_throws_too_many_requests() {
        assertThrows<WolfMansionTooManyRequestsException> {
            newCoordinator(FakeRefreshTokenRepository()).signup("bob", "new-password", true)
        }
    }

    @Test
    fun change_password_delegates_to_player_service() {
        val playerService = mock(PlayerService::class.java)
        newCoordinator(FakeRefreshTokenRepository(), playerService = playerService)
            .changePassword("alice", "brand-new-pass")
        verify(playerService).updatePassword("alice", "brand-new-pass")
    }

    @Test
    fun refresh_rotates_old_token_and_issues_new() {
        val repo = FakeRefreshTokenRepository()
        val coordinator = newCoordinator(repo)
        val first = coordinator.login("alice", "correct-horse", CLIENT_IP)

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
        val first = coordinator.login("alice", "correct-horse", CLIENT_IP)
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
        val tokens = coordinator.login("alice", "correct-horse", CLIENT_IP)

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

    companion object {
        private const val CLIENT_IP = "203.0.113.10"
        private const val MAX_PER_ACCOUNT = 5
    }
}

private class FakeLoginAttemptRepository : LoginAttemptRepository {
    data class Row(
        val loginName: String,
        val ipAddress: String,
        val attemptAt: LocalDateTime,
    )

    val rows = mutableListOf<Row>()

    override fun countByLoginName(
        loginName: String,
        since: LocalDateTime,
    ): Int = rows.count { it.loginName == loginName && !it.attemptAt.isBefore(since) }

    override fun countByIpAddress(
        ipAddress: String,
        since: LocalDateTime,
    ): Int = rows.count { it.ipAddress == ipAddress && !it.attemptAt.isBefore(since) }

    override fun recordFailure(
        loginName: String,
        ipAddress: String,
        attemptAt: LocalDateTime,
    ) {
        rows.add(Row(loginName, ipAddress, attemptAt))
    }

    override fun deleteByLoginName(loginName: String) {
        rows.removeAll { it.loginName == loginName }
    }

    override fun deleteOlderThan(threshold: LocalDateTime) {
        rows.removeAll { it.attemptAt.isBefore(threshold) }
    }
}

private class FakeDiscordRepository : DiscordRepository {
    override fun post(
        villageId: Int,
        day: Int,
        message: String,
    ) {}

    override fun postToWebhook(
        webhookUrl: String,
        villageId: Int,
        message: String,
        shouldContainVillageUrl: Boolean,
    ) {}

    override fun postToMaster(message: String) {}
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
    ): Boolean {
        val index = tokens.indexOfFirst { it.id == id }
        if (index < 0 || tokens[index].usedDatetime != null) return false
        tokens[index] = tokens[index].copy(usedDatetime = usedDatetime)
        return true
    }

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

    override fun deleteExpiredByPlayer(
        playerId: Int,
        now: LocalDateTime,
    ) {
        tokens.removeAll { it.playerId == playerId && it.expiresDatetime.isBefore(now) }
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
