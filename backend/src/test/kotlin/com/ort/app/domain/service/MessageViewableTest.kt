package com.ort.app.domain.service

import com.ort.app.domain.model.message.MessageType
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

    private fun typeViewable(
        messageType: CDef.MessageType,
        isViewable: Boolean,
    ) {
        doReturn(isViewable).`when`(service).isViewable(village, myself, null, messageType, 2)
    }

    private fun viewable(messageType: CDef.MessageType): Boolean = service.isViewableAnchorMessage(village, myself, null, messageType, 2)

    @Test
    @DisplayName("発言以外の種別は、種別を見られる役職でもアンカーで見られない")
    fun notAnchorableTypes() {
        (MessageType.personalSystemTypeList + MessageType.commonSystemTypeList + CDef.MessageType.公開システムメッセージ)
            .forEach {
                typeViewable(it, true)
                assertFalse(viewable(it), it.name)
            }
    }

    @Test
    @DisplayName("発言系の種別と村建て発言は、種別単位の判定に従う")
    fun anchorableTypesFollowTypeLevel() {
        (MessageType.sayTypeList + CDef.MessageType.村建て発言).forEach {
            typeViewable(it, true)
            assertTrue(viewable(it), it.name)
            typeViewable(it, false)
            assertFalse(viewable(it), it.name)
        }
    }
}
