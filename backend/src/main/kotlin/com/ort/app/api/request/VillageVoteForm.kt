package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

data class VillageVoteForm(
    @field:NotNull
    val targetCharaId: Int? = null
)