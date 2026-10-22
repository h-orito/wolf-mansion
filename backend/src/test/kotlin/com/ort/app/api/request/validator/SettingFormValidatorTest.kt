package com.ort.app.api.request.validator

import com.ort.app.api.request.VillageSettingForm
import com.ort.app.domain.model.village.createPrologueVillage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.BeanPropertyBindingResult

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

    private fun hasIntervalError(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ): Boolean {
        val form =
            VillageSettingForm(createPrologueVillage()).apply {
                dayChangeIntervalHours = hours
                dayChangeIntervalMinutes = minutes
                dayChangeIntervalSeconds = seconds
            }
        val errors = BeanPropertyBindingResult(form, "settingsForm")
        SettingFormValidator().validate(form, errors)
        return errors.hasFieldErrors("dayChangeIntervalHours")
    }
}
