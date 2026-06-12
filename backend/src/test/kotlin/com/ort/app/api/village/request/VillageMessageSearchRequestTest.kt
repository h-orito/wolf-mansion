package com.ort.app.api.village.request

import com.ort.app.domain.model.village.createDay1Village
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class VillageMessageSearchRequestTest {
    @Test
    fun `省略時は最新日・絞り込みなしのクエリになる`() {
        val village = createDay1Village()

        val query = VillageMessageSearchRequest().toQuery(village)

        assertEquals(village.latestDay(), query.day)
        assertEquals(emptyList<Any>(), query.requestTypes)
        assertEquals(emptyList<Any>(), query.fromParticipantIds)
        assertEquals(emptyList<Any>(), query.toParticipantIds)
        assertFalse(query.isPaging)
        assertFalse(query.isDispLatest)
    }

    @Test
    fun `合成種別 GRAVE_SPECTATE_SAY は呻きと見学発言に展開される`() {
        val village = createDay1Village()

        val query =
            VillageMessageSearchRequest(
                types = listOf("GRAVE_SPECTATE_SAY", CDef.MessageType.通常発言.code()),
            ).toQuery(village)

        assertEquals(
            listOf(CDef.MessageType.死者の呻き, CDef.MessageType.見学発言, CDef.MessageType.通常発言),
            query.requestTypes.map { it.toCdef() },
        )
    }

    @Test
    fun `発言者と日とページングが写像される`() {
        val village = createDay1Village()

        val query =
            VillageMessageSearchRequest(
                day = 0,
                pageSize = 30,
                pageNum = 2,
                isPaging = true,
                participantIds = listOf(2, 3),
            ).toQuery(village)

        assertEquals(0, query.day)
        assertEquals(30, query.pageSize)
        assertEquals(2, query.pageNum)
        assertEquals(listOf(2, 3), query.fromParticipantIds)
        assertEquals(true, query.isPaging)
    }
}
