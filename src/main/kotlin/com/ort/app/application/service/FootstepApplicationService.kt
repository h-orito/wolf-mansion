package com.ort.app.application.service

import com.ort.app.domain.model.footstep.FootstepRepository
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class FootstepApplicationService(
    private val footstepRepository: FootstepRepository
) {

    fun findFootsteps(villageId: Int): Footsteps = footstepRepository.findFootsteps(villageId)

    fun updateFootstep(
        village: Village,
        myself: VillageParticipant,
        footstepParticipant: VillageParticipant,
        footstep: String?
    ) =
        footstepRepository.updateFootstep(village, myself, footstepParticipant.charaId, footstep)

    fun updateDaychangeDifference(village: Village, current: Footsteps, changed: Footsteps) =
        footstepRepository.updateDaychangeDifference(village, current, changed)
}