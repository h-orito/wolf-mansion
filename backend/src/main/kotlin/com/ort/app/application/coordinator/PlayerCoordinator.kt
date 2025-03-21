package com.ort.app.application.coordinator

import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRecords
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.Villages
import org.springframework.stereotype.Service

@Service
class PlayerCoordinator(
    private val villageService: VillageService
) {
    fun findPlayerRecords(player: Player): PlayerRecords {
        if (player.participateFinishedVillageIdList.isEmpty()) return PlayerRecords(player, Villages(listOf()))
        val villages = villageService.findVillages(
            query = VillageQuery(
                statuses = VillageStatus.settledStatusList.map { VillageStatus(it) },
                ids = player.participateFinishedVillageIdList
            )
        )
        return PlayerRecords(player, villages)
    }
}