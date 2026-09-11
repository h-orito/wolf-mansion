package com.ort.app.api.village.request

import com.ort.app.fw.exception.WolfMansionBusinessException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class VillageSearchRequestTest {
    @Test
    fun `未指定なら空クエリ + 既定は降順`() {
        val query = VillageSearchRequest().toQuery()

        assertEquals(emptyList<Any>(), query.statuses)
        assertEquals(emptyList<Int>(), query.charachipIds)
        assertEquals(emptyList<Any>(), query.skills)
        assertNull(query.isRandomOrg)
        assertTrue(query.isDescending)
    }

    @Test
    fun `status code を VillageStatus に変換する`() {
        val query = VillageSearchRequest(status = listOf("IN_PREPARATION", "IN_PROGRESS")).toQuery()

        assertEquals(listOf("IN_PREPARATION", "IN_PROGRESS"), query.statuses.map { it.toCdef().code() })
    }

    @Test
    fun `charachip id・skill code・random を変換する`() {
        val query =
            VillageSearchRequest(
                charachip = listOf(1, 2),
                skill = listOf("VILLAGER", "SEER"),
                random = true,
            ).toQuery()

        assertEquals(listOf(1, 2), query.charachipIds)
        assertEquals(listOf("VILLAGER", "SEER"), query.skills.map { it.code })
        assertEquals(true, query.isRandomOrg)
    }

    @Test
    fun `order=asc は昇順 それ以外は降順`() {
        assertEquals(false, VillageSearchRequest(order = "asc").toQuery().isDescending)
        assertEquals(false, VillageSearchRequest(order = "ASC").toQuery().isDescending)
        assertEquals(true, VillageSearchRequest(order = "desc").toQuery().isDescending)
        assertEquals(true, VillageSearchRequest(order = null).toQuery().isDescending)
    }

    @Test
    fun `不正な status code は 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            VillageSearchRequest(status = listOf("NOT_A_STATUS")).toQuery()
        }
    }

    @Test
    fun `不正な skill code は 400 (BusinessException)`() {
        assertThrows<WolfMansionBusinessException> {
            VillageSearchRequest(skill = listOf("NOT_A_SKILL")).toQuery()
        }
    }
}
