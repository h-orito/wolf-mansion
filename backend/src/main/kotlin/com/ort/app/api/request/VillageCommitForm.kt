package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageCommitForm(
    /** コミットする/取り消す  */
    @field:NotNull
    val commit: Boolean? = null
)