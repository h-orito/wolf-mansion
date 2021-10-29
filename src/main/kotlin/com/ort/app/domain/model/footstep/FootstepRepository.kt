package com.ort.app.domain.model.footstep

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

interface FootstepRepository {

    fun findFootsteps(villageId: Int): Footsteps

    fun updateFootstep(village: Village, myself: VillageParticipant, footstep: String?)

    fun updateDaychangeDifference(villageId: Int, current: Footsteps, changed: Footsteps)
}