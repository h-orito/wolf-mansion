package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class RandomKeywordForm(
    @field:NotNull
    @field:Length(min = 3, max = 10)
    @field:Pattern(regexp = "[a-zA-Z]*")
    val keyword: String? = null,

    @field:NotNull
    val message: String? = null
)