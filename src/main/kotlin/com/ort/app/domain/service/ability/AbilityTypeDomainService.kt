package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.fw.exception.WolfMansionBusinessException

interface AbilityTypeDomainService {

    val abilityType: AbilityType

    fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant>

    fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? =
        abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.firstOrNull()
            ?.let { village.participants.chara(it.targetCharaId!!) }

    fun isAvailableNoTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): Boolean = false

    fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String>

    fun assertAbility(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?,
        abilities: Abilities,
        footsteps: Footsteps,
        votes: Votes,
        defaultFootstepAsserter: () -> Unit
    ) {
        if (!isAvailableNoTarget(village, myself, abilities) && targetCharaId == null) {
            throw WolfMansionBusinessException("対象指定が必要です")
        }
        if (targetCharaId != null
            && getSelectableTargetList(village, myself, abilities, votes).none { it.charaId == targetCharaId }
        ) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        // 足音
        if (isTargetingAndFootstep() && targetCharaId != null) defaultFootstepAsserter.invoke()
    }

    fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String

    fun getTargetPrefix(): String? = null
    fun getTargetSuffix(): String? = null
    fun isTargetingAndFootstep(): Boolean = false
    fun canUseDay(day: Int): Boolean = true
}