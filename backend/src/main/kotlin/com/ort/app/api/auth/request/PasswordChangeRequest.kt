package com.ort.app.api.auth.request

import com.ort.app.fw.security.PasswordPolicy
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * パスワード変更リクエスト。緩和ポリシー ([PasswordPolicy])。
 * `password == confirmPassword` の相関チェックはコントローラ側で行う (不一致は 400)。
 *
 * 長さ制約は Jakarta 標準の [Size] を使う ([SignupRequest] と同じ理由 — SpringDoc が minLength/maxLength を出力する)。
 */
data class PasswordChangeRequest(
    @field:NotNull
    @field:Size(min = PasswordPolicy.MIN_LENGTH, max = PasswordPolicy.MAX_LENGTH)
    @field:Pattern(regexp = PasswordPolicy.PATTERN)
    val password: String? = null,
    @field:NotNull
    @field:Size(min = PasswordPolicy.MIN_LENGTH, max = PasswordPolicy.MAX_LENGTH)
    @field:Pattern(regexp = PasswordPolicy.PATTERN)
    val confirmPassword: String? = null,
)
