package com.ort.app.domain.model.randomkeyword

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RandomKeywordsTest {
    private val keywords =
        RandomKeywords(
            list =
                listOf(
                    RandomKeyword(id = 1, keyword = "omikuji", contents = listOf(RandomContent("大吉"), RandomContent("凶"))),
                    RandomKeyword(id = 2, keyword = "coin", contents = listOf(RandomContent("表"), RandomContent("裏"))),
                ),
        )

    @Test
    fun `filterBy - キーワードの部分一致で絞り込む`() {
        assertEquals(listOf(1), keywords.filterBy("omi").list.map { it.id })
    }

    @Test
    fun `filterBy - 変換後文字列の部分一致で絞り込む`() {
        assertEquals(listOf(2), keywords.filterBy("表").list.map { it.id })
    }

    @Test
    fun `filterBy - どちらにも一致しなければ空`() {
        assertEquals(emptyList<Int>(), keywords.filterBy("該当なし").list)
    }

    @Test
    fun `sortedByKeyword - キーワード名昇順に並べ替える`() {
        assertEquals(listOf("coin", "omikuji"), keywords.sortedByKeyword().list.map { it.keyword })
    }
}
