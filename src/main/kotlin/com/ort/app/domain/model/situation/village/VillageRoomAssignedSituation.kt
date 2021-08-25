package com.ort.app.domain.model.situation.village

import com.ort.app.domain.model.village.participant.VillageParticipant

data class VillageRoomAssignedSituation(val columns: List<VillageRoomAssignedColumn>)

data class VillageRoomAssignedColumn(val rows: List<VillageParticipant?>)
