package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.service.FootstepDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SleepwalkDomainService(
    private val footstepDomainService: FootstepDomainService
) {

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().list.filter {
            val skill = it.skill!!.toCdef()
            skill == CDef.Skill.夢遊病者 || skill == CDef.Skill.リア充
        }.forEach { walker ->
            val target = village.participants.list.filterNot { it.id == walker.id }.shuffled().first()
            val footstep = Footstep(
                day = village.latestDay(),
                charaId = walker.charaId,
                roomNumbers = footstepDomainService.getCandidateList(village, walker.charaId, target.charaId).shuffled()
                    .first()
            )
            footsteps = footsteps.add(footstep)
        }
        return daychange.copy(footsteps = footsteps)
    }
}