package com.ort.app.domain.service

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class RpDomainService {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        charachips: Charachips,
        day: Int
    ): ParticipantRpSituation = ParticipantRpSituation(
        isAvailableChangeName = isAvailableChangeName(village, myself, charachips, day),
        isAvailableMemo = isAvailableMemo(village, myself, day),
        canAddImage = canAddImage(village, myself, day)
    )

    private fun isAvailableChangeName(
        village: Village,
        myself: VillageParticipant?,
        charachips: Charachips,
        day: Int
    ): Boolean =
        village.canChangeName(day) &&
                myself != null &&
                myself.canChangeName(village.status.isEpilogue()) &&
                charachips.list.first { it.charas.list.any { chara -> chara.id == myself.charaId } }.isAvailableChangeName

    private fun isAvailableMemo(
        village: Village,
        myself: VillageParticipant?,
        day: Int
    ): Boolean =
        village.canChangeName(day) &&
                myself?.canChangeName(village.status.isEpilogue()) ?: false

    private fun canAddImage(
        village: Village,
        myself: VillageParticipant?,
        day: Int
    ): Boolean = village.canAddImage(day) && myself?.canAddImage() ?: false
}