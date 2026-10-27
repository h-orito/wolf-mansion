package com.ort.app.application.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant

/**
 * 入村パスワードの失敗回数を、プレイヤー×村ごとにメモリで数える
 * (バックエンドは 1 台で動かしている。再起動すると回数はリセットされる)
 *
 * MAX_FAILURES 回、WINDOW の間にパスワードを間違えたら、最後の失敗から LOCK_DURATION の間は参加できない
 */
@Component
class JoinPasswordAttemptLimiter {
    companion object {
        const val MAX_FAILURES = 5
        val WINDOW: Duration = Duration.ofMinutes(15)
        val LOCK_DURATION: Duration = Duration.ofMinutes(15)

        // これを超えるプレイヤー×村の記録があれば、古い記録を掃除する
        const val SWEEP_THRESHOLD = 1000
    }

    private data class Key(
        val playerId: Int,
        val villageId: Int,
    )

    private class Attempt {
        val failures = mutableListOf<Instant>()
        var lockedUntil: Instant = Instant.EPOCH

        // 結果がまだ出ていない試行の数
        var inFlight = 0
    }

    private val attempts = mutableMapOf<Key, Attempt>()

    internal var now: () -> Instant = Instant::now

    internal val recordCount: Int
        get() = synchronized(this) { attempts.size }

    /**
     * 試行を 1 回分予約する。ロック中、または失敗回数と結果待ちの試行の合計が上限に達していれば false
     * (確認と予約を 1 回のロックの中で行い、同時に送られたリクエストで上限を超えて試せないようにする)
     */
    @Synchronized
    fun acquire(
        playerId: Int,
        villageId: Int,
    ): Boolean {
        val now = now()
        if (attempts.size > SWEEP_THRESHOLD) sweep(now)
        val attempt = attempts.getOrPut(Key(playerId, villageId)) { Attempt() }
        removeOldFailures(attempt, now)
        if (now.isBefore(attempt.lockedUntil) || attempt.failures.size + attempt.inFlight >= MAX_FAILURES) return false
        attempt.inFlight++
        return true
    }

    /**
     * acquire で予約した試行を終える。failed ならパスワードの失敗として数える
     */
    @Synchronized
    fun release(
        playerId: Int,
        villageId: Int,
        failed: Boolean,
    ) {
        val attempt = attempts[Key(playerId, villageId)] ?: return
        attempt.inFlight--
        if (!failed) return
        val now = now()
        removeOldFailures(attempt, now)
        attempt.failures.add(now)
        if (attempt.failures.size >= MAX_FAILURES) attempt.lockedUntil = now.plus(LOCK_DURATION)
    }

    private fun sweep(now: Instant) {
        attempts.entries.removeIf { (_, attempt) ->
            removeOldFailures(attempt, now)
            attempt.inFlight == 0 && attempt.failures.isEmpty() && !now.isBefore(attempt.lockedUntil)
        }
    }

    private fun removeOldFailures(
        attempt: Attempt,
        now: Instant,
    ) {
        attempt.failures.removeIf { Duration.between(it, now) >= WINDOW }
    }
}
