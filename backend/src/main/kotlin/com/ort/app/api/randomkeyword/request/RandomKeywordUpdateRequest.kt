package com.ort.app.api.randomkeyword.request

import com.ort.app.domain.model.randomkeyword.RandomContent
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

/**
 * ランダムキーワード更新リクエスト。キーワード自体は変更不可 (パスの id で特定) のため
 * 変換後文字列のみを受け取る。制約の表現方法は [RandomKeywordRegisterRequest] と同じ。
 */
data class RandomKeywordUpdateRequest(
    /** 変換後文字列 (1 要素 = 1 候補)。 */
    @field:NotEmpty
    @field:ArraySchema(
        schema =
            Schema(
                minLength = RandomKeywordPolicy.MESSAGE_MIN_LENGTH,
                maxLength = RandomKeywordPolicy.MESSAGE_MAX_LENGTH,
            ),
    )
    val messages: List<String>? = null,
) {
    fun toContents(): List<RandomContent> = toRandomContents(messages!!)
}
