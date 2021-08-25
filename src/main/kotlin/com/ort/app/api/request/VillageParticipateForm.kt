package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageParticipateForm(
    @field:NotNull
    val charaId: Int? = null,
    val requestedSkill: String? = null,
    val secondRequestedSkill: String? = null,
    @field:NotNull
    val joinMessage: String? = null,
    val joinPassword: String? = null,
    val spectator: Boolean? = null
)