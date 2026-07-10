package com.ort.app.domain.model.randomkeyword

data class RandomKeywords(
    val list: List<RandomKeyword>,
) {
    /** キーワードまたは変換後文字列の部分一致で絞り込む。 */
    fun filterBy(text: String): RandomKeywords =
        RandomKeywords(
            list =
                list.filter { keyword ->
                    keyword.keyword.contains(text) || keyword.contents.any { it.message.contains(text) }
                },
        )

    /** キーワード名昇順に並べ替える。 */
    fun sortedByKeyword(): RandomKeywords = RandomKeywords(list = list.sortedBy { it.keyword })
}
