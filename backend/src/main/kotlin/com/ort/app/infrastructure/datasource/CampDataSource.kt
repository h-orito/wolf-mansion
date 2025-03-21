package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.camp.CampRepository
import com.ort.app.domain.model.camp.CampSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.cbean.CampCB
import com.ort.dbflute.cbean.SkillCB
import com.ort.dbflute.exbhv.CampBhv
import org.springframework.stereotype.Repository

@Repository
class CampDataSource(
    private val campBhv: CampBhv
) : CampRepository {

    override fun findCampSkills(): List<CampSkill> {
        val campList = campBhv.selectList { cb: CampCB ->
            cb.query().addOrderBy_CampCode_Asc()
        }
        campBhv.loadSkill(campList) { skillCB: SkillCB ->
            skillCB.query().addOrderBy_DispOrder_Asc()
            skillCB.query().setSkillCode_NotInScope_AsSkill(CDef.Skill.listOfSomeoneSkill())
        }
        return campList.map {
            CampSkill(
                camp = Camp(it.campCodeAsCamp),
                skillList = it.skillList.map { Skill(it.skillCodeAsSkill) }
            )
        }
    }
}