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

    fun updateFootstep(village: Village, myself: VillageParticipant, footstep: String?) =
        footstepRepository.updateFootstep(village, myself, footstep)

    fun updateDaychangeDifference(villageId: Int, current: Footsteps, changed: Footsteps) =
        footstepRepository.updateDaychangeDifference(villageId, current, changed)
}