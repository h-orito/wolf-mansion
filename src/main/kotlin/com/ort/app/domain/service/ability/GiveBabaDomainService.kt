package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class GiveBabaDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.ババを渡す.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // ババになったことがない人
        return village.participants
            .filterAlive()
            .sortedByRoomNumber()
            .filterNotDummy(village.dummyParticipant()).list
            .filterNot {
                it.skill!!.histories.list.any { h -> h.skill.toCdef() == CDef.Skill.ババ }
            }
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String = "ババを押し付ける対象"
    override fun getTargetSuffix(): String = "にババを押し付ける"

    fun giveBaba(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.ババ.toModel()).list.shuffled().forEach { baba ->
            val ability = daychange.abilities.findYesterday(village, baba, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createGiveBabaMessage(village, baba, target))

            // 既に死亡していたり同棲者の場合は交換しない
            if (target.isDead() || target.skill!!.toCdef() == CDef.Skill.同棲者) return@forEach
            // 役職交換
            val targetSkill = target.skill
            village = village.assignParticipantSkill(baba.id, targetSkill)
            village = village.assignParticipantSkill(target.id, CDef.Skill.ババ.toModel())
            messages = messages.add(createGivenBabaMessage(village, target))
        }

        return daychange.copy(messages = messages, village = village)
    }

    private fun createGiveBabaMessage(
        village: Village,
        baba: VillageParticipant,
        target: VillageParticipant
    ): Message {
        val text = when {
            target.isDead() -> "${target.name()}は死亡しているため、${baba.name()}はババを押し付けられなかった。"
            target.skill!!.toCdef() == CDef.Skill.同棲者 -> "${target.name()}は同棲者のため、${baba.name()}はババを押し付けられなかった。"
            target.skill.toCdef() == CDef.Skill.絶対人狼 -> "${target.name()}は絶対人狼のため、${baba.name()}はババを押し付けられなかった。"
            else -> "${baba.name()}は、${target.name()}にババを押し付けた。"
        }
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = baba,
            text = text,
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    private fun createGivenBabaMessage(
        village: Village,
        target: VillageParticipant
    ): Message {

        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = target,
            text = "${target.name()}は、ババを押し付けられた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}