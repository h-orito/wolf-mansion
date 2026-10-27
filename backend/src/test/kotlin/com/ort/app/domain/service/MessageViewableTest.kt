package com.ort.app.domain.service

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`

internal class MessageViewableTest {
    private val village: Village = mock(Village::class.java, RETURNS_DEEP_STUBS)
    private val myself: VillageParticipant = mock(VillageParticipant::class.java)
    private val service: MessageDomainService =
        spy(
            MessageDomainService(
                mock(SayDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.PsychicMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.GuruMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.AttackMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.CoronerMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.DivineMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.WiseMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.InvestigateMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.LoversMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.FoxMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.PrivateAbilityMessageDomainService::class.java),
                mock(com.ort.app.domain.service.message.system.PrivateSystemMessageDomainService::class.java),
                mock(com.ort.app.domain.model.translate.TranslateRepository::class.java),
            ),
        )

    init {
        `when`(myself.id).thenReturn(1)
        `when`(village.status.isSettled()).thenReturn(false)
        `when`(village.status.isCanceled()).thenReturn(false)
    }

    /** 種別単位では見られる (占い系役職が占い結果を見られる等) 状態にする */
    private fun typeViewable(
        participant: VillageParticipant?,
        messageType: CDef.MessageType,
        isViewable: Boolean,
    ) {
        doReturn(isViewable).`when`(service).isViewable(village, participant, null, messageType, 2)
    }

    private fun viewable(
        messageType: CDef.MessageType,
        from: Int?,
        to: Int? = null,
        participant: VillageParticipant? = myself,
    ): Boolean = service.isViewableMessage(village, participant, null, messageType, 2, from, to)

    @Test
    @DisplayName("他人の占い結果は種別を見られる役職でも見られない")
    fun othersDivineResult() {
        typeViewable(myself, CDef.MessageType.白黒占い結果, true)
        assertFalse(viewable(CDef.MessageType.白黒占い結果, from = 2))
        assertTrue(viewable(CDef.MessageType.白黒占い結果, from = 1))
    }

    @Test
    @DisplayName("個人宛ての能力結果は本人のものだけ見られる")
    fun personalResultsOnlyOwn() {
        listOf(
            CDef.MessageType.足音調査結果,
            CDef.MessageType.役職占い結果,
            CDef.MessageType.恋人メッセージ,
            CDef.MessageType.妖狐メッセージ,
            CDef.MessageType.能力行使メッセージ,
            CDef.MessageType.独り言,
        ).forEach {
            typeViewable(myself, it, true)
            assertFalse(viewable(it, from = 2), it.name)
            assertTrue(viewable(it, from = 1), it.name)
        }
    }

    @Test
    @DisplayName("秘話は送信者と宛先だけが見られる")
    fun secretSayOnlySenderAndTarget() {
        assertTrue(viewable(CDef.MessageType.秘話, from = 1, to = 2))
        assertTrue(viewable(CDef.MessageType.秘話, from = 2, to = 1))
        assertFalse(viewable(CDef.MessageType.秘話, from = 2, to = 3))
    }

    @Test
    @DisplayName("未参加者は個人宛ての発言を見られない")
    fun notParticipant() {
        assertFalse(viewable(CDef.MessageType.白黒占い結果, from = 2, participant = null))
        assertFalse(viewable(CDef.MessageType.秘話, from = 2, to = 3, participant = null))
    }

    @Test
    @DisplayName("それ以外の種別は種別単位の判定に従う")
    fun otherTypesFollowTypeLevel() {
        typeViewable(myself, CDef.MessageType.人狼の囁き, false)
        typeViewable(myself, CDef.MessageType.通常発言, true)
        assertFalse(viewable(CDef.MessageType.人狼の囁き, from = 2))
        assertTrue(viewable(CDef.MessageType.通常発言, from = 2))
    }

    @Test
    @DisplayName("決着後と管理者は全て見られる")
    fun settledOrAdmin() {
        `when`(village.status.isSettled()).thenReturn(true)
        assertTrue(viewable(CDef.MessageType.白黒占い結果, from = 2))
        `when`(village.status.isSettled()).thenReturn(false)

        `when`(myself.isAdmin()).thenReturn(true)
        assertTrue(viewable(CDef.MessageType.秘話, from = 2, to = 3))
    }

    @Test
    @DisplayName("発言者が見られない発言へのアンカーは宛先として記録しない")
    fun anchorable() {
        typeViewable(myself, CDef.MessageType.人狼の囁き, false)
        assertFalse(service.isAnchorableMessage(village, myself, CDef.MessageType.人狼の囁き, 2, 2, null))
        typeViewable(myself, CDef.MessageType.人狼の囁き, true)
        assertTrue(service.isAnchorableMessage(village, myself, CDef.MessageType.人狼の囁き, 2, 2, null))
        assertTrue(service.isAnchorableMessage(village, myself, CDef.MessageType.独り言, 2, 1, null))
        assertFalse(service.isAnchorableMessage(village, myself, CDef.MessageType.独り言, 2, 2, null))
    }
}
