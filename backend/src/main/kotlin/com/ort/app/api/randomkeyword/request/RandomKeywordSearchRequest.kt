package com.ort.app.api.randomkeyword.request

/**
 * ランダムキーワード一覧 (`GET /api/v1/random-keywords`) の検索条件。
 */
data class RandomKeywordSearchRequest(
    /** 検索語。キーワードまたは変換後文字列の部分一致。未指定なら全件。 */
    val q: String? = null,
    /** 並び順。`keyword`=キーワード名昇順 / それ以外 (未指定含む)=登録順。 */
    val order: String? = null,
) {
    fun isOrderByKeyword(): Boolean = "keyword".equals(order, ignoreCase = true)
}
