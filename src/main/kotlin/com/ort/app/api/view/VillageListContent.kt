package com.ort.app.api.view

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages

data class VillageListContent(
    val villageList: List<VillageListVillage>
) {
    constructor(villages: Villages) : this(villageList = villages.list.reversed().map { VillageListVillage(it) })

    data class VillageListVillage(
        val villageId: Int,
        val villageNumber: String,
        val villageName: String,
        val participateNum: String,
        val status: String
    ) {
        constructor(village: Village) : this(
            villageId = village.id,
            villageNumber = village.id.toString().padStart(4, '0'),
            villageName = village.name,
            participateNum = mapParticipateNum(village),
            status = village.status.name
        )

        companion object {
            private fun mapParticipateNum(village: Village): String {
                val participantCount = village.participants.count
                val spectatorCount = village.spectators.count
                return if (spectatorCount == 0) {
                    "${participantCount}人"
                } else {
                    "$participantCount ($spectatorCount)人"
                }
            }
        }
    }
}