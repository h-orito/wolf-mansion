package com.ort.app.domain.model.ability

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class Abilities(val list: List<Ability>) {

    fun filterByDay(day: Int): Abilities = copy(list = list.filter { it.day == day })

    fun filterPastDay(day: Int): Abilities = copy(list = list.filter { it.day < day })

    fun filterByType(type: AbilityType): Abilities = copy(list = list.filter { it.type.code == type.code })

    fun filterByCharaId(charaId: Int): Abilities = copy(list = list.filter { it.charaId == charaId })

    fun sortedByDay(): Abilities = copy(list = list.sortedBy { it.day })

    fun isSame(other: Abilities): Boolean {
        return list.size == other.list.size
                && list.all { ability -> other.list.any { otherAbility -> ability.isSame(otherAbility) } }
    }

    fun add(ability: Ability): Abilities = copy(list = list + ability)

    fun findYesterday(
        village: Village,
        participant: VillageParticipant,
        type: AbilityType
    ): Ability? = filterByDay(village.latestDay() - 1)
        .filterByCharaId(participant.charaId)
        .filterByType(type)
        .list.firstOrNull()
}