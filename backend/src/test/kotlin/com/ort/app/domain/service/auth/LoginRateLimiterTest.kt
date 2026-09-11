package com.ort.app.domain.service.auth

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

internal class LoginRateLimiterTest {
    private val window = 15L
    private val maxAccount = 5
    private val maxIp = 30
    private val now: LocalDateTime = LocalDateTime.of(2026, 6, 5, 12, 0, 0)

    private fun newLimiter(
        repo: FakeLoginAttemptRepository,
        discord: FakeDiscordRepository = FakeDiscordRepository(),
    ): LoginRateLimiter = LoginRateLimiter(repo, discord, window, maxAccount, maxIp)

    @Test
    fun under_account_threshold_passes() {
        val repo = FakeLoginAttemptRepository()
        repeat(maxAccount - 1) { repo.recordFailure("alice", "1.1.1.1", now) }
        assertDoesNotThrow { newLimiter(repo).assertNotBlocked("alice", "1.1.1.1", now) }
    }

    @Test
    fun account_threshold_blocks() {
        val repo = FakeLoginAttemptRepository()
        repeat(maxAccount) { repo.recordFailure("alice", "1.1.1.1", now) }
        assertThrows<WolfMansionTooManyRequestsException> {
            newLimiter(repo).assertNotBlocked("alice", "1.1.1.1", now)
        }
    }

    @Test
    fun ip_threshold_blocks_across_accounts() {
        val repo = FakeLoginAttemptRepository()
        // 同一 IP・異なるアカウントで maxIp 回失敗
        repeat(maxIp) { i -> repo.recordFailure("user$i", "9.9.9.9", now) }
        assertThrows<WolfMansionTooManyRequestsException> {
            newLimiter(repo).assertNotBlocked("fresh-account", "9.9.9.9", now)
        }
    }

    @Test
    fun failures_outside_window_are_ignored() {
        val repo = FakeLoginAttemptRepository()
        val old = now.minusMinutes(window + 1)
        repeat(maxAccount) { repo.recordFailure("alice", "1.1.1.1", old) }
        // 窓外 (window+1 分前) の失敗は数えない
        assertDoesNotThrow { newLimiter(repo).assertNotBlocked("alice", "1.1.1.1", now) }
    }

    @Test
    fun reset_clears_account_failures() {
        val repo = FakeLoginAttemptRepository()
        val limiter = newLimiter(repo)
        repeat(maxAccount) { repo.recordFailure("alice", "1.1.1.1", now) }
        limiter.reset("alice")
        assertDoesNotThrow { limiter.assertNotBlocked("alice", "1.1.1.1", now) }
    }

    @Test
    fun record_failure_sweeps_out_of_window_rows() {
        val repo = FakeLoginAttemptRepository()
        val limiter = newLimiter(repo)
        // 窓外の古い行を 3 件仕込む
        repeat(3) { repo.recordFailure("bob", "2.2.2.2", now.minusMinutes(window + 5)) }
        // 新しい失敗を記録すると deleteOlderThan で古い行が掃除される
        limiter.recordFailure("bob", "2.2.2.2", now)
        assertEquals(1, repo.rows.size)
    }

    @Test
    fun notifies_master_once_at_account_threshold_via_login_loop() {
        val repo = FakeLoginAttemptRepository()
        val discord = FakeDiscordRepository()
        val limiter = newLimiter(repo, discord)
        // 実フロー再現: assertNotBlocked → 失敗時のみ recordFailure。閾値到達後は assertNotBlocked で弾かれ記録されない
        repeat(10) {
            try {
                limiter.assertNotBlocked("alice", "1.1.1.1", now)
                limiter.recordFailure("alice", "1.1.1.1", now)
            } catch (_: WolfMansionTooManyRequestsException) {
                // 閾値到達後はブロック
            }
        }
        assertEquals(1, discord.masterMessages.size)
        assertEquals(maxAccount, repo.rows.count { it.loginName == "alice" })
        assert(discord.masterMessages.single().contains("アカウント"))
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
    val masterMessages = mutableListOf<String>()

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

    override fun postToMaster(message: String) {
        masterMessages.add(message)
    }
}
