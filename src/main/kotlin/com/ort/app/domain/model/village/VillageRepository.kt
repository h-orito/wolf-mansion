package com.ort.app.domain.model.village

import com.ort.app.domain.model.village.participant.VillageParticipant

interface VillageRepository {

    fun findLatestVillageId(
        statusList: List<VillageStatus>
    ): Int

    fun findVillages(
        statusList: List<VillageStatus>,
        idList: List<Int>
    ): Villages

    fun findVillage(
        id: Int,
        excludeGone: Boolean
    ): Village?

    fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean
    ): VillageParticipant?

    fun findVillageParticipant(
        villageId: Int,
        userName: String,
        excludeGone: Boolean
    ): VillageParticipant?
}