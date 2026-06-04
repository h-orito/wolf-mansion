package com.ort.app.domain.model.village.setting

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.dbflute.allcommon.CDef

data class VillageOrganize(
    val fixedOrganization: String,
    val randomOrganization: VillageRandomOrganize
) {
    companion object {
        val defaultFixedOrganization = """
            村狼狼賢導村村村
            村狼狼賢導村村村村
            村狼狼狂賢導村村村村
            村狼狼狂賢導村村村村村
            村狼狼狼狂賢導狩村村村村
            村狼狼狼狂賢導狩村村村村村
            村狼狼狼魔狐賢導狩霊霊霊霊霊
            村狼狼狼魔狐賢導狩霊霊霊霊霊霊
            村狼狼狼魔狐賢導狩霊霊霊霊霊共共
            村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共
            村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊共共
            村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊共共
            村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊霊共共
        """.trimIndent()
    }

    fun allRequestableSkillList(): List<Skill> {
        val skillList = fixedOrganization.split("\n")
            .flatMap { it.split("") }
            .distinct()
            .mapNotNull { Skill.byShortName(it) }
        val someoneList = Skills.someones().list
        return skillList + someoneList
    }

    fun mapToSkillCount(isRandom: Boolean, participantsCount: Int): Map<CDef.Skill, Int> {
        return if (isRandom) randomOrganization.mapToSkillCount(participantsCount)
        else {
            val org = checkNotNull(
                fixedOrganization.replace("\r\n", "\n").split("\n").find { it.length == participantsCount })
            Skills.all().filterNotSomeone().list.map { skill ->
                val count = org.chunked(1).count { char -> char == skill.shortName }
                skill.toCdef() to count
            }.toMap()
        }
    }

    fun getReincarnationSkill(camp: Camp?): Skill? {
        return randomOrganization.getReincarnationSkill(camp)
    }
}