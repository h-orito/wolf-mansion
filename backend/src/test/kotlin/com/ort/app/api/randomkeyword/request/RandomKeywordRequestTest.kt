package com.ort.app.api.randomkeyword.request

import com.ort.app.fw.exception.WolfMansionBusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class RandomKeywordRequestTest {
    @Test
    fun `register - keyword と messages をドメインモデルに変換する`() {
        val model =
            RandomKeywordRegisterRequest(
                keyword = "abc",
                messages = listOf("ほげ", "ふが"),
            ).toModel()

        assertEquals("abc", model.keyword)
        assertEquals(listOf("ほげ", "ふが"), model.contents.map { it.message })
    }

    @Test
    fun `register - keyword に or か who を含むと 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            RandomKeywordRegisterRequest(keyword = "horse", messages = listOf("ほげ")).toModel()
        }
        assertThrows<WolfMansionBusinessException> {
            RandomKeywordRegisterRequest(keyword = "whoami", messages = listOf("ほげ")).toModel()
        }
    }

    @Test
    fun `register - messages の要素が 1〜20 文字の範囲外だと 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            RandomKeywordRegisterRequest(keyword = "abc", messages = listOf("")).toModel()
        }
        assertThrows<WolfMansionBusinessException> {
            RandomKeywordRegisterRequest(keyword = "abc", messages = listOf("あ".repeat(21))).toModel()
        }
        // 境界値 (20 文字) は通る
        RandomKeywordRegisterRequest(keyword = "abc", messages = listOf("あ".repeat(20))).toModel()
    }

    @Test
    fun `register - messages に重複があると 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            RandomKeywordRegisterRequest(keyword = "abc", messages = listOf("ほげ", "ほげ")).toModel()
        }
    }

    @Test
    fun `update - messages を contents に変換する`() {
        val contents = RandomKeywordUpdateRequest(messages = listOf("ほげ", "ふが")).toContents()

        assertEquals(listOf("ほげ", "ふが"), contents.map { it.message })
    }

    @Test
    fun `update - messages に重複があると 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            RandomKeywordUpdateRequest(messages = listOf("ほげ", "ほげ")).toContents()
        }
    }

    @Test
    fun `search - order=keyword のときのみキーワード名昇順`() {
        assertEquals(true, RandomKeywordSearchRequest(order = "keyword").isOrderByKeyword())
        assertEquals(true, RandomKeywordSearchRequest(order = "KEYWORD").isOrderByKeyword())
        assertEquals(false, RandomKeywordSearchRequest(order = null).isOrderByKeyword())
        assertEquals(false, RandomKeywordSearchRequest(order = "id").isOrderByKeyword())
    }
}
