package com.ort.app.api.v1.villages

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.Authority
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.check
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class VillageParticipateRestControllerTest {

    @Autowired private lateinit var context: WebApplicationContext
    @Autowired private lateinit var mapper: ObjectMapper

    @MockitoBean private lateinit var villageService: VillageService
    @MockitoBean private lateinit var playerService: PlayerService
    @MockitoBean private lateinit var charaService: CharaService
    @MockitoBean private lateinit var villageCoordinator: VillageCoordinator

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    private fun aPlayer(): Player = Player(
        id = 100,
        name = "tester",
        twitterUserName = null,
        introduction = null,
        authority = Authority(CDef.Authority.プレイヤー),
        isRestrictedParticipation = false,
        shouldCheckAccessInfo = false,
    )

    @Test
    fun `POST participate 未認証なら 400 (ログインが必要)`() {
        val village = createPrologueVillage().copy(id = 1)
        whenever(villageService.findVillage(eq(1), any())).thenReturn(village)
        val body = mapOf(
            "charaId" to 1,
            "charaName" to "太郎",
            "charaShortName" to "太",
            "joinMessage" to "よろしく",
        )
        mockMvc.perform(
            post("/api/v1/villages/1/participate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `POST participate 認証あり 201 で coordinator_participate が呼ばれる`() {
        val village = createPrologueVillage().copy(id = 2)
        whenever(villageService.findVillage(eq(2), any())).thenReturn(village)
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(aPlayer())

        val body = mapOf(
            "charaId" to 1,
            "charaName" to "太郎",
            "charaShortName" to "太",
            "requestedSkill" to CDef.Skill.村人.code(),
            "secondRequestedSkill" to CDef.Skill.人狼.code(),
            "joinMessage" to "よろしく",
            "joinPassword" to null,
            "spectator" to false,
        )
        mockMvc.perform(
            post("/api/v1/villages/2/participate")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isCreated)

        verify(villageCoordinator).participate(
            eq(village), any(), eq(1), eq("太郎"), eq("太"),
            anyOrNull(), check<Skill> { require(it.code == CDef.Skill.村人.code()) },
            check<Skill> { require(it.code == CDef.Skill.人狼.code()) },
            eq("よろしく"), anyOrNull(), eq(false), any(),
        )
    }

    @Test
    fun `POST participate オリジナルキャラチップ村に JSON 版を叩くと 400`() {
        val original = createPrologueVillage().let { v ->
            v.copy(
                id = 3,
                setting = v.setting.copy(chara = v.setting.chara.copy(isOriginalCharachip = true)),
            )
        }
        whenever(villageService.findVillage(eq(3), any())).thenReturn(original)
        whenever(playerService.findPlayer(eq("tester"))).thenReturn(aPlayer())
        val body = mapOf(
            "charaId" to 1,
            "charaName" to "太郎",
            "charaShortName" to "太",
            "joinMessage" to "よろしく",
        )
        // 旧仕様では 501 を返していたが、Step 8i で multipart 版 endpoint を追加したため
        // 「JSON 版に対しオリジナル村」のケースは 400 (multipart endpoint を使えという案内) に変更
        mockMvc.perform(
            post("/api/v1/villages/3/participate")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `DELETE participate 認証あり 204 で coordinator_leave が呼ばれる`() {
        val village = createPrologueVillage().copy(id = 4)
        whenever(villageService.findVillage(eq(4), any())).thenReturn(village)
        val participant = createDay1Village().participants.list.first()
        whenever(villageService.findVillageParticipant(eq(4), eq("tester"), any())).thenReturn(participant)

        mockMvc.perform(delete("/api/v1/villages/4/participate").with(authed()))
            .andExpect(status().isNoContent)

        verify(villageCoordinator).leave(eq(village), eq(participant))
    }

    @Test
    fun `PUT participate_skill 認証あり 204`() {
        val village = createPrologueVillage().copy(id = 5)
        whenever(villageService.findVillage(eq(5), any())).thenReturn(village)
        val participant = createDay1Village().participants.list.first()
        whenever(villageService.findVillageParticipant(eq(5), eq("tester"), any())).thenReturn(participant)

        val body = mapOf(
            "requestedSkill" to CDef.Skill.占い師.code(),
            "secondRequestedSkill" to CDef.Skill.村人.code(),
        )
        mockMvc.perform(
            put("/api/v1/villages/5/participate/skill")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(villageCoordinator).changeRequestSkill(
            eq(village), eq(participant),
            check<Skill> { require(it.code == CDef.Skill.占い師.code()) },
            check<Skill> { require(it.code == CDef.Skill.村人.code()) },
        )
    }

    @Test
    fun `POST participate_switch 認証あり 204`() {
        val village = createPrologueVillage().copy(id = 6)
        whenever(villageService.findVillage(eq(6), any())).thenReturn(village)
        val participant = createDay1Village().participants.list.first()
        whenever(villageService.findVillageParticipant(eq(6), eq("tester"), any())).thenReturn(participant)

        mockMvc.perform(post("/api/v1/villages/6/participate/switch").with(authed()))
            .andExpect(status().isNoContent)

        verify(villageCoordinator).switchParticipate(eq(village), eq(participant))
    }

    @Test
    fun `村が無ければ 404`() {
        whenever(villageService.findVillage(eq(999), any())).thenReturn(null)
        mockMvc.perform(delete("/api/v1/villages/999/participate").with(authed()))
            .andExpect(status().isNotFound)
    }
}
