package com.ort.app.api.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class PlayerChangePasswordForm(
    /** 変更後パスワード  */
    @field:NotNull
    @field:Length(min = 3, max = 72)
    @field:Pattern(regexp = "[\\x20-\\x7E]*")
    val password: String? = null,

    /** 変更後確認パスワード  */
    @field:NotNull
    @field:Length(min = 3, max = 72)
    @field:Pattern(regexp = "[\\x20-\\x7E]*")
    val confirmPassword: String? = null
)