package com.ort.app.api.v1.villages

import com.fasterxml.jackson.databind.ObjectMapper
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.chara.CharaImages
import com.ort.app.domain.model.chara.CharaSize
import com.ort.app.domain.model.chara.FaceType
import com.ort.app.domain.model.village.createDay1Village
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
class VillageRpRestControllerTest {

    @Autowired private lateinit var context: WebApplicationContext
    @Autowired private lateinit var mapper: ObjectMapper

    @MockitoBean private lateinit var villageService: VillageService
    @MockitoBean private lateinit var charaService: CharaService
    @MockitoBean private lateinit var villageCoordinator: VillageCoordinator

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun `PUT rp_name 認証あり 204 で coordinator_changeName が呼ばれる`() {
        val village = createDay1Village().copy(id = 1)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(1), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(1), eq("tester"), any())).thenReturn(myself)

        val body = mapOf("name" to "新太郎", "shortName" to "新")
        mockMvc.perform(
            put("/api/v1/villages/1/rp/name")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(villageCoordinator).changeName(eq(village), eq(myself), eq("新太郎"), eq("新"))
    }

    @Test
    fun `PUT rp_memo 認証あり 204 で service_changeMemo が呼ばれる`() {
        val village = createDay1Village().copy(id = 2)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(2), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(2), eq("tester"), any())).thenReturn(myself)

        val body = mapOf("memo" to "考察メモ")
        mockMvc.perform(
            put("/api/v1/villages/2/rp/memo")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(villageService).changeMemo(eq(myself), eq("考察メモ"))
    }

    @Test
    fun `PUT rp_face-types 認証あり 204`() {
        val village = createDay1Village().copy(id = 3)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(3), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(3), eq("tester"), any())).thenReturn(myself)

        val body = mapOf(
            "faceTypeList" to listOf(
                mapOf("code" to "F01", "name" to "笑顔", "display" to true),
                mapOf("code" to "F02", "name" to "怒", "display" to false),
            )
        )
        mockMvc.perform(
            put("/api/v1/villages/3/rp/face-types")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isNoContent)

        verify(charaService).updateOriginalCharaImage(eq(myself.charaId), eq("F01"), eq("笑顔"), eq(true))
        verify(charaService).updateOriginalCharaImage(eq(myself.charaId), eq("F02"), eq("怒"), eq(false))
    }

    @Test
    fun `バリデーション NG (memo 21 文字) で 400`() {
        val village = createDay1Village().copy(id = 4)
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(4), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(4), eq("tester"), any())).thenReturn(myself)

        val body = mapOf("memo" to "a".repeat(21))
        mockMvc.perform(
            put("/api/v1/villages/4/rp/memo")
                .with(authed())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun `GET rp_face-types 通常村は空 list を返す`() {
        val village = createDay1Village().copy(id = 10)
        // createDay1Village は isOriginalCharachip=false なので通常村
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(10), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(10), eq("tester"), any())).thenReturn(myself)

        mockMvc.perform(
            get("/api/v1/villages/10/rp/face-types").with(authed())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list.length()").value(0))
    }

    @Test
    fun `GET rp_face-types オリジナルキャラチップ村は自キャラの表情を返す`() {
        val baseVillage = createDay1Village().copy(id = 11)
        // setting を書き換えてオリジナルキャラチップ村に
        val village = baseVillage.copy(
            setting = baseVillage.setting.copy(
                chara = baseVillage.setting.chara.copy(isOriginalCharachip = true)
            )
        )
        val myself = village.participants.list.first()
        whenever(villageService.findVillage(eq(11), any())).thenReturn(village)
        whenever(villageService.findVillageParticipant(eq(11), eq("tester"), any())).thenReturn(myself)
        whenever(charaService.findChara(eq(myself.charaId), eq(true))).thenReturn(
            Chara(
                id = myself.charaId,
                name = "テス太郎",
                shortName = "テ",
                defaultJoinMessage = null,
                defaultFirstdayMessage = null,
                size = CharaSize(width = 60, height = 60),
                images = CharaImages(
                    list = listOf(
                        CharaImage(
                            faceType = FaceType(code = "100", name = "通常"),
                            url = "https://example.test/100.png",
                            isDisplay = true,
                        ),
                        CharaImage(
                            faceType = FaceType(code = "101", name = "笑顔"),
                            url = "https://example.test/101.png",
                            isDisplay = false,
                        ),
                    )
                ),
            )
        )

        mockMvc.perform(
            get("/api/v1/villages/11/rp/face-types").with(authed())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list.length()").value(2))
            .andExpect(jsonPath("$.list[0].code").value("100"))
            .andExpect(jsonPath("$.list[0].name").value("通常"))
            .andExpect(jsonPath("$.list[0].isDisplay").value(true))
            .andExpect(jsonPath("$.list[1].code").value("101"))
            .andExpect(jsonPath("$.list[1].isDisplay").value(false))
    }

    @Test
    fun `未認証なら 400`() {
        val village = createDay1Village().copy(id = 5)
        whenever(villageService.findVillage(eq(5), any())).thenReturn(village)
        val body = mapOf("name" to "x", "shortName" to "x")
        mockMvc.perform(
            put("/api/v1/villages/5/rp/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body))
        ).andExpect(status().isBadRequest)
    }
}
