package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

data class VillageCommitForm(
    /** コミットする/取り消す  */
    @field:NotNull
    val commit: Boolean? = null
)