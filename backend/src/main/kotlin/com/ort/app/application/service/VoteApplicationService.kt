package com.ort.app.application.service

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.model.vote.VoteRepository
import com.ort.app.domain.model.vote.Votes
import org.springframework.stereotype.Service

@Service
class VoteApplicationService(
    private val voteRepository: VoteRepository
) {

    fun findVotes(villageId: Int): Votes = voteRepository.findVotes(villageId)

    fun updateVote(village: Village, vote: Vote) = voteRepository.updateVote(village, vote)

    fun updateDaychangeDifference(villageId: Int, current: Votes, changed: Votes) =
        voteRepository.updateDatchangeDifference(villageId, current, changed)
}