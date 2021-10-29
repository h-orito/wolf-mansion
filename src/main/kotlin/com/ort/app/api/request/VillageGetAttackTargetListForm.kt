package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageGetAttackTargetListForm(
    @field:NotNull
    val villageId: Int? = null,
    @field:NotNull
    val charaId: Int? = null
)