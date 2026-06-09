package com.ort.app.api.rule.response

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills

data class JudgeListResponse(
    val judges: List<JudgeView>,
) {
    constructor() : this(
        judges =
            listOf(false, true)
                .flatMap { divineResultWolf ->
                    listOf(false, true).flatMap { psychicResultWolf ->
                        listOf(false, true).flatMap { noDeadByAttack ->
                            listOf("人間", "人狼", "カウントしない").map { count ->
                                JudgeView(
                                    skills =
                                        Skills
                                            .all()
                                            .filterNotSomeone()
                                            .list
                                            .filter {
                                                it.isDivineResultWolf() == divineResultWolf &&
                                                    it.isPsychicResultWolf() == psychicResultWolf &&
                                                    it.isNoDeadByAttack() == noDeadByAttack &&
                                                    when (count) {
                                                        "人間" -> !it.isWolfCount() && !it.isNoCount()
                                                        "人狼" -> it.isWolfCount() && !it.isNoCount()
                                                        "カウントしない" -> it.isNoCount()
                                                        else -> false
                                                    }
                                            }.map { JudgeSkillView(it) },
                                    divineResultWolf = divineResultWolf,
                                    psychicResultWolf = psychicResultWolf,
                                    noDeadByAttack = noDeadByAttack,
                                    count = count,
                                )
                            }
                        }
                    }
                }.filter { it.skills.isNotEmpty() },
    )
}

data class JudgeView(
    val skills: List<JudgeSkillView>,
    val divineResultWolf: Boolean,
    val psychicResultWolf: Boolean,
    val noDeadByAttack: Boolean,
    val count: String,
)

data class JudgeSkillView(
    val code: String,
    val name: String,
) {
    constructor(skill: Skill) : this(
        code = skill.code,
        name = skill.name,
    )
}
