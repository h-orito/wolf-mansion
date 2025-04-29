package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.skill.toModel
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class FantasistDomainService {

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        var footsteps = daychange.footsteps.copy()
        daychange.village.participants.filterAlive().filterBySkill(CDef.Skill.妄想癖.toModel()).list.forEach {
            val footstep = Footstep(
                day = daychange.village.latestDay(),
                charaId = it.charaId,
                roomNumbers = it.room!!.number.toString()
            )
            footsteps = footsteps.add(footstep)
        }
        return daychange.copy(footsteps = footsteps)
    }
}