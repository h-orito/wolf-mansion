package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class VillageFilterParticipantContent(
    val id: Int,
    val name: String,
    val imgWidth: Int,
    val imgHeight: Int,
    val imgUrl: String,
    val deadStatus: String?
) {
    constructor(
        village: Village,
        participant: VillageParticipant,
        charachips: Charachips
    ) : this(
        id = participant.id,
        name = participant.name(),
        imgWidth = charachips.chara(participant.charaId).size.width,
        imgHeight = charachips.chara(participant.charaId).size.height,
        imgUrl = charachips.chara(participant.charaId).defaultImage().url,
        deadStatus = mappingToDeadStatus(village, participant)
    )

    companion object {
        private fun mappingToDeadStatus(village: Village, participant: VillageParticipant): String? {
            if (participant.isSpectator) return "見学"
            if (participant.isAlive()) return "生存"
            return participant.dead.let {
                val reason = it.reason!!.getDisplayName(village.isSettled())
                "${it.deadDay}d${reason}"
            }
        }
    }
}
