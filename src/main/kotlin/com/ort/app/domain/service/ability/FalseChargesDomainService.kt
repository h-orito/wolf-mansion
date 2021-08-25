package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.service.FootstepDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class FalseChargesDomainService(
    private val footstepDomainService: FootstepDomainService
) {

    fun falseCharges(daychange: Daychange): Daychange {
        val village = daychange.village
        val victims = village.participants.filterMiserable().list
        if (victims.isEmpty()) return daychange
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.冤罪者.toModel()).list.forEach {
            val target = victims.shuffled().first()
            val candidates = footstepDomainService.getCandidateList(village, it.charaId, target.charaId)
            footsteps = footsteps.add(
                Footstep(
                    day = village.latestDay() - 1,
                    charaId = it.charaId,
                    roomNumbers = candidates.shuffled().first()
                )
            )
        }

        return daychange.copy(footsteps = footsteps)
    }
}
