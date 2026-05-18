package com.ort.app.application.scheduler

import com.ort.app.domain.model.auth.RefreshTokenRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class RefreshTokenCleanupJobTest {

    @Test
    fun `cleanup は repository の deleteExpired に現在時刻を渡す`() {
        val repo = mock<RefreshTokenRepository>()
        val job = RefreshTokenCleanupJob(repo)

        val before = LocalDateTime.now()
        job.cleanup()
        val after = LocalDateTime.now()

        verify(repo).deleteExpired(org.mockito.kotlin.check {
            require(!it.isBefore(before) && !it.isAfter(after)) {
                "expected deleteExpired arg between $before and $after but was $it"
            }
        })
        verify(repo).deleteExpired(any())
    }
}
