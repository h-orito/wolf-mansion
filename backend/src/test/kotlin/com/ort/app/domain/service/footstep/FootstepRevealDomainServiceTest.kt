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

    // 募集中はそもそも足音が存在しないので controller 側で空リスト返却するが、
    // 念のため "進行中でないなら必ず false" な挙動を検証する。
    @Test
    fun `募集中は誰に対しても false`() {
        val village = createPrologueVillage()
        val footstep = Footstep(day = 0, charaId = 1, roomNumbers = "なし")
        assertFalse(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    // ---------- settled (エピローグ / 終了) は全公開 ----------

    @Test
    fun `エピローグでは未参加者でも全公開される`() {
        val village = createDay1Village().copy(status = CDef.VillageStatus.エピローグ.toModel())
        val footstep = Footstep(day = 1, charaId = 3, roomNumbers = "01,02")
        assertTrue(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    @Test
    fun `終了では未参加者でも全公開される`() {
        val village = createDay1Village().copy(status = CDef.VillageStatus.終了.toModel())
        val footstep = Footstep(day = 1, charaId = 3, roomNumbers = "01,02")
        assertTrue(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    // ---------- 進行中は alive viewer に対しては常に匿名 ----------

    @Test
    fun `進行中、自分の足音であっても匿名扱い (既存挙動と一致)`() {
        val village = createDay1Village()
        val myself = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val footstep = Footstep(day = 1, charaId = myself.charaId, roomNumbers = "01,02")
        assertFalse(service.shouldRevealOwner(village, myself = myself, footstep = footstep))
    }

    @Test
    fun `進行中、人狼から見ても他人の足音は匿名 (team共有しない)`() {
        val village = createDay1Village()
        val wolves = village.participants.list.filter { it.skill?.toCdef() == CDef.Skill.人狼 }
        val viewer = wolves[0]
        val otherWolf = wolves[1]
        val footstep = Footstep(day = 1, charaId = otherWolf.charaId, roomNumbers = "05,06")
        assertFalse(service.shouldRevealOwner(village, myself = viewer, footstep = footstep))
    }

    @Test
    fun `進行中、未ログイン (myself null) でも匿名`() {
        val village = createDay1Village()
        val any = village.participants.list.first()
        val footstep = Footstep(day = 1, charaId = any.charaId, roomNumbers = "07")
        assertFalse(service.shouldRevealOwner(village, myself = null, footstep = footstep))
    }

    // ---------- 進行中、墓下開示村 + 自分が dead / 見学なら全公開 ----------

    @Test
    fun `進行中、墓下開示村で自分が死亡している viewer には全公開`() {
        val baseVillage = createDay1Village()
        val openVillage = baseVillage.copy(
            setting = baseVillage.setting.copy(rule = baseVillage.setting.rule.copy(isOpenSkillInGrave = true))
        )
        val villager = openVillage.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val deadViewer = villager.execute(day = 1)
        val wolf = openVillage.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }
        val footstep = Footstep(day = 1, charaId = wolf.charaId, roomNumbers = "03,04")
        assertTrue(service.shouldRevealOwner(openVillage, myself = deadViewer, footstep = footstep))
    }

    @Test
    fun `進行中、墓下開示村でも alive viewer は匿名のまま`() {
        val baseVillage = createDay1Village()
        val openVillage = baseVillage.copy(
            setting = baseVillage.setting.copy(rule = baseVillage.setting.rule.copy(isOpenSkillInGrave = true))
        )
        val aliveViewer = openVillage.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val wolf = openVillage.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }
        val footstep = Footstep(day = 1, charaId = wolf.charaId, roomNumbers = "03,04")
        assertFalse(service.shouldRevealOwner(openVillage, myself = aliveViewer, footstep = footstep))
    }

    @Test
    fun `進行中、墓下開示村で見学者の viewer にも全公開`() {
        val baseVillage = createDay1Village()
        val openVillage = baseVillage.copy(
            setting = baseVillage.setting.copy(rule = baseVillage.setting.rule.copy(isOpenSkillInGrave = true))
        )
        val villager = openVillage.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val spectatorViewer = villager.copy(isSpectator = true)
        val wolf = openVillage.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }
        val footstep = Footstep(day = 1, charaId = wolf.charaId, roomNumbers = "10,11")
        assertTrue(service.shouldRevealOwner(openVillage, myself = spectatorViewer, footstep = footstep))
    }

    @Test
    fun `進行中、isOpenSkillInGrave=false の村では dead viewer でも匿名 (既存挙動)`() {
        // createDay1Village の rule は isOpenSkillInGrave=false がデフォルト
        val village = createDay1Village()
        val villager = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.村人 }
        val deadViewer = villager.execute(day = 1)
        val wolf = village.participants.list.first { it.skill?.toCdef() == CDef.Skill.人狼 }
        val footstep = Footstep(day = 1, charaId = wolf.charaId, roomNumbers = "08,09")
        assertFalse(service.shouldRevealOwner(village, myself = deadViewer, footstep = footstep))
    }
}
