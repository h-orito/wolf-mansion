package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.toModel
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
        val victims = village.participants.filterDeadDay(village.latestDay()).filterMiserable().list
        if (victims.isEmpty()) return daychange
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.冤罪者.toModel()).list.forEach {
            // 冤罪者になった当日は足音を出さない
            if (village.latestDay() >= 2 && it.skillWhen(village.latestDay() - 1)
                    ?.toCdef() != CDef.Skill.冤罪者
            ) return@forEach

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
        village.participants.filterAlive().filterBySkill(CDef.Skill.濡衣者.toModel()).list.forEach {
            val ability =
                daychange.abilities.findYesterday(village, it, CDef.AbilityType.濡衣.toModel()) ?: return@forEach
            val from = village.participants.chara(ability.targetCharaId!!)
            val to = victims.shuffled().first()
            val candidates = footstepDomainService.getCandidateList(village, from.charaId, to.charaId)
            footsteps = footsteps.add(
                Footstep(
                    day = village.latestDay() - 1,
                    charaId = it.charaId, // 足音を出した人は濡衣者
                    roomNumbers = candidates.shuffled().first()
                )
            )
        }

        return daychange.copy(footsteps = footsteps)
    }
}
