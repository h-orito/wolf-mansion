package com.ort.app.domain.service

import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class RpDomainService {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        charachip: Charachip,
        day: Int
    ): ParticipantRpSituation = ParticipantRpSituation(
        isAvailableChangeName = isAvailableChangeName(village, myself, charachip, day),
        isAvailableMemo = isAvailableMemo(village, myself, day)
    )

    private fun isAvailableChangeName(
        village: Village,
        myself: VillageParticipant?,
        charachip: Charachip,
        day: Int
    ): Boolean =
        village.canChangeName(day) &&
                myself?.canChangeName(village.status.isEpilogue()) ?: false &&
                charachip.isAvailableChangeName

    private fun isAvailableMemo(
        village: Village,
        myself: VillageParticipant?,
        day: Int
    ): Boolean =
        village.canChangeName(day) &&
                myself?.canChangeName(village.status.isEpilogue()) ?: false
}