package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import org.springframework.stereotype.Service

@Service
class DisturbDomainService(
    private val footstepDomainService: FootstepDomainService
) {

    fun assertAbility(village: Village, footstep: String?) {
        footstepDomainService.assertDisturbFootstep(village, footstep)
    }

    fun getHistories(
        village: Village,
        myself: VillageParticipant,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        return footsteps
            .filterByCharaId(myself.charaId)
            .filterPastDay(day)
            .sortedByDay()
            .list.map {
                val footstep =
                    if (it.roomNumbers.isEmpty() || it.roomNumbers == "なし") "徘徊しない"
                    else "${it.roomNumbers}を徘徊する"
                "${it.day}日目 $footstep"
            }
    }

    fun createSetMessageText(village: Village, myself: VillageParticipant, footstep: String): String {
        return "${myself.name()}が通過する部屋を${footstep}に設定しました。"
    }

    fun addDefaultFootsteps(daychange: Daychange): Daychange {
        var footsteps = daychange.footsteps.copy()
        daychange.village.participants.filterAlive().list
            .filter { it.skill!!.hasDisturbAbility() }
            .forEach {
                val footstep = Footstep(
                    day = daychange.village.latestDay(),
                    charaId = it.charaId,
                    roomNumbers = "なし"
                )
                footsteps = footsteps.add(footstep)
            }
        return daychange.copy(footsteps = footsteps)
    }
}