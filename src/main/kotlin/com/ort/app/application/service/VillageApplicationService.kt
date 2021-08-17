package com.ort.app.application.service

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageRepository
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.fw.security.UserInfo
import org.springframework.stereotype.Service

@Service
class VillageApplicationService(
    private val villageRepository: VillageRepository
) {

    fun findLatestVillageId(
        statusList: List<VillageStatus>
    ): Int = villageRepository.findLatestVillageId(statusList)

    fun findVillages(
        statusList: List<VillageStatus>,
        idList: List<Int> = listOf()
    ): Villages = villageRepository.findVillages(statusList, idList)

    fun findVillage(
        villageId: Int,
        excludeGone: Boolean = true
    ): Village? = villageRepository.findVillage(villageId, excludeGone)

    fun findVillageParticipant(
        villageId: Int,
        userInfo: UserInfo,
        excludeGone: Boolean = true
    ): VillageParticipant? = villageRepository.findVillageParticipant(
        villageId = villageId,
        userName = userInfo.username,
        excludeGone = excludeGone
    )
}