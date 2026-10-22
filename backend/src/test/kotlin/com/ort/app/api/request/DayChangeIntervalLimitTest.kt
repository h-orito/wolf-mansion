package com.ort.app.api.request

import com.ort.app.api.village.request.VillageCreateRequest
import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DayChangeIntervalLimitTest {
    private val beanValidator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `時間欄は 120 まで入力でき 121 は拒否される`() {
        listOf(VillageCreateRequest::class.java, NewVillageForm::class.java, VillageSettingForm::class.java).forEach {
            assertTrue(beanValidator.validateValue(it, "dayChangeIntervalHours", 120).isEmpty(), it.simpleName)
            assertFalse(beanValidator.validateValue(it, "dayChangeIntervalHours", 121).isEmpty(), it.simpleName)
        }
    }
}
