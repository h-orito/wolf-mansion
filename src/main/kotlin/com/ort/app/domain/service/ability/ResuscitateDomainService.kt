package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
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
}
