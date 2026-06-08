package com.ort.app.api.village

import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageRepository
import com.ort.app.domain.model.village.Villages
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock

internal class VillageRestControllerTest {
    private var capturedQuery: VillageQuery? = null

    // findVillages に渡る VillageQuery を捕捉するためのスタブ (mockito-kotlin 未導入のため anonymous subclass で代替)。
    private val villageService =
        object : VillageService(mock(VillageRepository::class.java)) {
            override fun findVillages(query: VillageQuery): Villages {
                capturedQuery = query
                return Villages(emptyList())
            }
        }
    private val controller = VillageRestController(villageService)

    @Test
    fun `引数なしなら全件 (空クエリ)`() {
        controller.list(status = null, charachip = null, skill = null, random = null)

        val query = capturedQuery!!
        assertEquals(emptyList<Any>(), query.statuses)
        assertEquals(emptyList<Int>(), query.charachipIds)
        assertEquals(emptyList<Any>(), query.skills)
        assertNull(query.isRandomOrg)
    }

    @Test
    fun `status code を VillageStatus に変換して渡す`() {
        controller.list(status = listOf("IN_PREPARATION", "IN_PROGRESS"), charachip = null, skill = null, random = null)

        assertEquals(listOf("IN_PREPARATION", "IN_PROGRESS"), capturedQuery!!.statuses.map { it.toCdef().code() })
    }

    @Test
    fun `charachip id・skill code・random を全て渡す`() {
        controller.list(status = null, charachip = listOf(1, 2), skill = listOf("VILLAGER", "SEER"), random = true)

        val query = capturedQuery!!
        assertEquals(listOf(1, 2), query.charachipIds)
        assertEquals(listOf("VILLAGER", "SEER"), query.skills.map { it.code })
        assertEquals(true, query.isRandomOrg)
    }

    @Test
    fun `不正な status code は 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            controller.list(status = listOf("NOT_A_STATUS"), charachip = null, skill = null, random = null)
        }
    }

    @Test
    fun `不正な skill code は 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            controller.list(status = null, charachip = null, skill = listOf("NOT_A_SKILL"), random = null)
        }
    }
}
