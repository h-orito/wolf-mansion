package com.ort.app.domain.model.village

import com.ort.app.fw.exception.WolfMansionBusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VillageActionTextTest {
    private val names = listOf("[A] アリス", "[B] ボブ")

    @Test
    fun buildActionText() {
        // 行為者名はクライアントの指定ではなくサーバーで付ける
        assertEquals("[A] アリスは、手を振った。", Village.buildActionText("[A] アリス", names, null, "手を振った。"))
        assertEquals("[A] アリスは、手を振った。", Village.buildActionText("[A] アリス", names, "", "手を振った。"))
        assertEquals("[A] アリスは、全員に手を振った。", Village.buildActionText("[A] アリス", names, "全員", "に手を振った。"))
        assertEquals("[A] アリスは、[B] ボブに手を振った。", Village.buildActionText("[A] アリス", names, "[B] ボブ", "に手を振った。"))
        // 村にいない人や任意の文字列は対象にできない
        assertThrows<WolfMansionBusinessException> { Village.buildActionText("[A] アリス", names, "[C] キャロル", "に") }
        assertThrows<WolfMansionBusinessException> { Village.buildActionText("[A] アリス", names, "[B] ボブは、ではなく", "") }
    }
}
