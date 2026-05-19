package com.ort.app.api.request.player

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * パスワード変更リクエスト。
 *
 * 旧 `PlayerChangePasswordForm` の置き換え。確認用パスワードとの一致は
 * controller 側で検証する (validator を別途立てずに済ませる)。
 *
 * NOTE: 旧実装と同じく現パスワードの再入力は要求しない。JWT cookie 経由で認証済みの
 * ユーザのみアクセスできる前提に立っている。
 */
@Schema(description = "パスワード変更リクエスト")
data class PlayerPasswordBody(
    @field:NotBlank
    @field:Size(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z0-9]*")
    @field:Schema(description = "新しいパスワード (英数字 3〜12 文字)")
    val password: String,
    @field:NotBlank
    @field:Size(min = 3, max = 12)
    @field:Pattern(regexp = "[a-zA-Z0-9]*")
    @field:Schema(description = "確認用パスワード (password と一致する必要がある)")
    val confirmPassword: String,
)
