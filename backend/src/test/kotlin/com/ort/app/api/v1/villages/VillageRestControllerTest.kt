package com.ort.app.api.v1.villages

import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.app.domain.model.village.toModel
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.verify
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
class VillageRestControllerTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    @MockitoBean
    private lateinit var villageService: VillageService

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun `GET _api_v1_villages 認証不要で 200 と list を返す`() {
        val v1 = createPrologueVillage().copy(id = 1, name = "村1")
        val v2 = createPrologueVillage().copy(id = 2, name = "村2")
        whenever(villageService.findVillages(any())).thenReturn(Villages(list = listOf(v1, v2)))

        mockMvc.perform(get("/api/v1/villages"))
            .andExpect(status().isOk)
            // reversed: id=2 が先頭
            .andExpect(jsonPath("$.list[0].id").value(2))
            .andExpect(jsonPath("$.list[0].name").value("村2"))
            .andExpect(jsonPath("$.list[0].number").value("0002"))
            .andExpect(jsonPath("$.list[0].statusCode").exists())
            .andExpect(jsonPath("$.list[0].statusName").exists())
            .andExpect(jsonPath("$.list[1].id").value(1))
    }

    @Test
    fun `status クエリでフィルタ条件が渡る (code 形式)`() {
        whenever(villageService.findVillages(any())).thenReturn(Villages(list = emptyList()))

        mockMvc.perform(get("/api/v1/villages").param("status", "募集中,進行中"))
            .andExpect(status().isOk)

        verify(villageService).findVillages(check { query ->
            val codes = query.statuses.map { it.code }
            require(codes.containsAll(listOf(CDef.VillageStatus.募集中.code(), CDef.VillageStatus.進行中.code()))) {
                "expected 募集中,進行中 statuses but got $codes"
            }
        })
    }

    @Test
    fun `status 未指定なら statuses は空リスト (全件取得)`() {
        whenever(villageService.findVillages(any())).thenReturn(Villages(list = emptyList()))

        mockMvc.perform(get("/api/v1/villages"))
            .andExpect(status().isOk)

        verify(villageService).findVillages(check { query ->
            require(query.statuses.isEmpty()) { "expected empty statuses but got ${query.statuses}" }
        })
    }

    @Test
    fun `status に未知の値が混ざっても 200 で無視される`() {
        whenever(villageService.findVillages(any())).thenReturn(Villages(list = emptyList()))

        mockMvc.perform(get("/api/v1/villages").param("status", "ghost,募集中"))
            .andExpect(status().isOk)

        verify(villageService).findVillages(check { query ->
            val codes = query.statuses.map { it.code }
            require(codes == listOf(CDef.VillageStatus.募集中.code())) {
                "expected only 募集中 but got $codes"
            }
        })
    }

    @Test
    fun `id 4桁ゼロ埋め number が出る`() {
        val v = createPrologueVillage().copy(id = 42)
        whenever(villageService.findVillages(any())).thenReturn(Villages(list = listOf(v)))

        mockMvc.perform(get("/api/v1/villages"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.list[0].number").value("0042"))
    }

    @Test
    fun `id 1万を超える村も number はそのまま表示`() {
        val v = createPrologueVillage().copy(id = 12345)
        whenever(villageService.findVillages(any())).thenReturn(Villages(list = listOf(v)))

        mockMvc.perform(get("/api/v1/villages"))
            .andExpect(jsonPath("$.list[0].number").value("12345"))
    }

    @Suppress("unused")
    private fun referenceTypes() {
        // unused import 抑止用
        @Suppress("UNUSED_VARIABLE") val ignored = CDef.VillageStatus.募集中.toModel()
    }
}
