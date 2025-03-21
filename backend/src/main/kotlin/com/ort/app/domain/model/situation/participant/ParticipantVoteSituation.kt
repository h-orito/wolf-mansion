package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.village.participant.VillageParticipant

data class ParticipantVoteSituation(
    val canVote: Boolean,
    val targetList: List<VillageParticipant>,
    val target: VillageParticipant?
)