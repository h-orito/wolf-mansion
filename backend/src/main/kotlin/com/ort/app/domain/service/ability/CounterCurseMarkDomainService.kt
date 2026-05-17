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
class CounterCurseMarkDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.反呪.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String? = "反呪符を貼り付ける対象"
    override fun getTargetSuffix(): String? = "に貼り付ける"

    fun couterCurse(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.反呪者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            village = village.addCounterCurseMark(target.id)
            messages = messages.add(createCounterCurseMarkMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createCounterCurseMarkMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${target.name()}に反呪符を貼り付けた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}
