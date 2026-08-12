package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImages
import com.ort.app.domain.model.chara.CharaSize
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.createVillageParticipant
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class VillageParticipantViewTest {
    @Test
    fun `非公開時は役職・陣営・状態が隠される`() {
        val participant = createVillageParticipant(skill = Skill(CDef.Skill.村人), id = 1)

        val view = VillageParticipantView(participant, createChara(participant.charaId), shouldHidePrivate = true)

        assertNull(view.skill)
        assertNull(view.camp)
        assertNull(view.statuses)
    }

    @Test
    fun `公開時は役職変化履歴が日昇順で返る`() {
        val participant =
            createVillageParticipant(skill = Skill(CDef.Skill.人狼), id = 1)
                .assignSkill(Skill(CDef.Skill.村人), day = 3)

        val view = VillageParticipantView(participant, createChara(participant.charaId), shouldHidePrivate = false)

        val histories = view.skill!!.histories
        assertEquals(2, histories.size)
        assertEquals(1, histories[0].day)
        assertEquals(CDef.Skill.人狼.code(), histories[0].code)
        assertEquals(CDef.Skill.人狼.alias(), histories[0].name)
        assertEquals(3, histories[1].day)
        assertEquals(CDef.Skill.村人.code(), histories[1].code)
    }

    @Test
    fun `公開時は状態ラベルが返る`() {
        val participant = createVillageParticipant(skill = Skill(CDef.Skill.村人), id = 1)
        val inLove = participant.copy(status = participant.status.addLover(2).addCurseMark())

        val view = VillageParticipantView(inLove, createChara(inLove.charaId), shouldHidePrivate = false)

        assertEquals(listOf("恋絆", "呪縛符"), view.statuses)
    }

    @Test
    fun `状態がなければ空リストが返る`() {
        val participant = createVillageParticipant(skill = Skill(CDef.Skill.村人), id = 1)

        val view = VillageParticipantView(participant, createChara(participant.charaId), shouldHidePrivate = false)

        assertEquals(emptyList<String>(), view.statuses)
    }

    private fun createChara(charaId: Int): Chara =
        Chara(
            id = charaId,
            name = "name$charaId",
            shortName = "s",
            defaultJoinMessage = null,
            defaultFirstdayMessage = null,
            size = CharaSize(width = 50, height = 77),
            images = CharaImages(list = emptyList()),
        )
}
