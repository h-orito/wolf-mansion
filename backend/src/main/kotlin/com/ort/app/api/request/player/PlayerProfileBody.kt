package com.ort.app.api.request.player

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

/**
 * プロフィール (Twitter ユーザ名 / 自己紹介) 更新リクエスト。
 *
 * 旧 `UserDetailForm` の置き換え。null 指定で各フィールドをクリアできる。
 */
// NOTE: `nullable = true` を明示しないと SpringDoc が `?` を `required: false` だけに
// 変換し、生成側で `string` 扱いになる。null クリアを明示的にリクエストできるよう nullable も付与。
@Schema(description = "プロフィール更新リクエスト")
data class PlayerProfileBody(
    @field:Size(max = 50)
    @field:Schema(description = "Twitter ユーザ名 (50 文字以内、null でクリア)", nullable = true)
    val twitterUserName: String? = null,
    @field:Size(max = 2000)
    @field:Schema(description = "自己紹介 (2000 文字以内、null でクリア)", nullable = true)
    val introduction: String? = null,
)
