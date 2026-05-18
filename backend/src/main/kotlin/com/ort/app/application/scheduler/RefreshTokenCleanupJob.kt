package com.ort.app.application.scheduler

import com.ort.app.domain.model.auth.RefreshTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * 期限切れ refresh_token を定期削除する。
 * デフォルトは毎日 03:30 (JST) 実行。`app.jwt.cleanup-cron` で上書き可能。
 */
@Component
class RefreshTokenCleanupJob(
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    private val logger = LoggerFactory.getLogger(RefreshTokenCleanupJob::class.java)

    @Scheduled(cron = "\${app.jwt.cleanup-cron:0 30 3 * * *}", zone = "Asia/Tokyo")
    @Transactional
    fun cleanup() {
        val now = LocalDateTime.now()
        logger.info("RefreshToken cleanup started at {}", now)
        refreshTokenRepository.deleteExpired(now)
        logger.info("RefreshToken cleanup finished")
    }
}
