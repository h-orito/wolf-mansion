package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.model.vote.VoteRepository
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.exbhv.VoteBhv
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Vote as DbVote

@Repository
class VoteDataSource(
    private val voteBhv: VoteBhv
) : VoteRepository {

    override fun findVotes(villageId: Int): Votes {
        val voteList = voteBhv.selectList {
            it.query().setVillageId_Equal(villageId)
        }
        return mapVotes(voteList)
    }

    override fun updateVote(village: Village, vote: Vote) {
        deleteVote(village, vote)
        insertVote(village.id, vote)
    }

    override fun updateDatchangeDifference(villageId: Int, current: Votes, changed: Votes) {
        changed.list.filterNot { changedVote ->
            current.list.any { currentVote -> changedVote.day == currentVote.day && changedVote.charaId == currentVote.charaId }
        }.forEach { insertVote(villageId, it) }
    }

    private fun deleteVote(village: Village, vote: Vote) {
        voteBhv.queryDelete {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(vote.day)
            it.query().setCharaId_Equal(vote.charaId)
        }
    }

    private fun insertVote(villageId: Int, vote: Vote) {
        val v = DbVote()
        v.villageId = villageId
        v.day = vote.day
        v.charaId = vote.charaId
        v.voteCharaId = vote.targetCharaId
        voteBhv.insert(v)
    }

    private fun mapVotes(voteList: List<DbVote>): Votes = Votes(list = voteList.map { mapVote(it) })

    private fun mapVote(vote: DbVote): Vote =
        Vote(day = vote.day, charaId = vote.charaId, targetCharaId = vote.voteCharaId)
}