package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageChangeRequestSkillForm(
    @field:NotNull
    val requestedSkill: String? = null,
    @field:NotNull
    val secondRequestedSkill: String? = null
)