package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class RunawayDomainService(
    private val roomDomainService: RoomDomainService,
    private val forceReincarnationDomainService: ForceReincarnationDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.暴走転生.toModel()

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
        else "暴走する！"
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}が暴走しないことにしました。"
        return "${myself.name()}が暴走することにしました。"
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
            .sortedByDay().list.map { "${it.day}日目 暴走！" }
    }

    override fun getTargetPrefix(): String? = "暴走する場合自分を選択してください"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun runaway(orgDaychange: Daychange): Daychange {
        var daychange = orgDaychange.copy()
        daychange.village.participants.filterAlive()
            .filterBySkill(CDef.Skill.暴走トラック.toModel()).list.forEach { track ->
                daychange.abilities.findYesterday(daychange.village, track, abilityType) ?: return@forEach
                val roomList = roomDomainService.detectHishaRoomNumbers(track.room!!, daychange.village.roomSize!!)
                // 直線上の全ての生存者が対象
                val targets = daychange.village.participants
                    .filterAlive()
                    .filterNotParticipant(track)
                    .sortedByRoomNumber().list
                    .filter { roomList.contains(it.room!!.number) }
                targets.forEach { target ->
                    daychange = forceReincarnationDomainService.forceReincarnationTarget(daychange, target)
                }
            }

        return daychange
    }
}