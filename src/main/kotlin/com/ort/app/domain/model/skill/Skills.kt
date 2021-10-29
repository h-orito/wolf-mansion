package com.ort.app.domain.model.skill

import com.ort.dbflute.allcommon.CDef

data class Skills(val list: List<Skill>) {
    companion object {
        fun all(): Skills = Skills(
            list = CDef.Skill.listAll().sortedBy { it.order().toInt() }.map { Skill(it) }
        )

        fun someones(): Skills = Skills(
            list = CDef.Skill.listOfSomeoneSkill().sortedBy { it.order().toInt() }.map { Skill(it) }
        )

        private val notRevivableSkills = listOf(CDef.Skill.同棲者, CDef.Skill.恋人)

        fun revivables(): Skills = Skills(
            list = all().filterNotSomeone().list.filterNot { notRevivableSkills.contains(it.toCdef()) }
        )
    }

    fun filterByCamp(camp: CDef.Camp): Skills = copy(list = list.filter { it.toCdef().campCode() == camp.code() })

    fun filterNotSomeone(): Skills = copy(list = list.filterNot { it.toCdef().isSomeoneSkill })
}