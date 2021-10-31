package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class VillageFilterParticipantContent(
    val id: Int,
    val name: String,
    val imgWidth: Int,
    val imgHeight: Int,
    val imgUrl: String,
    val deadStatus: String
) {
    constructor(
        village: Village,
        participant: VillageParticipant,
        charas: Charas
    ) : this(
        id = participant.id,
        name = participant.name(),
        imgWidth = charas.chara(participant.charaId).size.width,
        imgHeight = charas.chara(participant.charaId).size.height,
        imgUrl = charas.chara(participant.charaId).defaultImage().url,
        deadStatus = mappingToDeadStatus(village, participant)
    )

    companion object {
        private fun mappingToDeadStatus(village: Village, participant: VillageParticipant): String {
            if (participant.isSpectator) return " "
            if (participant.isAlive()) return " "
            return participant.dead.let {
                val reason = it.reason!!.getDisplayName(village.isSettled())
                "${it.deadDay}d${reason}"
            }
        }
    }
}
