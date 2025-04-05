package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

data class VillageActionForm(
    @field:NotNull
    val myself: String? = null,

    val target: String? = null,

    @field:NotNull
    var message: String? = null,

    val convertDisable: Boolean? = null
)