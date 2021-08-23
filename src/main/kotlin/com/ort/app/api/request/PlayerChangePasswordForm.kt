package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class PlayerChangePasswordForm(
    /** 変更後パスワード  */
    @NotNull
    @Length(min = 3, max = 12)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    val password: String? = null,

    /** 変更後確認パスワード  */
    @NotNull
    @Length(min = 3, max = 12)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    val confirmPassword: String? = null
)