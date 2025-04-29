package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

data class VillageLeaveForm(
    @field:NotNull
    val villagePlayerId: Int? = null
)