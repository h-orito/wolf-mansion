package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

internal fun getOnlyOneTimeAliveTargets(
    village: Village,
    myself: VillageParticipant,
    abilities: Abilities,
    abilityType: AbilityType
): List<VillageParticipant> {
    // 一度使うと使えない
    return if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) emptyList()
    else village.participants
        .filterAlive()
        .filterNotDummy(village.dummyParticipant())
        .filterNotParticipant(myself)
        .sortedByRoomNumber().list
}

internal fun getAliveTargetsWithoutMyself(
    village: Village,
    myself: VillageParticipant
): List<VillageParticipant> = village.participants
    .filterAlive()
    .filterNotParticipant(myself)
    .sortedByRoomNumber().list

internal fun getAliveTargets(village: Village): List<VillageParticipant> =
    village.participants
        .filterAlive()
        .sortedByRoomNumber().list

internal fun hasAlreadyUseAbility(
    village: Village,
    myself: VillageParticipant,
    abilities: Abilities,
    abilityType: AbilityType
): Boolean = abilities
    .filterByType(abilityType)
    .filterByCharaId(myself.charaId)
    .filterPastDay(village.latestDay())
    .list.isNotEmpty()
