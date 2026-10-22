package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageSettingForm
import com.ort.app.domain.model.village.createPrologueVillage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.BeanPropertyBindingResult
import java.util.Locale

internal class SettingFormValidatorTest {
    @Test
    fun `更新間隔 120 時間ちょうどは許可される`() {
        assertFalse(hasIntervalError(120, 0, 0))
    }

    @Test
    fun `更新間隔 120 時間 1 秒は拒否される`() {
        assertTrue(hasIntervalError(120, 0, 1))
    }

    @Test
    fun `更新間隔 1 分未満は拒否される`() {
        assertTrue(hasIntervalError(0, 0, 59))
    }

    @Test
    fun `更新間隔のエラーメッセージに上限時間が入る`() {
        val messageSource =
            ResourceBundleMessageSource().apply {
                setBasename("messages")
                setDefaultEncoding("UTF-8")
            }
        val error = validate(120, 0, 1).getFieldError("dayChangeIntervalHours")!!

        assertEquals("更新間隔は1分以上120時間以内で設定してください", messageSource.getMessage(error, Locale.JAPAN))
    }

    private fun hasIntervalError(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ): Boolean = validate(hours, minutes, seconds).hasFieldErrors("dayChangeIntervalHours")

    private fun validate(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ): BeanPropertyBindingResult {
        val form =
            VillageSettingForm(createPrologueVillage()).apply {
                dayChangeIntervalHours = hours
                dayChangeIntervalMinutes = minutes
                dayChangeIntervalSeconds = seconds
            }
        val errors = BeanPropertyBindingResult(form, "settingsForm")
        SettingFormValidator().validate(form, errors)
        return errors
    }
}
