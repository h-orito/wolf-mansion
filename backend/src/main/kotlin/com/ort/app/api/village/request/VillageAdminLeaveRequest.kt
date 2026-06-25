package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull

data class VillageAdminLeaveRequest(
    @field:NotNull val villagePlayerId: Int? = null,
)
