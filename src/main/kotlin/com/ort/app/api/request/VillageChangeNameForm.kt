package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull

data class VillageChangeNameForm(
    @field:NotNull
    @field:Length(min = 1, max = 40)
    val name: String? = null,

    @field:NotNull
    @field:Length(min = 1, max = 1)
    val shortName: String? = null
)