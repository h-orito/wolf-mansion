package com.ort.app.domain.model.ability

import com.ort.app.domain.model.village.Village

interface AbilityRepository {

    fun findAbilities(villageId: Int): Abilities

    fun updateAbility(
        village: Village,
        ability: Ability
    )

    fun updateDaychangeDifference(village: Village, current: Abilities, changed: Abilities)
}