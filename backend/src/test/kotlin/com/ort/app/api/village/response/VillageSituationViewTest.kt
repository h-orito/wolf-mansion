package com.ort.app.api.village.response

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.situation.VillageSituation
import com.ort.app.domain.model.situation.village.VillageDayFootstep
import com.ort.app.domain.model.situation.village.VillageFootstepSituation
import com.ort.app.domain.model.situation.village.VillageParticipantLiveSituation
import com.ort.app.domain.model.situation.village.VillageRoomAssignedSituation
import com.ort.app.domain.model.situation.village.VillageVoteSituation
import com.ort.app.domain.model.situation.village.VillageWholeSituation
import com.ort.app.domain.model.village.createPrologueVillage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class VillageSituationViewTest {
    @Test
    fun `部屋なし村は部屋割りと投票を返さない`() {
        val village = createPrologueVillage()
        val situation =
            VillageSituation(
                roomAssigned = VillageRoomAssignedSituation(columns = emptyList()),
                live = VillageParticipantLiveSituation(village),
                footstep =
                    VillageFootstepSituation(
                        list = listOf(VillageDayFootstep(day = 1, footstep = "01、02")),
                    ),
                vote = VillageVoteSituation(list = emptyList()),
                whole = VillageWholeSituation(list = emptyList()),
            )

        val view =
            VillageSituationView(
                village = village,
                day = 0,
                villageSituation = situation,
                charachips = Charachips(list = emptyList()),
                myself = null,
                player = null,
                isViewableSpoilerContent = false,
            )

        assertNull(view.roomAssignedRowList)
        assertNull(view.roomWidth)
        assertNull(view.vote)
        assertFalse(view.isViewableSpoilerContent)
        // ステータス別の参加者グルーピングは live situation の構造をそのまま写す
        assertEquals(
            listOf("生存", "処刑死", "無惨", "後追", "突然", "見学"),
            view.memberList.map { it.status },
        )
        assertEquals(1, view.footstepList.size)
        assertEquals(1, view.footstepList[0].day)
        assertEquals("01、02", view.footstepList[0].footstep)
    }
}
