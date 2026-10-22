package com.ort.app.api.request.validator

import com.ort.app.api.request.NewVillageForm
import com.ort.app.api.request.VillageSettingForm
import com.ort.app.api.village.request.VillageCreateRequest
import com.ort.app.domain.model.village.createPrologueVillage
import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.BeanPropertyBindingResult

/** 更新間隔の上限 (120 時間) の境界。村作成側の相関チェックは VillageCreateRequestTest で検証する。 */
internal class DayChangeIntervalLimitTest {
    private val beanValidator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `村設定変更で 120 時間ちょうどは許可される`() {
        assertFalse(hasSettingIntervalError(120, 0, 0))
    }

    @Test
    fun `村設定変更で 120 時間 1 秒は拒否される`() {
        assertTrue(hasSettingIntervalError(120, 0, 1))
    }

    @Test
    fun `村設定変更で 1 分未満は拒否される`() {
        assertTrue(hasSettingIntervalError(0, 0, 59))
    }

    @Test
    fun `時間欄は 120 まで入力でき 121 は拒否される`() {
        listOf(VillageCreateRequest::class.java, NewVillageForm::class.java, VillageSettingForm::class.java).forEach {
            assertTrue(beanValidator.validateValue(it, "dayChangeIntervalHours", 120).isEmpty(), it.simpleName)
            assertFalse(beanValidator.validateValue(it, "dayChangeIntervalHours", 121).isEmpty(), it.simpleName)
        }
    }

    private fun hasSettingIntervalError(
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
