package com.ort.app.api.randomkeyword.request

import com.ort.app.domain.model.randomkeyword.RandomContent
import com.ort.app.fw.exception.WolfMansionBusinessException

/**
 * ランダムキーワードの入力制約。リクエストのアノテーション (Jakarta Size / swagger ArraySchema)
 * から参照し、OpenAPI spec → フロント定数 (pnpm gen:api) の単一ソースにする。
 */
object RandomKeywordPolicy {
    const val KEYWORD_MIN_LENGTH = 3
    const val KEYWORD_MAX_LENGTH = 10
    const val KEYWORD_PATTERN = "[a-zA-Z]*"
    const val MESSAGE_MIN_LENGTH = 1
    const val MESSAGE_MAX_LENGTH = 20
}

/**
 * アノテーションで表現できない検証 (SSR の RandomKeywordFormValidator と同じ規則)。
 * 違反は [WolfMansionBusinessException] (= 400 business_error)。
 */
internal fun validateKeyword(keyword: String) {
    if (keyword.contains("or") || keyword.contains("who")) {
        throw WolfMansionBusinessException("キーワードにorとwhoを含むことはできません")
    }
}

internal fun toRandomContents(messages: List<String>): List<RandomContent> {
    // 要素ごとの長さは型引数の @Size では実行時検証されない (Kotlin は型アノテーションを
    // 既定でバイトコードに出力しない) ため、ここでコード検証する。
    if (messages.any { it.length !in RandomKeywordPolicy.MESSAGE_MIN_LENGTH..RandomKeywordPolicy.MESSAGE_MAX_LENGTH }) {
        throw WolfMansionBusinessException("変換後文字列はそれぞれ1文字以上20文字以内で入力してください")
    }
    if (messages.distinct().size != messages.size) {
        throw WolfMansionBusinessException("変換後文字列は全て違う文字列にしてください")
    }
    return messages.map { RandomContent(it) }
}
