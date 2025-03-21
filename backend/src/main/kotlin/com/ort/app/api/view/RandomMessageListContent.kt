package com.ort.app.api.view

import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywords

data class RandomMessageListContent(
    val randomKeywordList: List<RandomMessageContent>
) {

    constructor(
        keywords: RandomKeywords
    ) : this(
        randomKeywordList = keywords.list.map { RandomMessageContent(it) }
    )

    data class RandomMessageContent(
        val keywordId: Int,
        val keyword: String,
        val contentExample: String?
    ) {
        constructor(
            keyword: RandomKeyword
        ) : this(
            keywordId = keyword.id,
            keyword = keyword.keyword,
            contentExample = keyword.contents.map { it.message }.joinToString(separator = "\n")
        )
    }
}