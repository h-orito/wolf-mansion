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
class ResuscitateDomainService : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.蘇生.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 一度使うと使えない
        return if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) emptyList()
        else village.participants
            .filterDead()
            .filterNotDummy(village.dummyParticipant())
            .sortedByRoomNumber().list
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 2
    override fun getTargetPrefix(): String = "蘇生する対象"
    override fun getTargetSuffix(): String? = "を蘇生する"

    fun resuscitate(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.蘇生者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 蘇生済み/同棲者の場合は失敗
            if (target.isAlive() || target.skill!!.toCdef() == CDef.Skill.同棲者) return@forEach
            village = village.reviveParticipant(target.id)
            messages = messages.add(createResusciateMessage(village, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createResusciateMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、不思議な力により蘇った。"
        )
    }
}
