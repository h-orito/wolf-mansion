package com.ort.app.domain.model.skill

import com.ort.dbflute.allcommon.CDef

data class Skills(val list: List<Skill>) {
    companion object {
        fun all(): Skills = Skills(
            list = CDef.Skill.listAll().sortedBy { it.order().toInt() }.map { Skill(it) }
        )
    }

    fun filterByCamp(camp: CDef.Camp): Skills = copy(list = list.filter { it.code == camp.code() })

    fun filterNotSomeone(): Skills = copy(list = list.filterNot { it.toCdef().isSomeoneSkill })
}