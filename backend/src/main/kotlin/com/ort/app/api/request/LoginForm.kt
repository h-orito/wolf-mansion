package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class LoginForm(
    /** ユーザID  */
    @field:NotNull
    @field:Length(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z][a-zA-Z0-9\\-_]*")
    val userId: String? = null,

    /** パスワード  */
    @field:NotNull
    @field:Length(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z0-9]*")
    val password: String? = null,

    /** エラー有無  */
    val error: Boolean? = null
)