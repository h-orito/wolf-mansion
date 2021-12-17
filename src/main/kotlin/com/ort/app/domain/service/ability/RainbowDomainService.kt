package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class RainbowDomainService : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.虹塗り.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getAliveTargets(village)

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String = "虹色に塗る対象"
    override fun getTargetSuffix(): String? = "を虹色に塗る"

    fun rainbow(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.虹職人.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createRainbowMessage(village, target))
        }

        return daychange.copy(messages = messages)
    }

    private fun createRainbowMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、虹色になった。"
        )
    }
}