package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class HiyasichukaDomainService : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.冷やし中華)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        return if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) emptyList()
        else listOf(myself)
    }

    override fun getSelectingTargetMessage(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): String? {
        return if (getSelectingTarget(village, myself, abilities) == null) "何もしない"
        else "夏を始める"
    }

    override fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        return abilities
            .filterPastDay(day)
            .filterByCharaId(myself.charaId)
            .filterByType(abilityType)
            .sortedByDay().list.map { "${it.day}日目 夏の始まり" }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}が始まることにしました。"
        return "${myself.name()}が始まるのをやめました。"
    }

    override fun getTargetPrefix(): String? = "始まる場合自分を選択してください"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true

    fun start(daychange: Daychange): Daychange {
        var messages = daychange.messages.copy()
        daychange.village.participants.filterAlive().filterBySkill(CDef.Skill.冷やし中華.toModel()).list.shuffled()
            .forEach {
                daychange.abilities.findYesterday(daychange.village, it, abilityType) ?: return@forEach
                messages = messages.add(createMessage(daychange.village, it))
            }

        return daychange.copy(messages = messages)
    }

    private fun createMessage(village: Village, myself: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${myself.name()}は、始まった。"
        )
    }
}