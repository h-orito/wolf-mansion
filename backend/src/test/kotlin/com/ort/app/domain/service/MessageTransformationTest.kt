package com.ort.app.domain.service

import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.toModel
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MessageTransformationTest {
    private fun content(text: String): MessageContent =
        MessageContent(
            type = CDef.MessageType.通常発言.toModel(),
            num = null,
            text = text,
            faceTypeCode = null,
            isConvertDisable = false,
        )

    private fun transform(
        text: String,
        transformation: MessageDomainService.MessageTransformation,
    ): String = transformation.transform(content(text)).text

    private val barlowLine = Regex("^ラー+ン！+$")
    private val barlowFixedLines =
        listOf(
            "たったひとつの真実見抜く、見た目は人狼、頭脳は大人！",
            "真実はいつもひとつ！",
            "あれれ～～～？？？",
            "幼馴染で同級生の、毛利",
        )

    @Test
    fun barlow_transforms_each_lf_separated_line() {
        val lines = listOf("こんにちは", "", "今日はいい天気ですね")
        repeat(30) {
            val result = transform(lines.joinToString("\n"), MessageDomainService.MessageTransformation(barlow = true)).split("\n")
            assertEquals(3, result.size)
            assertEquals("", result[1])
            listOf(0, 2).forEach { i ->
                val line = result[i]
                assertTrue(
                    line == lines[i] || barlowLine.matches(line) || line in barlowFixedLines,
                    "unexpected line: $line",
                )
            }
        }
    }

    @Test
    fun barlow_accepts_crlf_and_joins_with_lf() {
        val result = transform("あ\r\nい", MessageDomainService.MessageTransformation(barlow = true))
        assertEquals(2, result.split("\n").size)
        assertTrue(!result.contains("\r"))
    }

    @Test
    fun assassin_appends_suffix_to_each_line() {
        val result = transform("あ\nい", MessageDomainService.MessageTransformation(assassin = true))
        assertEquals("あ──────\nい──────", result)
    }

    @Test
    fun clowning_appends_card_to_each_line() {
        val result = transform("あ\n\nい", MessageDomainService.MessageTransformation(clowning = true))
        val lines = result.split("\n")
        assertEquals(3, lines.size)
        assertTrue(Regex("^あ[♠♥♦♣]$").matches(lines[0]), lines[0])
        assertEquals("", lines[1])
        assertTrue(Regex("^い[♠♥♦♣]$").matches(lines[2]), lines[2])
    }

    @Test
    fun dakuten_wraps_each_line() {
        val result = transform("あ\r\nい", MessageDomainService.MessageTransformation(dakuten = true))
        assertEquals("[[large]][[b]]あ゛\nい゛[[/b]][[/large]]", result)
    }
}
