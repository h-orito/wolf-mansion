package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class ExecuteDomainService(
    private val messageDomainService: MessageDomainService
) {

    fun execute(daychange: Daychange): Daychange {
        var village = daychange.village
        if (village.latestDay() < 3) return daychange

        val votes = daychange.votes
            .filterByDay(village.latestDay() - 1).list
            .filterNot { village.participants.chara(it.charaId).isDead() }
        if (votes.isEmpty()) return daychange // 全員突然死

        val votedCountMap = votes.groupBy { it.targetCharaId }
            .map { (targetCharaId, voteList) -> village.participants.chara(targetCharaId) to voteList.size }
            .toMap()
        val executedParticipant = decideExecutedParticipant(votedCountMap)

        village = village.executeParticipant(executedParticipant.id)

        var messages = daychange.messages.copy()
        messages = messages.add(createEachVoteMessage(village, votes))
        messages = messages.add(createExecuteMessage(village, votedCountMap, executedParticipant))

        return daychange.copy(village = village, messages = messages)
    }

    private fun decideExecutedParticipant(votedMap: Map<VillageParticipant, Int>): VillageParticipant {
        val maxVotedCount = votedMap.map { (_, votedCount) -> votedCount }.max()!!
        val candidates = votedMap
            .filter { (_, votedCount) -> votedCount == maxVotedCount }
            .map { (participant, _) -> participant }
        val nonLuckymanCandidates = candidates.filterNot { it.skill!!.toCdef() == CDef.Skill.強運者 }
        return if (nonLuckymanCandidates.isNotEmpty()) nonLuckymanCandidates.shuffled().first()
        else candidates.shuffled().first()
    }

    private fun createEachVoteMessage(village: Village, votes: List<Vote>): Message {
        val fromMaxLength = votes.map { village.participants.chara(it.charaId).name().length }.max()!!
        val toMaxLength = votes.map { village.participants.chara(it.targetCharaId).name().length }.max()!!
        val text = votes.sortedBy { village.participants.chara(it.charaId).room!!.number }
            .joinToString(
                separator = "\n",
                prefix = "投票結果は以下の通り。\n"
            ) {
                val from = village.participants.chara(it.charaId)
                val to = village.participants.chara(it.targetCharaId)
                "${from.name().padEnd(fromMaxLength, '　')} → ${to.name().padEnd(toMaxLength, '　')}"
            }
        val messageType =
            if (village.setting.rule.isOpenVote) CDef.MessageType.公開システムメッセージ.toModel()
            else CDef.MessageType.非公開システムメッセージ.toModel()
        return messageDomainService.createEachVoteMessage(village, text, messageType)
    }

    private fun createExecuteMessage(
        village: Village,
        votedCountMap: Map<VillageParticipant, Int>,
        executedParticipant: VillageParticipant
    ): Message {
        val text = votedCountMap.entries
            .sortedWith(
                compareByDescending<Map.Entry<VillageParticipant, Int>> { it.value }
                    .thenBy { it.key.room!!.number })
            .joinToString(
                separator = "\n",
                postfix = "\n\n${executedParticipant.name()}は村人達の手により処刑された。"
            ) { (participant, count) ->
                "${participant.name()}、${count}票"
            }
        return messageDomainService.createExecuteMessage(village, text)
    }
}
