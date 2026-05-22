package com.ort.app.application.scheduler

import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DaychangeSchedulerTest {

    @Test
    fun `未終了の村すべてに changeDayIfNeeded を呼ぶ`() {
        val v1 = createPrologueVillage().copy(id = 1)
        val v2 = createPrologueVillage().copy(id = 2)
        val villageService = mock<VillageService> {
            on { findVillages(any()) } doReturn Villages(listOf(v1, v2))
            on { findVillage(1, false) } doReturn v1
            on { findVillage(2, false) } doReturn v2
        }
        val coordinator = mock<DaychangeCoordinator>()

        DaychangeScheduler(villageService, coordinator).changeDay()

        verify(coordinator).changeDayIfNeeded(v1)
        verify(coordinator).changeDayIfNeeded(v2)
    }

    @Test
    fun `未終了ステータスのみを対象に村を取得する`() {
        val villageService = mock<VillageService> {
            on { findVillages(any()) } doReturn Villages(emptyList())
        }

        DaychangeScheduler(villageService, mock()).changeDay()

        verify(villageService).findVillages(check { query ->
            val statuses = query.statuses.map { it.toCdef() }.toSet()
            require(
                statuses == setOf(
                    CDef.VillageStatus.募集中,
                    CDef.VillageStatus.進行中,
                    CDef.VillageStatus.エピローグ,
                )
            ) {
                "未終了ステータスのみを対象にすべき。actual=$statuses"
            }
        })
    }

    @Test
    fun `1 村の例外が他村の処理を止めない`() {
        val v1 = createPrologueVillage().copy(id = 1)
        val v2 = createPrologueVillage().copy(id = 2)
        val villageService = mock<VillageService> {
            on { findVillages(any()) } doReturn Villages(listOf(v1, v2))
            on { findVillage(1, false) } doReturn v1
            on { findVillage(2, false) } doReturn v2
        }
        val coordinator = mock<DaychangeCoordinator> {
            on { changeDayIfNeeded(v1) } doThrow RuntimeException("boom")
        }

        DaychangeScheduler(villageService, coordinator).changeDay()

        // v1 が例外を投げても v2 は処理される
        verify(coordinator).changeDayIfNeeded(v2)
    }
}
