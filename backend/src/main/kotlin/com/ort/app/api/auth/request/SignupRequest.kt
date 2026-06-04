package com.ort.app.api.auth.request

import com.ort.app.fw.security.PasswordPolicy
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

/**
 * 新規登録リクエスト。userId は現状維持 (3〜12文字 / 英数 + ハイフン・アンダーバー / 1文字目英字)、
 * password は緩和ポリシー ([PasswordPolicy])。
 */
data class SignupRequest(
    @field:NotNull
    @field:Length(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z][a-zA-Z0-9\\-_]*")
    val userId: String? = null,
    @field:NotNull
    @field:Length(min = PasswordPolicy.MIN_LENGTH, max = PasswordPolicy.MAX_LENGTH)
    @field:Pattern(regexp = PasswordPolicy.PATTERN)
    val password: String? = null,
)
