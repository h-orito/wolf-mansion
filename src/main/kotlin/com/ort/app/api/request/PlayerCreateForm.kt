package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class PlayerCreateForm(
    /** ユーザID  */
    @field:NotNull
    @field:Length(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z][a-zA-Z0-9\\-_]*")
    val userId: String? = null,

    /** パスワード  */
    @field:NotNull
    @field:Length(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z0-9]*")
    val password: String? = null
)