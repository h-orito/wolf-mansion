package com.ort.app.api.request

import org.hibernate.validator.constraints.Length

data class VillageMemoForm(
    @field:Length(max = 20)
    val memo: String? = null
)