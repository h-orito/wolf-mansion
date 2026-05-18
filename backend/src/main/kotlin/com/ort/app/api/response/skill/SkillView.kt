package com.ort.app.api.response.skill

import com.ort.app.domain.model.skill.Skill
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "役職 (軽量)")
data class SkillView(
    @field:Schema(description = "役職コード")
    val code: String,
    @field:Schema(description = "役職名")
    val name: String,
    @field:Schema(description = "1 文字略称")
    val shortName: String,
) {
    constructor(skill: Skill) : this(
        code = skill.code,
        name = skill.name,
        shortName = skill.shortName,
    )
}
