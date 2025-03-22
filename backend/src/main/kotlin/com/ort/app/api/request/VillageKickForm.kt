package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

class VillageKickForm(
    @field:NotNull
    val charaId: Int? = null
)