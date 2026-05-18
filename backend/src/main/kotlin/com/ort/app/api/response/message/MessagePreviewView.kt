package com.ort.app.api.response.message

import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 発言確認 (preview) のレスポンス。
 *
 * フロントエンドで発言ボタンを押す前に「実際にはこう表示されます」をプレビューするための DTO。
 * 同時にランダムキーワード一覧 (発言入力欄でのサジェスト用) も返す。
 */
@Schema(description = "発言確認 preview")
data class MessagePreviewView(
    @field:Schema(description = "プレビュー対象の発言")
    val message: MessageView,
    @field:Schema(description = "ランダムキーワード (カンマ区切り)")
    val randomKeywords: String,
) {
    constructor(message: Message, keywords: RandomKeywords) : this(
        message = MessageView(message),
        randomKeywords = keywords.list.joinToString(",") { it.keyword },
    )
}
