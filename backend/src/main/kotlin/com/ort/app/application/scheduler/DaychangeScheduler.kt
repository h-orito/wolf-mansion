package com.ort.app.application.scheduler

import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * 未終了の村の日付更新を定期実行する。
 *
 * 旧 Thymeleaf 版ではクライアントのポーリング (`POST /village/{id}/update`) が
 * `changeDayIfNeeded` を駆動していたが、REST 化でこの経路が失われたためスケジューラで
 * 代替する。実際に日付を進めるかは `changeDayIfNeeded` 内が各村の更新時刻を見て判定する。
 *
 * デフォルトは 60 秒間隔。`app.daychange.interval-ms` で上書き可能。
 *
 * `app.daychange.enabled=false` で無効化できる (テストでは `@Scheduled` の発火が
 * モック検証に干渉するため無効化している)。
 */
@Component
@ConditionalOnProperty(name = ["app.daychange.enabled"], matchIfMissing = true)
class DaychangeScheduler(
    private val villageService: VillageService,
    private val daychangeCoordinator: DaychangeCoordinator,
) {
    private val logger = LoggerFactory.getLogger(DaychangeScheduler::class.java)

    @Scheduled(fixedDelayString = "\${app.daychange.interval-ms:60000}")
    fun changeDay() {
        val villageIds = villageService.findVillages(
            VillageQuery(statuses = VillageStatus.notFinishedStatusList.map { VillageStatus(it) })
        ).list.map { it.id }
        // 1 村の日付更新失敗 (例外) が他村を巻き込まないよう、村ごとに隔離する。
        // changeDayIfNeeded 自体が @Transactional なのでロールバック境界も村単位。
        for (villageId in villageIds) {
            try {
                val village = villageService.findVillage(villageId, excludeGone = false) ?: continue
                daychangeCoordinator.changeDayIfNeeded(village)
            } catch (e: Exception) {
                logger.error("daychange failed for village {}", villageId, e)
            }
        }
    }
}
