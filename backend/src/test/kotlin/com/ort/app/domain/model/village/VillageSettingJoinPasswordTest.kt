package com.ort.app.domain.model.village

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class VillageSettingJoinPasswordTest {
    @Test
    fun matchesJoinPassword() {
        val setting = createVillageSetting().copy(joinPassword = "secret")
        assertTrue(setting.matchesJoinPassword("secret"))
        assertFalse(setting.matchesJoinPassword("wrong"))
        assertFalse(setting.matchesJoinPassword("secret "))
        assertFalse(setting.matchesJoinPassword(""))
        assertFalse(setting.matchesJoinPassword(null))
        // パスワードのない村は常に true
        assertTrue(createVillageSetting().copy(joinPassword = null).matchesJoinPassword(null))
        assertTrue(createVillageSetting().copy(joinPassword = "").matchesJoinPassword("anything"))
    }
}
