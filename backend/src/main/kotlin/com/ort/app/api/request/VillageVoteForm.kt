package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageVoteForm(
    @field:NotNull
    val targetCharaId: Int? = null
)