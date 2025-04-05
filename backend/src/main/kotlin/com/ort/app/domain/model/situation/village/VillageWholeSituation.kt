package com.ort.app.domain.model.situation.village

import com.ort.app.domain.model.village.participant.VillageParticipants

data class VillageWholeSituation(
    val list: List<VillageWholeDetail>
)

data class VillageWholeDetail(
    val day: Int,
    val suddenlyDeath: VillageParticipants,
    val executed: VillageParticipants,
    val miserable: VillageParticipants,
    val revival: VillageParticipants,
    val suicide: VillageParticipants,
    var ability: List<String>
)
