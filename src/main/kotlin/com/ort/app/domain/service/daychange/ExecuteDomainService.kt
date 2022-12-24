package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
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

        // 処刑人数
        val executeCount = village.participants.filterAlive().filterBySkill(CDef.Skill.執行人.toModel()).list.size + 1
        // 投票を隠すか
        val existsBlackbox = daychange.abilities
            .filterByDay(village.latestDay() - 1)
            .filterByType(CDef.AbilityType.隠蔽.toModel())
            .list.any { village.participants.chara(it.charaId).isAlive() }
        // 革命中か
        val existsRevolution = daychange.abilities
            .filterByDay(village.latestDay() - 2)
            .filterByType(CDef.AbilityType.革命.toModel())
            .list.any { village.participants.chara(it.charaId).isAlive() }

        // 被投票数
        val votedCountMap = calculateVoteCount(village, votes, daychange.abilities)
        // 処刑される人
        val executedParticipants = decideExecutedParticipants(votedCountMap, executeCount, existsRevolution)

        // 王族に投票した人に不敬を付与
        votes.filter {
            village.participants.chara(it.targetCharaId).skill!!.toCdef() == CDef.Skill.王族
        }.forEach {
            val from = village.participants.chara(it.charaId)
            val to = village.participants.chara(it.targetCharaId)
            village = village.disrespect(to.id, from.id)
        }

        executedParticipants.forEach {
            village = village.executeParticipant(it.id)
        }

        var messages = daychange.messages.copy()
        messages = messages.add(createEachVoteMessage(village, votes, existsBlackbox))
        if (existsRevolution) {
            messages = messages.add(createRevolutionMessage(village))
        }
        messages = messages.add(createExecuteMessage(village, votedCountMap, executedParticipants, existsBlackbox))
        if (existsBlackbox) {
            messages = messages.add(createBlackboxMessage(village, executedParticipants))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun calculateVoteCount(
        village: Village,
        votes: List<Vote>,
        abilities: Abilities
    ): Map<VillageParticipant, Int> {
        val votedCountMap = votes.groupBy { it.targetCharaId }
            .map { (targetCharaId, voteList) -> village.participants.chara(targetCharaId) to voteList.size }
            .toMap().toMutableMap()
        // 市長・組長が投票した人は+1
        village.participants.filterAlive().list
            .filter { listOf(CDef.Skill.市長, CDef.Skill.組長).contains(it.skill!!.toCdef()) }
            .forEach { mayor ->
                val voteTargetCharaId = votes.first { it.charaId == mayor.charaId }.targetCharaId
                val voteTarget = village.participants.chara(voteTargetCharaId)
                votedCountMap[voteTarget] = votedCountMap[voteTarget]!!.plus(1)
            }
        // 弁護士が投票した人は-3
        village.participants.filterAlive().filterBySkill(CDef.Skill.弁護士.toModel()).list.forEach { lawyer ->
            val voteTargetCharaId = votes.first { it.charaId == lawyer.charaId }.targetCharaId
            val voteTarget = village.participants.chara(voteTargetCharaId)
            votedCountMap[voteTarget] = votedCountMap[voteTarget]!!.minus(3)
        }
        // 冷凍者が能力を行使した場合、冷凍者が投票した人は+529999
        village.participants.filterAlive().filterBySkill(CDef.Skill.冷凍者.toModel()).list.forEach { freezer ->
            abilities.findYesterday(village, freezer, CDef.AbilityType.戦闘力発揮.toModel()) ?: return@forEach
            val voteTargetCharaId = votes.first { it.charaId == freezer.charaId }.targetCharaId
            val voteTarget = village.participants.chara(voteTargetCharaId)
            votedCountMap[voteTarget] = votedCountMap[voteTarget]!!.plus(529999)
        }
        // 不敬が付与されている場合、被投票数が2倍になる
        village.participants.filterAlive().list.filter { it.status.isDisrespectful() }.forEach { participant ->
            if (votedCountMap[participant] != null) {
                votedCountMap[participant] = votedCountMap.getOrDefault(participant, 0) * 2
            }
        }

        return votedCountMap
    }

    private fun decideExecutedParticipants(
        votedMap: Map<VillageParticipant, Int>,
        executeCount: Int,
        existsRevolution: Boolean
    ): List<VillageParticipant> {
        val map = votedMap.filterNot { it.value <= 0 }.toMutableMap()
        val executed = mutableListOf<VillageParticipant>()
        repeat(executeCount) {
            decideExecutedParticipant(map, existsRevolution)?.let {
                executed.add(it)
                map.remove(it)
            }
        }
        return executed.toList()
    }

    private fun decideExecutedParticipant(
        votedMap: Map<VillageParticipant, Int>,
        existsRevolution: Boolean
    ): VillageParticipant? {
        // 革命中は少数決、そうでなければ多数決
        val votedCount =
            if (existsRevolution) votedMap.map { (_, count) -> count }.minOrNull()
            else votedMap.map { (_, count) -> count }.maxOrNull()
        votedCount ?: return null
        val candidates = votedMap
            .filter { (_, count) -> votedCount == count }
            .map { (participant, _) -> participant }
        val nonLuckymanCandidates = candidates.filterNot { it.skill!!.toCdef() == CDef.Skill.強運者 }
        return if (nonLuckymanCandidates.isNotEmpty()) nonLuckymanCandidates.shuffled().first()
        else candidates.shuffled().first()
    }

    private fun createEachVoteMessage(village: Village, votes: List<Vote>, existsBlackbox: Boolean): Message {
        val fromMaxLength = votes.maxOf { village.participants.chara(it.charaId).name().length }
        val toMaxLength = votes.maxOf { village.participants.chara(it.targetCharaId).name().length }
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
            if (village.setting.rule.isOpenVote && !existsBlackbox) CDef.MessageType.公開システムメッセージ.toModel()
            else CDef.MessageType.非公開システムメッセージ.toModel()
        return messageDomainService.createEachVoteMessage(village, text, messageType)
    }

    private fun createRevolutionMessage(village: Village): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "革命が成功し、本日は少数票の者を処刑することとなった。"
        )
    }

    private fun createExecuteMessage(
        village: Village,
        votedCountMap: Map<VillageParticipant, Int>,
        executedParticipants: List<VillageParticipant>,
        existsBlackbox: Boolean
    ): Message {
        val postfix = if (executedParticipants.isEmpty()) {
            "\n\n本日は処刑が行われなかった。"
        } else {
            "\n\n${executedParticipants.joinToString(separator = "、") { it.name() }}は村人達の手により処刑された。"
        }
        val text = votedCountMap.entries
            .sortedWith(
                compareByDescending<Map.Entry<VillageParticipant, Int>> { it.value }
                    .thenBy { it.key.room!!.number })
            .joinToString(
                separator = "\n",
                postfix = postfix
            ) { (participant, count) ->
                "${participant.name()}、${if (count >= 0) count else 0}票"
            }
        val messageType =
            if (existsBlackbox) CDef.MessageType.非公開システムメッセージ.toModel()
            else CDef.MessageType.公開システムメッセージ.toModel()
        return messageDomainService.createExecuteMessage(
            village = village,
            text = text,
            messageType = messageType
        )
    }

    private fun createBlackboxMessage(
        village: Village,
        executedParticipants: List<VillageParticipant>
    ): Message {
        val text = if (executedParticipants.isEmpty()) {
            "何者かに投票箱を隠されてしまったようだ。\n\n本日は処刑が行われなかった。"
        } else {
            "何者かに投票箱を隠されてしまったようだ。\n\n${executedParticipants.joinToString(separator = "、") { it.name() }}は村人達の手により処刑された。"
        }
        return messageDomainService.createExecuteMessage(
            village = village,
            text = text,
            messageType = CDef.MessageType.公開システムメッセージ.toModel()
        )
    }
}
