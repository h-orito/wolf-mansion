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
class LoveStealDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.恋泥棒)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun getTargetPrefix(): String? = "心を盗む対象"
    override fun getTargetSuffix(): String? = "の心を盗む"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

    fun stealLove(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.泥棒猫.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 心を盗む
            village = village.stealLoveParticipant(target.id, it.id)
            messages = messages.add(createStealLoveMessage(village, it, target))
            messages = messages.add(createStolenLoveMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    fun createStealLoveMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}の心を盗んだ。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    fun createStolenLoveMessage(village: Village, thiefCat: VillageParticipant, myself: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${thiefCat.name()}に心を盗まれ、これまでの相手を忘れて恋をしてしまった。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}