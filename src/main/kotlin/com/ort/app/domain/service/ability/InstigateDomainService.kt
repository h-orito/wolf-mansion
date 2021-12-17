package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class InstigateDomainService : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.煽動)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 当日に能力行使していたらもう使えない
        if (abilities.filterByDay(village.latestDay()).filterByType(abilityType)
                .filterByCharaId(myself.charaId).list.isNotEmpty()
        ) {
            return listOf()
        }
        return village.participants.filterAlive().sortedByRoomNumber().list
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? = null

    override fun getSelectingTargetMessage(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): String? = null

    override fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        return abilities
            .filterPastDay(day)
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId)
            .sortedByDay().list
            .map {
                val abilityDay = it.day
                val target = village.participants.chara(it.targetCharaId!!)
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を指差す"
            }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val target = village.participants.chara(targetCharaId!!)
        return "${myself.name()}は、${target.name()}を指差した。"
    }

    override fun getTargetSuffix(): String? = "を指差す"
    override fun canUseDay(day: Int): Boolean = day > 1
}
