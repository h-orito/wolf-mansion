package com.ort.app.api.rule.response

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills

enum class CountType(
    val label: String,
) {
    HUMAN("人間"),
    WOLF("人狼"),
    NO_COUNT("カウントしない"),
}

data class JudgeListResponse(
    val judges: List<JudgeView>,
) {
    constructor() : this(
        judges =
            listOf(false, true)
                .flatMap { divineResultWolf ->
                    listOf(false, true).flatMap { psychicResultWolf ->
                        listOf(false, true).flatMap { noDeadByAttack ->
                            CountType.entries.map { countType ->
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
                                                    when (countType) {
                                                        CountType.HUMAN -> !it.isWolfCount() && !it.isNoCount()
                                                        CountType.WOLF -> it.isWolfCount() && !it.isNoCount()
                                                        CountType.NO_COUNT -> it.isNoCount()
                                                    }
                                            }.map { JudgeSkillView(it) },
                                    divineResultWolf = divineResultWolf,
                                    psychicResultWolf = psychicResultWolf,
                                    noDeadByAttack = noDeadByAttack,
                                    countType = countType,
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
    val countType: CountType,
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
