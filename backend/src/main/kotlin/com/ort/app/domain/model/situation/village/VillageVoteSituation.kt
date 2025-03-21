package com.ort.app.domain.model.situation.village

import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Vote

data class VillageVoteSituation(val list: List<VillageMemberVotes>)

data class VillageMemberVotes(
    val participant: VillageParticipant,
    val voteList: List<Vote>
)
