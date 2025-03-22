package com.ort.app.api.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class VillageChangeNameForm(
    @field:NotBlank
    @field:Length(min = 1, max = 40)
    val name: String? = null,

    @field:NotBlank
    @field:Length(min = 1, max = 1)
    val shortName: String? = null
)