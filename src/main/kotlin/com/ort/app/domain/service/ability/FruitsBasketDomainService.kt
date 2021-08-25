package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class FruitsBasketDomainService(
    private val roomDomainService: RoomDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.フルーツバスケット)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        val day = village.latestDay()
        if (day < 2) return emptyList()
        // 前日以前に能力行使していたらもう使えない
        return if (abilities.filterPastDay(day).filterByType(abilityType)
                .filterByCharaId(myself.charaId).list.isNotEmpty()
        ) {
            emptyList()
        } else listOf(myself)
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? {
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.firstOrNull()
            ?.let { village.participants.chara(it.targetCharaId!!) }
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
            .sortedByDay().list.map { "${it.day}日目 フルーツバスケット！" }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}がフルーツバスケットをやめました。"
        return "${myself.name()}がフルーツバスケットすることにしました。"
    }

    override fun getTargetPrefix(): String? = "発動させる場合自分を選択してください"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun fruitBasket(orgDaychange: Daychange): Daychange {
        var daychange = orgDaychange.copy()
        val village = daychange.village
        village.participants.filterAlive().filterBySkill(CDef.Skill.果実籠.toModel()).list.forEach {
            daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            daychange = roomDomainService.reAssign(daychange, it)
        }
        return daychange
    }
}