package com.ort.app.api.village.response

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.ort.app.domain.model.village.createVillageSetting
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class VillageSettingViewTest {
    @Test
    fun `ドメインの設定値をそのまま写す`() {
        val setting = createVillageSetting()

        val view = VillageSettingView(setting)

        assertEquals(setting.chara, view.chara)
        assertEquals(setting.personMin, view.personMin)
        assertEquals(setting.personMax, view.personMax)
        assertEquals(setting.startDatetime, view.startDatetime)
        assertEquals(setting.dayChangeIntervalSeconds, view.dayChangeIntervalSeconds)
        assertEquals(setting.rule, view.rule)
        assertEquals(setting.organize, view.organize)
        assertEquals(setting.sayRestriction, view.sayRestriction)
        assertEquals(setting.tags, view.tags)
    }

    @Test
    fun `入村パスワードをシリアライズ結果に含まない`() {
        val setting = createVillageSetting().copy(joinPassword = "secret-password")

        val json =
            ObjectMapper()
                .registerKotlinModule()
                .registerModule(JavaTimeModule())
                .writeValueAsString(VillageSettingView(setting))

        assertFalse(json.contains("joinPassword"))
        assertFalse(json.contains("secret-password"))
    }
}
