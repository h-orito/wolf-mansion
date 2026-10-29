package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class ActionSayDomainServiceTest {
    private val service = ActionSayDomainService()
    private val village: Village = mock(Village::class.java, RETURNS_DEEP_STUBS)
    private val myself = participant("[A] アリス")
    private val other = participant("[B] ボブ")

    init {
        `when`(village.allParticipants().list).thenReturn(listOf(myself, other))
    }

    private fun participant(name: String): VillageParticipant =
        mock(VillageParticipant::class.java).also { `when`(it.name()).thenReturn(name) }

    private fun create(
        target: String?,
        message: String,
    ): String = service.createActionText(village, myself, target, message)

    @Test
    @DisplayName("行為者名はサーバーで付け、対象は全員か村にいる人の名前を付ける")
    fun createsText() {
        assertEquals("[A] アリスは、手を振った。", create(null, "手を振った。"))
        assertEquals("[A] アリスは、手を振った。", create("", " 手を振った。 "))
        assertEquals("[A] アリスは、全員に手を振った。", create("全員", "に手を振った。"))
        assertEquals("[A] アリスは、[B] ボブに手を振った。", create("[B] ボブ", "に手を振った。"))
    }

    @Test
    @DisplayName("村にいない人や任意の文字列は対象にできない")
    fun rejectsUnknownTarget() {
        assertThrows<WolfMansionBusinessException> { create("[C] キャロル", "に") }
        assertThrows<WolfMansionBusinessException> { create("[B] ボブは、ではなく", "手を振った。") }
    }

    @Test
    @DisplayName("本文が空白だけ、または行為者名込みで400文字を超えるものは受け付けない")
    fun rejectsInvalidLength() {
        assertThrows<WolfMansionBusinessException> { create("全員", " ") }
        // 「[A] アリスは、」(9 文字) + 391 文字 = 400 文字までは受け付ける
        assertEquals(400, create(null, "あ".repeat(391)).length)
        assertThrows<WolfMansionBusinessException> { create(null, "あ".repeat(392)) }
    }
}
