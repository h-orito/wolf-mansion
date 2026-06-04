package com.ort.app.domain.model.situation

import com.ort.app.domain.model.situation.village.VillageFootstepSituation
import com.ort.app.domain.model.situation.village.VillageParticipantLiveSituation
import com.ort.app.domain.model.situation.village.VillageRoomAssignedSituation
import com.ort.app.domain.model.situation.village.VillageVoteSituation
import com.ort.app.domain.model.situation.village.VillageWholeSituation

data class VillageSituation(
    val roomAssigned: VillageRoomAssignedSituation,
    val live: VillageParticipantLiveSituation,
    val footstep: VillageFootstepSituation,
    val vote: VillageVoteSituation,
    val whole: VillageWholeSituation
)