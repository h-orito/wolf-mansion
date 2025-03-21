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
class InsuranceDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.保険.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1
    override fun getTargetPrefix(): String? = "保険を勧める対象"
    override fun getTargetSuffix(): String? = "に保険を勧める"

    fun insurance(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.保険屋.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 保険を付与する
            village = village.insuranceParticipant(it.id, target.id)
            messages = messages.add(createInsuranceMessage(village, it, target))
            messages = messages.add(createInsurancedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createInsuranceMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}に保険を勧めた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createInsurancedMessage(
        village: Village,
        insurancer: VillageParticipant,
        myself: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "あなたは、${insurancer.name()}に保険を勧められた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}