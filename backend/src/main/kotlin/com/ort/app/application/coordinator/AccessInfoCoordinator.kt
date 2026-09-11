package com.ort.app.application.coordinator

import com.ort.app.application.service.NotificationService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class AccessInfoCoordinator(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val slackService: NotificationService,
) {
    fun registerAccessInfo(
        village: Village,
        myself: VillageParticipant,
        ipAddress: String,
    ) {
        villageService.addIpAddress(myself, ipAddress)

        // IPアドレスが重複している人がいたら通知
        if (!playerService.findPlayer(myself.playerId).shouldCheckAccessInfo) return

        val dummyParticipant = village.participants.list.firstOrNull { it.charaId == village.setting.chara.dummyCharaId }
        val isContain =
            village
                .allParticipants()
                .let { if (dummyParticipant != null) it.filterNotDummy(dummyParticipant) else it }
                .filterNotParticipant(myself)
                .list
                .flatMap { it.ipAddresses }
                .distinct()
                .contains(ipAddress)
        if (isContain) {
            slackService.notifyToDeveloperTextIfNeeded(village, "IPアドレス重複検出: $ipAddress")
        }
    }
}
