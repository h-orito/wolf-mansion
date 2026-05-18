package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "希望役職変更リクエスト")
data class VillageChangeRequestSkillBody(
    @field:NotNull
    @field:NotBlank
    @field:Schema(description = "第一希望役職コード")
    val requestedSkill: String,
    @field:NotNull
    @field:NotBlank
    @field:Schema(description = "第二希望役職コード")
    val secondRequestedSkill: String,
)
