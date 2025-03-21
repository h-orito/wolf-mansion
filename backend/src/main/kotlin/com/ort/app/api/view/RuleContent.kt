package com.ort.app.api.view

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.camp.Camps
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills

data class RuleContent(
    val campList: List<CampContent>,
    val judgeList: List<JudgeContent>
) {

    data class CampContent(
        val code: String,
        val name: String,
        val skillList: List<SkillContent>
    ) {
        constructor(camp: Camp) : this(
            code = camp.code.lowercase(),
            name = camp.name,
            skillList = Skills.all().filterNotSomeone().filterByCamp(camp.toCdef()).list.map { SkillContent(it) }
        )
    }

    data class JudgeContent(
        val skillList: List<SkillContent>,
        val divineResultWolf: Boolean,
        val psychicResultWolf: Boolean,
        val noDeadByAttack: Boolean,
        val count: String
    )

    data class SkillContent(
        val code: String,
        val name: String
    ) {
        constructor(
            skill: Skill
        ) : this(
            code = skill.code.lowercase(),
            name = skill.name
        )
    }

    constructor() : this(
        campList = Camps.all().list.map { CampContent(it) },
        judgeList = listOf(false, true).flatMap { divineResultWolf -> // 占結果
            listOf(false, true).flatMap { psychicResultWolf -> // 霊結果
                listOf(false, true).flatMap { noDeadByAttack -> // 襲撃耐性
                    listOf("人間", "人狼", "カウントしない").map { count ->
                        JudgeContent(
                            skillList = Skills.all().filterNotSomeone().list.filter {
                                it.isDivineResultWolf() == divineResultWolf &&
                                        it.isPsychicResultWolf() == psychicResultWolf &&
                                        it.isNoDeadByAttack() == noDeadByAttack &&
                                        when (count) {
                                            "人間" -> !it.isWolfCount() && !it.isNoCount()
                                            "人狼" -> it.isWolfCount() && !it.isNoCount()
                                            "カウントしない" -> it.isNoCount()
                                            else -> false
                                        }
                            }.map { SkillContent(it) },
                            divineResultWolf = divineResultWolf,
                            psychicResultWolf = psychicResultWolf,
                            noDeadByAttack = noDeadByAttack,
                            count = count
                        )
                    }
                }
            }
        }.filter { it.skillList.isNotEmpty() }
    )
}