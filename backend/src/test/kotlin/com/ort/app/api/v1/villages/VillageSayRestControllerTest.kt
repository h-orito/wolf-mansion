package com.ort.app.api.v1.villages

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.application.coordinator.MessageCoordinator
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageTime
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.domain.model.village.createDay1Village
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@SpringBootTest
class VillageSayRestControllerTest {

    @Autowired private lateinit var context: WebApplicationContext
    @Autowired private lateinit var mapper: ObjectMapper

    @MockitoBean private lateinit var villageService: VillageService
    @MockitoBean private lateinit var messageCoordinator: MessageCoordinator
    @MockitoBean private lateinit var randomKeywordService: RandomKeywordService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun `POST messages 認証あり 201 で MessageCoordinator_say が呼ばれる`() {
        val village = createDay1Village().copy(id = 7)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(7), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(7), eq("tester"), any())).thenReturn(myself)

        val body = mapOf(
            "message" to "こんにちは",
            "messageType" to CDef.MessageType.通常発言.code(),
        )
        mockMvc.perform(
            post("/api/v1/villages/7/messages")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isCreated)

        verify(messageCoordinator).say(
            eq(village), eq(myself), eq("こんにちは"), eq(CDef.MessageType.通常発言.code()),
            anyOrNull(), anyOrNull(), anyOrNull(), any(),
        )
    }

    @Test
    fun `POST messages_preview MessagePreviewView を返す`() {
        val village = createDay1Village().copy(id = 8)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(8), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(8), eq("tester"), any())).thenReturn(myself)
        whenever(messageCoordinator.confirmToSay(any(), anyOrNull(), any(), any(), anyOrNull(), anyOrNull(), anyOrNull()))
            .thenReturn(
                Message(
                    fromParticipantId = null,
                    fromCharacterName = "[名]太郎",
                    toParticipantId = null,
                    toCharacterName = null,
                    time = MessageTime(day = 1, datetime = LocalDateTime.now()),
                    content = MessageContent(
                        type = MessageType(CDef.MessageType.通常発言),
                        num = null,
                        text = "プレビュー",
                        faceTypeCode = null,
                        isConvertDisable = false,
                    ),
                )
            )
        whenever(randomKeywordService.findRandomKeywords())
            .thenReturn(RandomKeywords(list = listOf(RandomKeyword(id = 1, keyword = "alpha", contents = emptyList()))))

        val body = mapOf(
            "message" to "プレビュー",
            "messageType" to CDef.MessageType.通常発言.code(),
        )
        mockMvc.perform(
            post("/api/v1/villages/8/messages/preview")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message.text").value("プレビュー"))
            .andExpect(jsonPath("$.randomKeywords").value("alpha"))
    }

    @Test
    fun `POST messages バリデーション NG (空 message) で 400`() {
        val body = mapOf(
            "message" to "",
            "messageType" to CDef.MessageType.通常発言.code(),
        )
        mockMvc.perform(
            post("/api/v1/villages/9/messages")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `POST messages 未知の messageType は 400 (旧実装は 500)`() {
        val village = createDay1Village().copy(id = 11)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(11), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(11), eq("tester"), any())).thenReturn(myself)
        val body = mapOf(
            "message" to "hi",
            "messageType" to "NO_SUCH_TYPE",
        )
        mockMvc.perform(
            post("/api/v1/villages/11/messages")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `POST actions は myself + target + message を結合して say を呼ぶ`() {
        val village = createDay1Village().copy(id = 10)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(10), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(10), eq("tester"), any())).thenReturn(myself)

        val body = mapOf(
            "myself" to "*",
            "target" to "→誰か",
            "message" to "が手を振った",
        )
        mockMvc.perform(
            post("/api/v1/villages/10/actions")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isCreated)

        verify(messageCoordinator).say(
            eq(village), eq(myself), eq("*→誰かが手を振った"), eq(CDef.MessageType.アクション.code()),
            anyOrNull(), anyOrNull(), anyOrNull(), any(),
        )
    }

    @Test
    fun `POST actions 結合 400 文字を超えるなら 400 (合計文字数バリデーション)`() {
        val village = createDay1Village().copy(id = 12)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(12), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(12), eq("tester"), any())).thenReturn(myself)

        val body = mapOf(
            "myself" to "x".repeat(200),
            "target" to "y".repeat(100),
            "message" to "z".repeat(200), // 合計 500 文字
        )
        mockMvc.perform(
            post("/api/v1/villages/12/actions")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }
}
