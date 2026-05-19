package com.ort.app.api.v1.players

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.api.v1.villages.authed
import com.ort.app.application.coordinator.PlayerCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRecords
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.player.Record
import com.ort.app.domain.model.village.Villages
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class PlayerRestControllerTest {

    @Autowired private lateinit var context: WebApplicationContext
    @Autowired private lateinit var mapper: ObjectMapper

    @MockitoBean private lateinit var playerService: PlayerService
    @MockitoBean private lateinit var playerCoordinator: PlayerCoordinator
    @MockitoBean private lateinit var charaService: CharaService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    private fun player(
        id: Int = 1,
        name: String = "tester",
        twitter: String? = null,
        introduction: String? = null,
        participateFinished: List<Int> = emptyList(),
    ): Player = Player(
        id = id,
        name = name,
        twitterUserName = twitter,
        introduction = introduction,
        authority = Authority(CDef.Authority.プレイヤー),
        isRestrictedParticipation = false,
        shouldCheckAccessInfo = true,
        participateFinishedVillageIdList = participateFinished,
    )

    @Test
    fun `GET _players 認証不要で 200 と list を返す`() {
        whenever(playerService.findAllPlayers(eq(30), eq(1))).thenReturn(
            Players(
                list = listOf(player(id = 1, name = "alice"), player(id = 2, name = "bob")),
                allPageCount = 1,
                isExistPrePage = false,
                isExistNextPage = false,
                currentPageNum = 1,
            )
        )

        mockMvc.perform(get("/api/v1/players"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list[0].name").value("alice"))
            .andExpect(jsonPath("$.list[1].name").value("bob"))
            .andExpect(jsonPath("$.currentPageNum").value(1))
            .andExpect(jsonPath("$.pageNumList[0]").value(1))
    }

    @Test
    fun `GET _players pageNum を渡すと service に伝わる`() {
        whenever(playerService.findAllPlayers(any(), any())).thenReturn(
            Players(list = emptyList(), allPageCount = 0, currentPageNum = 5)
        )

        mockMvc.perform(get("/api/v1/players").param("pageNum", "5"))
            .andExpect(status().isOk)

        verify(playerService).findAllPlayers(eq(30), eq(5))
    }

    @Test
    fun `GET _players_me 認証ありで 200 を返す`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player(twitter = "tw", introduction = "hi"))

        mockMvc.perform(get("/api/v1/players/me").with(authed()))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("tester"))
            .andExpect(jsonPath("$.twitterUserName").value("tw"))
            .andExpect(jsonPath("$.introduction").value("hi"))
            .andExpect(jsonPath("$.authorityCode").value(CDef.Authority.プレイヤー.code()))
            .andExpect(jsonPath("$.isRestrictedParticipation").value(false))
            .andExpect(jsonPath("$.isAvailableCreateVillage").value(false))
    }

    @Test
    fun `GET _players_me 未認証なら 400`() {
        mockMvc.perform(get("/api/v1/players/me"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `PUT _players_me_profile 204 で service が呼ばれる`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player())

        val body = mapOf("twitterUserName" to "tw", "introduction" to "hello")
        mockMvc.perform(
            put("/api/v1/players/me/profile")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(playerService).updatePlayerDetail(eq("tester"), eq("tw"), eq("hello"))
    }

    @Test
    fun `PUT _players_me_profile null フィールドで両方クリア`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player())

        val body = mapOf<String, Any?>("twitterUserName" to null, "introduction" to null)
        mockMvc.perform(
            put("/api/v1/players/me/profile")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(playerService).updatePlayerDetail(eq("tester"), eq<String?>(null), eq<String?>(null))
    }

    @Test
    fun `PUT _players_me_profile twitter 51 文字で 400`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player())

        val body = mapOf("twitterUserName" to "a".repeat(51))
        mockMvc.perform(
            put("/api/v1/players/me/profile")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `PUT _players_me_password 204 で service が呼ばれる`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player())

        val body = mapOf("password" to "abc123", "confirmPassword" to "abc123")
        mockMvc.perform(
            put("/api/v1/players/me/password")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(playerService).updatePassword(eq("tester"), eq("abc123"))
    }

    @Test
    fun `PUT _players_me_password 確認用と不一致なら 400`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player())

        val body = mapOf("password" to "abc123", "confirmPassword" to "xyz999")
        mockMvc.perform(
            put("/api/v1/players/me/password")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `PUT _players_me_password 形式 NG (記号混入) で 400`() {
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(player())

        val body = mapOf("password" to "abc!@#", "confirmPassword" to "abc!@#")
        mockMvc.perform(
            put("/api/v1/players/me/password")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `PUT _players_me_password 未認証なら 400`() {
        val body = mapOf("password" to "abc123", "confirmPassword" to "abc123")
        mockMvc.perform(
            put("/api/v1/players/me/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `GET _players_userName 200 で detail を返す`() {
        val target = player(name = "alice", twitter = "alice_tw", introduction = "hi alice")
        whenever(playerService.findPlayer(eq("alice"))).thenReturn(target)
        whenever(playerCoordinator.findPlayerRecords(eq(target))).thenReturn(
            PlayerRecords(
                player = target,
                wholeRecord = Record(participateCount = 3, winCount = 2, winRate = 0.6667f),
                campRecordList = emptyList(),
                skillRecordList = emptyList(),
                participateVillageList = emptyList(),
            )
        )
        whenever(charaService.findCharasByCharachipId(any(), any())).thenReturn(Charas(list = emptyList()))

        mockMvc.perform(get("/api/v1/players/alice"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("alice"))
            .andExpect(jsonPath("$.twitterUserName").value("alice_tw"))
            .andExpect(jsonPath("$.introduction").value("hi alice"))
            .andExpect(jsonPath("$.isSelf").value(false))
            .andExpect(jsonPath("$.wholeStats.participateNum").value(3))
            .andExpect(jsonPath("$.wholeStats.winNum").value(2))
    }

    @Test
    fun `GET _players_userName 認証ありで自分のページなら isSelf=true`() {
        val target = player(name = "tester")
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(target)
        whenever(playerCoordinator.findPlayerRecords(eq(target))).thenReturn(
            PlayerRecords(player = target, villages = Villages(list = emptyList()))
        )
        whenever(charaService.findCharasByCharachipId(any(), any())).thenReturn(Charas(list = emptyList()))

        mockMvc.perform(get("/api/v1/players/tester").with(authed()))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.isSelf").value(true))
    }

    @Test
    fun `GET _players_userName 存在しないユーザは 404`() {
        whenever(playerService.findPlayer(eq("ghost"))).thenReturn(null)

        mockMvc.perform(get("/api/v1/players/ghost"))
            .andExpect(status().isNotFound)
    }
}
