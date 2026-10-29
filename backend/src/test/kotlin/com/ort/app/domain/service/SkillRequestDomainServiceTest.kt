package com.ort.app.domain.service

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class SkillRequestDomainServiceTest {
    private val service = SkillRequestDomainService()
    private val village: Village = mock(Village::class.java)
    private val leftover = Skill(CDef.Skill.おまかせ)
    private val villager = Skill(CDef.Skill.村人)
    private val werewolf = Skill(CDef.Skill.人狼)

    @Test
    @DisplayName("役職希望ができない村では、おまかせ以外を希望して入村できない")
    fun cannotRequestSkill() {
        `when`(village.canRequestSkill()).thenReturn(false)
        assertDoesNotThrow { service.assertParticipateRequestSkill(village, leftover, leftover) }
        assertThrows<WolfMansionBusinessException> { service.assertParticipateRequestSkill(village, werewolf, leftover) }
        assertThrows<WolfMansionBusinessException> { service.assertParticipateRequestSkill(village, leftover, werewolf) }
    }

    @Test
    @DisplayName("役職希望ができる村では、村で希望できる役職だけを希望して入村できる")
    fun canRequestSkill() {
        `when`(village.canRequestSkill()).thenReturn(true)
        `when`(village.allRequestableSkillList()).thenReturn(listOf(villager, leftover))
        assertDoesNotThrow { service.assertParticipateRequestSkill(village, villager, leftover) }
        assertThrows<WolfMansionBusinessException> { service.assertParticipateRequestSkill(village, werewolf, villager) }
    }
}
