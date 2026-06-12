package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull

/** 希望役職 (第 1/第 2) の変更。 */
data class VillageChangeSkillRequest(
    @field:NotNull
    val requestedSkill: String? = null,
    @field:NotNull
    val secondRequestedSkill: String? = null,
)
