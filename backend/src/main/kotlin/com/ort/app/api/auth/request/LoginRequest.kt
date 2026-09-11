package com.ort.app.api.auth.request

import jakarta.validation.constraints.NotNull

/**
 * ログインリクエスト。緩和後ポリシーに合わせ **password の形式バリデーションはしない** (NotNull のみ)。
 * 形式チェックを残すと緩和後パスワードでログイン不能になるため。
 */
data class LoginRequest(
    @field:NotNull
    val userId: String? = null,
    @field:NotNull
    val password: String? = null,
)
