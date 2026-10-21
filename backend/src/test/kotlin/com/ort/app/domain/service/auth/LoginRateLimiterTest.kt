package com.ort.app.domain.service.auth

import com.ort.app.domain.model.auth.LoginAttemptRepository
import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.fw.exception.WolfMansionTooManyRequestsException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

internal class LoginRateLimiterTest {
    private val window = 15L
    private val maxAccount = 5
    private val maxIp = 30
    private val now: LocalDateTime = LocalDateTime.of(2026, 6, 5, 12, 0, 0)

    private fun newLimiter(
        repo: FakeLoginAttemptRepository,
        discord: FakeDiscordRepository = FakeDiscordRepository(),
    ): LoginRateLimiter = LoginRateLimiter(repo, discord, NoOpTransactionManager(), window, maxAccount, maxIp)

    private fun LoginRateLimiter.isBlocked(
        loginName: String,
        ipAddress: String,
    ): Boolean =
        try {
            acquire(loginName, ipAddress, now)
            false
        } catch (_: WolfMansionTooManyRequestsException) {
            true
        }

    @Test
    fun attempts_up_to_account_threshold_pass() {
        val limiter = newLimiter(FakeLoginAttemptRepository())
        repeat(maxAccount) { assertDoesNotThrow { limiter.acquire("alice", "1.1.1.1", now) } }
    }

    @Test
    fun attempt_over_account_threshold_is_blocked() {
        val limiter = newLimiter(FakeLoginAttemptRepository())
        repeat(maxAccount) { limiter.acquire("alice", "1.1.1.1", now) }
        assertThrows<WolfMansionTooManyRequestsException> { limiter.acquire("alice", "1.1.1.1", now) }
    }

    @Test
    fun account_threshold_from_other_ip_does_not_block_owner() {
        val limiter = newLimiter(FakeLoginAttemptRepository())
        // 攻撃者が別の IP から上限を超えて試しても、本人の IP からは試せる (締め出し対策)
        repeat(maxAccount + 3) { limiter.isBlocked("alice", "6.6.6.6") }
        assertTrue(limiter.isBlocked("alice", "6.6.6.6"))
        assertDoesNotThrow { limiter.acquire("alice", "1.1.1.1", now) }
    }

    @Test
    fun ip_threshold_blocks_across_accounts() {
        val limiter = newLimiter(FakeLoginAttemptRepository())
        // 同一 IP・異なるアカウントで maxIp 回試行
        repeat(maxIp) { i -> limiter.acquire("user$i", "9.9.9.9", now) }
        assertThrows<WolfMansionTooManyRequestsException> { limiter.acquire("fresh-account", "9.9.9.9", now) }
    }

    @Test
    fun attempts_outside_window_are_ignored() {
        val repo = FakeLoginAttemptRepository()
        val old = now.minusMinutes(window + 1)
        repeat(maxAccount) { repo.recordAttempt("alice", "1.1.1.1", old) }
        // 窓外 (window+1 分前) の試行は数えない
        assertDoesNotThrow { newLimiter(repo).acquire("alice", "1.1.1.1", now) }
    }

    @Test
    fun reset_clears_only_that_account_and_ip() {
        val repo = FakeLoginAttemptRepository()
        val limiter = newLimiter(repo)
        repeat(maxAccount) { limiter.acquire("alice", "1.1.1.1", now) }
        repeat(2) { limiter.acquire("alice", "6.6.6.6", now) }
        limiter.reset("alice", "1.1.1.1")
        assertEquals(0, repo.rows.count { it.ipAddress == "1.1.1.1" })
        assertEquals(2, repo.rows.count { it.ipAddress == "6.6.6.6" })
        assertDoesNotThrow { limiter.acquire("alice", "1.1.1.1", now) }
    }

    @Test
    fun acquire_sweeps_out_of_window_rows() {
        val repo = FakeLoginAttemptRepository()
        // 窓外の古い行を 3 件仕込む
        repeat(3) { repo.recordAttempt("bob", "2.2.2.2", now.minusMinutes(window + 5)) }
        // 新しい試行を記録すると deleteOlderThan で古い行が掃除される
        newLimiter(repo).acquire("bob", "2.2.2.2", now)
        assertEquals(1, repo.rows.size)
    }

    @Test
    fun notifies_master_once_when_account_threshold_is_exceeded() {
        val discord = FakeDiscordRepository()
        val limiter = newLimiter(FakeLoginAttemptRepository(), discord)
        repeat(10) { limiter.isBlocked("alice", "1.1.1.1") }
        assertEquals(1, discord.masterMessages.size)
        assertTrue(discord.masterMessages.single().contains("アカウント"))
    }

    @Test
    fun concurrent_attempts_do_not_exceed_account_threshold() {
        // 並行リクエストが全員「記録前の件数」を見て通ることがないか確認する。
        // 各スレッドの最初のリポジトリ操作 (記録 or 件数取得) の直後で全スレッドを待ち合わせ、
        // 記録と件数取得の間に他のリクエストが割り込む最悪の順序を作る
        val threads = 10
        val barrier = CyclicBarrier(threads)
        val fake = FakeLoginAttemptRepository()
        val firstCallDone = ThreadLocal.withInitial { false }

        fun <T> afterFirstCall(result: T): T {
            if (!firstCallDone.get()) {
                firstCallDone.set(true)
                barrier.await(5, TimeUnit.SECONDS)
            }
            return result
        }
        val repo =
            object : LoginAttemptRepository by fake {
                override fun countByLoginNameAndIpAddress(
                    loginName: String,
                    ipAddress: String,
                    since: LocalDateTime,
                ): Int = afterFirstCall(fake.countByLoginNameAndIpAddress(loginName, ipAddress, since))

                override fun recordAttempt(
                    loginName: String,
                    ipAddress: String,
                    attemptAt: LocalDateTime,
                ) = afterFirstCall(fake.recordAttempt(loginName, ipAddress, attemptAt))
            }
        val passed = AtomicInteger()
        val executor = Executors.newFixedThreadPool(threads)
        try {
            val futures =
                (1..threads).map {
                    executor.submit {
                        try {
                            LoginRateLimiter(repo, FakeDiscordRepository(), NoOpTransactionManager(), window, maxAccount, maxIp)
                                .acquire("alice", "1.1.1.1", now)
                            passed.incrementAndGet()
                        } catch (_: WolfMansionTooManyRequestsException) {
                            // 上限超過
                        }
                    }
                }
            futures.forEach { it.get(10, TimeUnit.SECONDS) }
        } finally {
            executor.shutdownNow()
        }
        assertTrue(passed.get() <= maxAccount, "passed=${passed.get()}")
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
