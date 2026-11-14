package com.ort.app.api.village

import com.ort.app.api.village.request.VillageMessageSearchRequest
import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.CommitService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.message.MessageRepository
import com.ort.app.domain.model.message.Messages
import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createVillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.security.jwt.JwtPrincipal
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

/**
 * 新着検知 (latest-datetime) が村全体・参加者の履歴・プレイヤーの村一覧を読まずに、
 * 公開範囲の判定に要る分だけを読んで閲覧者を判定に渡すことを確認する。
 */
internal class VillageMessageRestControllerLatestDatetimeTest {
    private val villageService: VillageService = mock(VillageService::class.java)
    private val playerService: PlayerService = mock(PlayerService::class.java)
    private val repository = CapturingMessageRepository()
    private val controller =
        VillageMessageRestController(
            villageService,
            mock(CharaService::class.java),
            playerService,
            mock(VoteApplicationService::class.java),
            mock(CommitService::class.java),
            MessageService(mock(MessageDomainService::class.java), repository),
            mock(AbilityService::class.java),
        )

    private val village: Village = createDay1Village()
    private val viewer: VillageParticipant = createVillageParticipant(skill = Skill(CDef.Skill.人狼), id = 30)
    private val player =
        Player(
            id = 30,
            name = "viewer",
            twitterUserName = null,
            introduction = null,
            authority = Authority(CDef.Authority.プレイヤー.code(), CDef.Authority.プレイヤー.alias()),
            isRestrictedParticipation = false,
            shouldCheckAccessInfo = false,
        )
    private val principal = JwtPrincipal(playerId = 30, name = "viewer", authorities = emptyList())

    private class CapturingMessageRepository : MessageRepository {
        var myself: VillageParticipant? = null
        var query: MessageQuery? = null

        override fun findLatestMessageDatetime(
            myself: VillageParticipant?,
            query: MessageQuery,
        ): LocalDateTime? {
            this.myself = myself
            this.query = query
            return LocalDateTime.of(2026, 11, 14, 12, 34, 56)
        }

        override fun findMessages(
            village: Village,
            myself: VillageParticipant?,
            query: MessageQuery,
        ): Messages = throw UnsupportedOperationException()

        override fun findMessage(
            village: Village,
            messageType: CDef.MessageType,
            messageNumber: Int,
        ): Message? = throw UnsupportedOperationException()

        override fun findParticipantDayMessageCount(
            village: Village,
            day: Int,
            participant: VillageParticipant,
        ): Map<CDef.MessageType, Int> = throw UnsupportedOperationException()

        override fun registerMessage(
            village: Village,
            message: Message,
        ): Message = throw UnsupportedOperationException()
    }

    private fun verifyHeavyLoadsNotCalled() {
        verify(villageService, never()).findVillage(anyInt(), anyBoolean())
        verify(villageService, never()).findVillageParticipant(anyInt(), anyString(), anyBoolean())
        verify(playerService, never()).findPlayer(anyString())
    }

    @Test
    @DisplayName("未ログインでは村・村設定・日付だけを読み、閲覧者なしで判定する")
    fun anonymous() {
        doReturn(village).`when`(villageService).findVillageWithoutParticipants(1)

        val result = controller.latestMessageDatetime(null, 1, VillageMessageSearchRequest())

        assertEquals("20261114123456", result.latestMessageDatetime)
        assertNull(repository.myself)
        assertEquals(village.latestDay(), repository.query!!.day)
        verifyHeavyLoadsNotCalled()
        verify(villageService, never()).findVillageParticipantWithoutHistories(anyInt(), anyString())
        verify(playerService, never()).findSimplePlayer(anyString())
    }

    @Test
    @DisplayName("ログイン中は閲覧者本人の参加情報とプレイヤーだけを読んで判定に渡す")
    fun loggedIn() {
        doReturn(village).`when`(villageService).findVillageWithoutParticipants(1)
        doReturn(viewer).`when`(villageService).findVillageParticipantWithoutHistories(1, "viewer")
        doReturn(player).`when`(playerService).findSimplePlayer("viewer")

        controller.latestMessageDatetime(principal, 1, VillageMessageSearchRequest(day = 1))

        assertSame(viewer, repository.myself)
        assertEquals(1, repository.query!!.day)
        verifyHeavyLoadsNotCalled()
    }

    @Test
    @DisplayName("参加者で絞る場合は「全員選択 = 絞り込みなし」の判定のために村全体を読む")
    fun filteredByParticipant() {
        doReturn(village).`when`(villageService).findVillage(1, false)

        controller.latestMessageDatetime(null, 1, VillageMessageSearchRequest(participantIds = listOf(2)))

        verify(villageService).findVillage(1, false)
        verify(villageService, never()).findVillageWithoutParticipants(anyInt())
        assertEquals(listOf(2), repository.query!!.fromParticipantIds)
    }

    @Test
    @DisplayName("村がなければ 404")
    fun notFound() {
        val e =
            assertThrows<ResponseStatusException> {
                controller.latestMessageDatetime(null, 999, VillageMessageSearchRequest())
            }
        assertEquals(HttpStatus.NOT_FOUND, e.statusCode)
    }
}
