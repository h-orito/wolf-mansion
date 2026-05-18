package com.ort.app.domain.service.footstep

import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.app.domain.model.village.toModel
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FootstepRevealDomainServiceTest {

    private val service = FootstepRevealDomainService()

    // ---------- 募集中 ----------

    @Test
    fun `募集中はそもそも未登録なので呼ばれても false を返す`() {
        val village = createPrologueVillage()
        val footstep = Footstep(day = 0, charaId = 1, roomNumbers = "なし")
        // 参加者がいないので myself = null
        assertFalse(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    // ---------- エピローグ / 終了 ----------

    @Test
    fun `エピローグでは未参加者でも全公開される`() {
        val village = createDay1Village().let { it.copy(status = CDef.VillageStatus.エピローグ.toModel()) }
        val footstep = Footstep(day = 1, charaId = 3, roomNumbers = "01,02")
        assertTrue(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    @Test
    fun `終了では未参加者でも全公開される`() {
        val village = createDay1Village().let { it.copy(status = CDef.VillageStatus.終了.toModel()) }
        val footstep = Footstep(day = 1, charaId = 3, roomNumbers = "01,02")
        assertTrue(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    // ---------- 進行中 ----------

    @Test
    fun `進行中、自分の足音は開示される`() {
        val village = createDay1Village()
        // organize "村狼狼狼魔狐賢導狩共共霊霊霊霊霊霊" の先頭 = 村 (id=2)
        val myself = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val footstep = Footstep(day = 1, charaId = myself.charaId, roomNumbers = "01,02")
        assertTrue(service.shouldRevealOwner(village, myself = myself, footstep = footstep))
    }

    @Test
    fun `進行中、他人 (異陣営) の足音は隠蔽される`() {
        val village = createDay1Village()
        val villager = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val wolf = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }
        val footstep = Footstep(day = 1, charaId = wolf.charaId, roomNumbers = "03,04")
        assertFalse(service.shouldRevealOwner(village, myself = villager, footstep = footstep))
    }

    @Test
    fun `進行中、同じ人狼陣営の足音は開示される (チーム共有)`() {
        val village = createDay1Village()
        val wolves = village.participants.list.filter { it.skill?.toCdef() == CDef.Skill.人狼 }
        // 2 匹以上の人狼がいる前提 (createDay1Village の organize に "狼狼狼" を含む)
        val viewer = wolves[0]
        val other = wolves[1]
        val footstep = Footstep(day = 1, charaId = other.charaId, roomNumbers = "05,06")
        assertTrue(service.shouldRevealOwner(village, myself = viewer, footstep = footstep))
    }

    @Test
    fun `進行中、未ログイン (myself null) の閲覧者には他人の足音は隠蔽される`() {
        val village = createDay1Village()
        val any = village.participants.list.first()
        val footstep = Footstep(day = 1, charaId = any.charaId, roomNumbers = "07")
        assertFalse(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    @Test
    fun `進行中、共鳴者は他の共鳴者の足音を見られる`() {
        val village = createDay1Village()
        val sympathizers = village.participants.list.filter { it.skill?.toCdef() == CDef.Skill.共鳴者 }
        val viewer = sympathizers[0]
        val other = sympathizers[1]
        val footstep = Footstep(day = 1, charaId = other.charaId, roomNumbers = "08,09")
        assertTrue(service.shouldRevealOwner(village, myself = viewer, footstep = footstep))
    }
}
