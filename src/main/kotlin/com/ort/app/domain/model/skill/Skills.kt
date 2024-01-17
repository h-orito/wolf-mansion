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
        val openSkills = listOf(CDef.Skill.勇者, CDef.Skill.絶対人狼)

        // 転生で割り当て可能な役職
        fun revivables(): Skills = Skills(
            list = all().filterNotSomeone().list.filterNot { notRevivableSkills.contains(it.toCdef()) }
        )

        // 役職希望で選択できる役職
        fun requestables(): Skills = Skills(
            list = all().list.filter { it.isRequestable() }
        )

        // 編成に含めることができる役職
        fun organizables(): Skills = Skills(
            list = all().filterNotSomeone().list.filter { it.isRequestable() }
        )

        fun getSkillListStr(): String =
            organizables().list.joinToString(separator = " / ") { "${it.name}:${it.shortName}" }
    }

    fun filterByCamp(camp: CDef.Camp): Skills = copy(list = list.filter { it.toCdef().campCode() == camp.code() })

    fun filterNotSomeone(): Skills = copy(list = list.filterNot { it.toCdef().isSomeoneSkill })

    fun filter(predicate: (Skill) -> Boolean): Skills = copy(list = list.filter { predicate(it) })
}