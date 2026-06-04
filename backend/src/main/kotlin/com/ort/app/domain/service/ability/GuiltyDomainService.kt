package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
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
class GuiltyDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.濡衣)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        val day = village.latestDay()
        // 前日以前に2回以上能力行使していたらもう使えない
        val count = abilities.filterPastDay(day).filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.size
        return if (count >= 2) emptyList() else getAliveTargetsWithoutMyself(village, myself)
    }

    override fun getTargetPrefix(): String = "濡れ衣を着せる対象"
    override fun getTargetSuffix(): String = "に濡れ衣を着せる"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true

    fun guilty(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.濡衣者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createGuiltyMessage(village, it, target))
        }

        return daychange.copy(messages = messages)
    }

    private fun createGuiltyMessage(
        village: Village,
        guilter: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = guilter,
            text = "${guilter.name()}は、${target.name()}に濡れ衣を着せた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}