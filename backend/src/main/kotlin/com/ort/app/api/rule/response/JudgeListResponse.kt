package com.ort.app.api.rule.response

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills

enum class CountType {
    HUMAN,
    WOLF,
    NO_COUNT,
    ;

    companion object {
        fun of(skill: Skill): CountType =
            when {
                skill.isNoCount() -> NO_COUNT
                skill.isWolfCount() -> WOLF
                else -> HUMAN
            }
    }
}

private data class JudgeKey(
    val divineResultWolf: Boolean,
    val psychicResultWolf: Boolean,
    val noDeadByAttack: Boolean,
    val countType: CountType,
)

data class JudgeListResponse(
    val judges: List<JudgeView>,
) {
    constructor() : this(
        judges =
            Skills
                .all()
                .filterNotSomeone()
                .list
                .groupBy { skill ->
                    JudgeKey(
                        divineResultWolf = skill.isDivineResultWolf(),
                        psychicResultWolf = skill.isPsychicResultWolf(),
                        noDeadByAttack = skill.isNoDeadByAttack(),
                        countType = CountType.of(skill),
                    )
                }.map { (key, skills) ->
                    JudgeView(
                        skills = skills.map { JudgeSkillView(it) },
                        divineResultWolf = key.divineResultWolf,
                        psychicResultWolf = key.psychicResultWolf,
                        noDeadByAttack = key.noDeadByAttack,
                        countType = key.countType,
                    )
                },
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
