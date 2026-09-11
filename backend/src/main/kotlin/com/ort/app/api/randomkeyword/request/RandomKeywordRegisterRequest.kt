package com.ort.app.api.randomkeyword.request

import com.ort.app.domain.model.randomkeyword.RandomKeyword
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * ランダムキーワード作成リクエスト。
 *
 * 制約は [RandomKeywordPolicy] を単一ソースに、Jakarta アノテーションで検証しつつ OpenAPI spec へ
 * 出力する (pnpm gen:api → constants.ts)。要素ごとの長さ制約は [ArraySchema] で spec に出し、
 * 検証はアノテーションで表現できない規則 (NG ワード・重複) と合わせて [toModel] で行う。
 */
data class RandomKeywordRegisterRequest(
    @field:NotNull
    @field:Size(min = RandomKeywordPolicy.KEYWORD_MIN_LENGTH, max = RandomKeywordPolicy.KEYWORD_MAX_LENGTH)
    @field:Pattern(regexp = RandomKeywordPolicy.KEYWORD_PATTERN)
    val keyword: String? = null,
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
    fun toModel(): RandomKeyword {
        validateKeyword(keyword!!)
        return RandomKeyword(
            id = 0, // 採番は datasource
            keyword = keyword,
            contents = toRandomContents(messages!!),
        )
    }
}
