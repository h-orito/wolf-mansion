package com.ort.app.api.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class PlayerCreateForm(
    /** ユーザID  */
    @field:NotNull
    @field:Length(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z][a-zA-Z0-9\\-_]*")
    val userId: String? = null,

    /** パスワード  */
    // TODO: DB拡張して72文字までにしたい
    @field:NotNull
    @field:Length(min = 3, max = 72)
    @field:Pattern(regexp = "[\\x20-\\x7E]*")
    val password: String? = null
)