package com.ort.app.api.village.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class VillageDebugParticipateRequest(
    @field:NotNull @field:Min(1) @field:Max(50) val personNumber: Int? = null,
)
