package com.ort.app.domain.model.vote

import com.ort.app.domain.model.village.Village

interface VoteRepository {

    fun findVotes(villageId: Int): Votes

    fun updateVote(village: Village, vote: Vote)

    fun updateDatchangeDifference(villageId: Int, current: Votes, changed: Votes)
}