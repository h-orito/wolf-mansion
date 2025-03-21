package com.ort.app.application.service

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityRepository
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class AbilityService(
    private val abilityRepository: AbilityRepository
) {

    fun findAbilities(villageId: Int): Abilities = abilityRepository.findAbilities(villageId)

    fun updateAbility(
        village: Village,
        myself: VillageParticipant,
        attackerCharaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ) {
        val abilityType = myself.skill?.getAbility() ?: return
        val ability = Ability(
            day = village.latestDay(),
            type = abilityType,
            charaId = myself.charaId,
            attackerCharaId = attackerCharaId,
            targetCharaId = targetCharaId,
            targetFootstep = footstep
        )
        abilityRepository.updateAbility(village, ability)
    }

    fun updateDaychangeDifference(village: Village, current: Abilities, changed: Abilities) =
        abilityRepository.updateDaychangeDifference(village, current, changed)
}