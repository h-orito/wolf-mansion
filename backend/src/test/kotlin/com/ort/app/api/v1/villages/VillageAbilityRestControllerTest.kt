package com.ort.app.api.v1.villages

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.participant.VillageParticipants
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class VillageAbilityRestControllerTest {

    @Autowired private lateinit var context: WebApplicationContext
    @Autowired private lateinit var mapper: ObjectMapper

    @MockitoBean private lateinit var villageService: VillageService
    @MockitoBean private lateinit var villageCoordinator: VillageCoordinator

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }


    @Test
    fun `POST abilities 認証あり 204 で coordinator_setAbility が呼ばれる`() {
        val village = createDay1Village().copy(id = 1)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(1), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(1), eq("tester"), any())).thenReturn(myself)

        val body = mapOf(
            "attackerCharaId" to 2,
            "targetCharaId" to 3,
            "footstep" to "02,03",
        )
        mockMvc.perform(
            post("/api/v1/villages/1/abilities")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(villageCoordinator).setAbility(eq(village), eq(myself), eq(2), eq(3), eq("02,03"))
    }

    @Test
    fun `POST votes 認証あり 204`() {
        val village = createDay1Village().copy(id = 2)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(2), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(2), eq("tester"), any())).thenReturn(myself)

        val body = mapOf("targetCharaId" to 5)
        mockMvc.perform(
            post("/api/v1/villages/2/votes")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(villageCoordinator).setVote(eq(village), eq(myself), eq(5))
    }

    @Test
    fun `PUT commit 認証あり 204`() {
        val village = createDay1Village().copy(id = 3)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(3), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(3), eq("tester"), any())).thenReturn(myself)

        val body = mapOf("commit" to true)
        mockMvc.perform(
            put("/api/v1/villages/3/commit")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(villageCoordinator).setCommit(eq(village), eq(myself), eq(true))
    }

    @Test
    fun `GET abilities_attack-targets は charaId のリストを返す`() {
        val village = createDay1Village().copy(id = 4)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(4), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(4), eq("tester"), any())).thenReturn(myself)
        val candidates = VillageParticipants(
            count = 2,
            list = listOf(village.participants.list[1], village.participants.list[2]),
        )
        whenever(villageCoordinator.getAttackableTargets(eq(village), eq(myself), eq(myself.charaId)))
            .thenReturn(candidates)

        mockMvc.perform(
            get("/api/v1/villages/4/abilities/attack-targets")
                .with(authed())
                .param("charaId", myself.charaId.toString())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0]").value(village.participants.list[1].charaId))
    }

    @Test
    fun `GET abilities_footstep-candidates は文字列リストを返す`() {
        val village = createDay1Village().copy(id = 5)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(5), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(5), eq("tester"), any())).thenReturn(myself)
        whenever(villageCoordinator.getSelectableFootstepList(eq(village), eq(myself), eq(2), eq(5)))
            .thenReturn(listOf("02,03", "02,04"))

        mockMvc.perform(
            get("/api/v1/villages/5/abilities/footstep-candidates")
                .with(authed())
                .param("charaId", "2")
                .param("targetCharaId", "5")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0]").value("02,03"))
    }

    @Test
    fun `未認証なら 400`() {
        whenever(villageService.findVillage(eq(6), any())).thenReturn(createDay1Village().copy(id = 6))
        val body = mapOf("targetCharaId" to 1)
        mockMvc.perform(
            post("/api/v1/villages/6/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }
}
