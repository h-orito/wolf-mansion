package com.ort.app.domain.service

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.situation.participant.ParticipantVoteSituation
import com.ort.app.domain.model.situation.village.VillageMemberVotes
import com.ort.app.domain.model.situation.village.VillageVoteSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.model.vote.Votes
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class VoteDomainService {

    fun convertToVillageSituation(
        village: Village,
        votes: Votes,
        day: Int
    ): VillageVoteSituation {
        return VillageVoteSituation(
            list = village.participants
                .filterNotDummy(village.dummyParticipant())
                .list.map {
                    VillageMemberVotes(
                        participant = it,
                        voteList = votes
                            .filterByCharaId(it.charaId)
                            .filterPastDay(day).list
                            .sortedBy { v -> v.day }
                    )
                }.sortedByDescending { it.voteList.size }
        )
    }

    fun convertToParticipantSituation(
        village: Village,
        myself: VillageParticipant?,
        votes: Votes
    ): ParticipantVoteSituation {
        return ParticipantVoteSituation(
            canVote = canVote(village, myself),
            targetList = getSelectableTargetList(village, myself),
            target = getSelectingTarget(village, myself, votes)
        )
    }

    private fun canVote(village: Village, myself: VillageParticipant?): Boolean =
        village.canVote() && myself?.canVote() ?: false

    fun getSelectableTargetList(village: Village, myself: VillageParticipant?): List<VillageParticipant> {
        if (!canVote(village, myself)) return listOf()
        return village.participants.filterAlive().sortedByRoomNumber().list
    }

    fun getSelectingTarget(village: Village, myself: VillageParticipant?, votes: Votes): VillageParticipant? {
        if (!canVote(village, myself)) return null
        return votes.filterByDay(village.latestDay()).list
            .find { it.charaId == myself!!.charaId }
            ?.let { village.participants.chara(it.targetCharaId) }
    }

    fun assertVote(village: Village, myself: VillageParticipant, targetCharaId: Int) {
        if (getSelectableTargetList(village, myself).none { it.charaId == targetCharaId }) {
            throw WolfMansionBusinessException("投票できない対象に投票しています")
        }
    }

    fun addDefaultVotes(daychange: Daychange): Daychange {
        if (daychange.village.setting.rule.isAvailableSuddenlyDeath) return daychange
        var votes = daychange.votes.copy()
        daychange.village.participants.filterAlive().list.forEach {
            votes = votes.add(
                Vote(
                    day = daychange.village.latestDay(),
                    charaId = it.charaId,
                    targetCharaId = it.charaId
                )
            )
        }
        return daychange.copy(votes = votes)
    }
}