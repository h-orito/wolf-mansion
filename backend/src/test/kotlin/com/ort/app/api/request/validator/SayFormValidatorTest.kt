package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageSayForm
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.BeanPropertyBindingResult

internal class SayFormValidatorTest {
    private fun validate(message: String): BeanPropertyBindingResult {
        val form = VillageSayForm(message = message, messageType = "NORMAL_SAY", faceType = "NORMAL")
        val errors = BeanPropertyBindingResult(form, "sayForm")
        SayFormValidator().validate(form, errors)
        return errors
    }

    @Test
    fun lf_line_separators_are_not_counted_as_characters() {
        // 400 文字 + 改行 19 個: 改行を文字数に含めなければ上限内
        val message = List(20) { "あ".repeat(20) }.joinToString("\n")
        assertFalse(validate(message).hasErrors())
    }

    @Test
    fun crlf_line_separators_are_not_counted_as_characters() {
        val message = List(20) { "あ".repeat(20) }.joinToString("\r\n")
        assertFalse(validate(message).hasErrors())
    }

    @Test
    fun over_20_lf_lines_is_rejected() {
        val errors = validate(List(21) { "あ" }.joinToString("\n"))
        assertTrue(errors.hasFieldErrors("message"))
        assertEquals("VillageSayForm.validator.message.line", errors.getFieldError("message")!!.code)
    }
}
