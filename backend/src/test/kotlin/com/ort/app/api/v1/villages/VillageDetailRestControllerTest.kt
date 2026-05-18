package com.ort.app.api.v1.villages

import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.chara.CharaImages
import com.ort.app.domain.model.chara.CharaSize
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.FaceType
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.app.domain.model.village.toModel
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class VillageDetailRestControllerTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    @MockitoBean
    private lateinit var villageService: VillageService

    @MockitoBean
    private lateinit var charaService: CharaService

    @MockitoBean
    private lateinit var playerService: PlayerService

    @MockitoBean
    private lateinit var messageService: MessageService

    @MockitoBean
    private lateinit var footstepService: FootstepApplicationService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
        // 全テストで共通の player モック (find by id では未登録なので空 Players、name 引きは null)
        whenever(playerService.findPlayers(any<Int>())).thenReturn(Players(list = emptyList()))
    }

    // ---------- GET /{id} ----------

    @Test
    fun `GET _api_v1_villages_id 認証不要で 200 と村ヘッダを返す`() {
        val village = createPrologueVillage().copy(id = 42, name = "テスト村")
        whenever(villageService.findVillage(eq(42), any())).thenReturn(village)
        whenever(charaService.findCharachips(any(), any())).thenReturn(buildCharachips(village))

        mockMvc.perform(get("/api/v1/villages/42"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(42))
            .andExpect(jsonPath("$.name").value("テスト村"))
            .andExpect(jsonPath("$.number").value("0042"))
            .andExpect(jsonPath("$.statusCode").value(CDef.VillageStatus.募集中.code()))
            .andExpect(jsonPath("$.isParticipating").value(false))
            .andExpect(jsonPath("$.isCreator").value(false))
    }

    @Test
    fun `GET _api_v1_villages_id 村が存在しなければ業務例外`() {
        whenever(villageService.findVillage(eq(999), any())).thenReturn(null)

        // ExceptionControllerAdvice は Thymeleaf 時代の遺物で未配線のため、
        // 現状は WolfMansionBusinessException がそのまま伝搬する。
        // API 化フェーズ完了後に共通エラーハンドラを追加する想定 (Step 9 以降)。
        runCatching {
            mockMvc.perform(get("/api/v1/villages/999"))
        }.exceptionOrNull().let {
            require(it != null) { "expected an exception to be thrown for unknown village id" }
        }
    }

    // ---------- GET /{id}/footsteps ----------

    @Test
    fun `GET _api_v1_villages_id_footsteps 募集中は空リスト`() {
        val village = createPrologueVillage().copy(id = 1)
        whenever(villageService.findVillage(eq(1), any())).thenReturn(village)
        whenever(charaService.findCharachips(any(), any())).thenReturn(buildCharachips(village))

        mockMvc.perform(get("/api/v1/villages/1/footsteps"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list").isArray)
            .andExpect(jsonPath("$.list.length()").value(0))
    }

    @Test
    fun `GET _api_v1_villages_id_footsteps 進行中、未参加者から見ると registerChara_chara は隠蔽されるが roomNumbers は公開`() {
        val village = createDay1Village().copy(id = 2)
        val ownerCharaId = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }.charaId
        whenever(villageService.findVillage(eq(2), any())).thenReturn(village)
        whenever(charaService.findCharachips(any(), any())).thenReturn(buildCharachips(village))
        whenever(footstepService.findFootsteps(eq(2))).thenReturn(
            Footsteps(list = listOf(Footstep(day = 1, charaId = ownerCharaId, roomNumbers = "01,02,03")))
        )

        mockMvc.perform(get("/api/v1/villages/2/footsteps"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list.length()").value(1))
            .andExpect(jsonPath("$.list[0].day").value(1))
            .andExpect(jsonPath("$.list[0].roomNumbers").value("01,02,03"))
            .andExpect(jsonPath("$.list[0].registerChara").doesNotExist())
            .andExpect(jsonPath("$.list[0].chara").doesNotExist())
    }

    @Test
    fun `GET _api_v1_villages_id_footsteps エピローグでは未参加者でも全公開`() {
        val village = createDay1Village().copy(
            id = 3,
            status = CDef.VillageStatus.エピローグ.toModel(),
        )
        val ownerCharaId = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }.charaId
        whenever(villageService.findVillage(eq(3), any())).thenReturn(village)
        whenever(charaService.findCharachips(any(), any())).thenReturn(buildCharachips(village))
        whenever(footstepService.findFootsteps(eq(3))).thenReturn(
            Footsteps(list = listOf(Footstep(day = 1, charaId = ownerCharaId, roomNumbers = "04,05")))
        )

        mockMvc.perform(get("/api/v1/villages/3/footsteps"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list.length()").value(1))
            .andExpect(jsonPath("$.list[0].roomNumbers").value("04,05"))
            .andExpect(jsonPath("$.list[0].registerChara.id").value(ownerCharaId))
            .andExpect(jsonPath("$.list[0].chara.id").value(ownerCharaId))
    }

    // ---------- ヘルパー ----------

    /**
     * テスト用 Charachip を組み立てる。
     * 参加者の charaId をすべて含むダミーキャラを用意する。
     */
    private fun buildCharachips(village: Village): Charachips {
        val charas = village.allParticipants().list.map { p ->
            Chara(
                id = p.charaId,
                name = "chara${p.charaId}",
                shortName = "C${p.charaId}",
                defaultJoinMessage = null,
                defaultFirstdayMessage = null,
                size = CharaSize(width = 60, height = 80),
                images = CharaImages(
                    list = listOf(
                        CharaImage(
                            faceType = FaceType(CDef.FaceType.通常),
                            url = "/img/${p.charaId}.png",
                            isDisplay = true,
                        )
                    )
                ),
            )
        }
        return Charachips(
            list = listOf(
                Charachip(
                    id = 1,
                    name = "テストチップ",
                    designer = null,
                    descriptionUrl = null,
                    isAvailableChangeName = false,
                    charas = Charas(list = charas),
                )
            )
        )
    }
}
