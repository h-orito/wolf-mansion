package com.ort.app.application.service

import com.ort.app.application.service.JoinPasswordAttemptLimiter.Companion.LOCK_DURATION
import com.ort.app.application.service.JoinPasswordAttemptLimiter.Companion.MAX_FAILURES
import com.ort.app.application.service.JoinPasswordAttemptLimiter.Companion.SWEEP_THRESHOLD
import com.ort.app.application.service.JoinPasswordAttemptLimiter.Companion.WINDOW
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class JoinPasswordAttemptLimiterTest {
    private var clock: Instant = Instant.parse("2026-10-01T00:00:00Z")
    private val limiter = JoinPasswordAttemptLimiter().apply { now = { clock } }

    private fun advance(duration: Duration) {
        clock = clock.plus(duration)
    }

    // 1 回試して失敗する
    private fun fail(
        playerId: Int,
        villageId: Int,
    ): Boolean {
        if (!limiter.acquire(playerId, villageId)) return false
        limiter.release(playerId, villageId, failed = true)
        return true
    }

    @Test
    @DisplayName("上限回数失敗するとロックされ、最後の失敗からロック期間が過ぎると解除される")
    fun locksAfterMaxFailures() {
        repeat(MAX_FAILURES) {
            assertTrue(fail(1, 10), "attempt ${it + 1}")
            advance(Duration.ofMinutes(1))
        }
        assertFalse(limiter.acquire(1, 10))
        // 他のプレイヤー・他の村には影響しない
        assertTrue(limiter.acquire(2, 10))
        assertTrue(limiter.acquire(1, 11))

        advance(LOCK_DURATION.minusMinutes(1).minusSeconds(1))
        assertFalse(limiter.acquire(1, 10))
        advance(Duration.ofSeconds(1))
        assertTrue(limiter.acquire(1, 10))
    }

    @Test
    @DisplayName("成功は数えない")
    fun successIsNotCounted() {
        repeat(MAX_FAILURES * 2) {
            assertTrue(limiter.acquire(1, 10))
            limiter.release(1, 10, failed = false)
        }
    }

    @Test
    @DisplayName("時間が経った失敗は数えない")
    fun forgetsOldFailures() {
        repeat(MAX_FAILURES - 1) { fail(1, 10) }
        advance(WINDOW)
        fail(1, 10)
        assertTrue(limiter.acquire(1, 10))
    }

    @Test
    @DisplayName("結果が出る前の試行も数え、同時に送られたリクエストで上限を超えて試せない")
    fun countsInFlightAttempts() {
        val executor = Executors.newFixedThreadPool(16)
        val start = CountDownLatch(1)
        val acquired = AtomicInteger()
        val futures =
            (1..50).map {
                executor.submit {
                    start.await()
                    if (limiter.acquire(1, 10)) acquired.incrementAndGet()
                }
            }
        start.countDown()
        futures.forEach { it.get() }
        executor.shutdown()
        assertEquals(MAX_FAILURES, acquired.get())
    }

    @Test
    @DisplayName("記録が増えたら期限切れの記録を掃除する")
    fun sweepsExpiredRecords() {
        (0..SWEEP_THRESHOLD).forEach { fail(it, 10) }
        advance(WINDOW)
        fail(9999, 10)
        assertEquals(1, limiter.recordCount)
    }
}
