package com.ort.app.api.auth.request

import com.ort.app.fw.security.PasswordPolicy
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

/**
 * パスワード変更リクエスト。緩和ポリシー ([PasswordPolicy])。
 * `password == confirmPassword` の相関チェックはコントローラ側で行う (不一致は 400)。
 */
data class PasswordChangeRequest(
    @field:NotNull
    @field:Length(min = PasswordPolicy.MIN_LENGTH, max = PasswordPolicy.MAX_LENGTH)
    @field:Pattern(regexp = PasswordPolicy.PATTERN)
    val password: String? = null,
    @field:NotNull
    @field:Length(min = PasswordPolicy.MIN_LENGTH, max = PasswordPolicy.MAX_LENGTH)
    @field:Pattern(regexp = PasswordPolicy.PATTERN)
    val confirmPassword: String? = null,
)
