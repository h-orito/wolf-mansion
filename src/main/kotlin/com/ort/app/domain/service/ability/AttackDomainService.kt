package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class AttackDomainService(
    private val roomDomainService: RoomDomainService,
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService,
    private val cohabitDomainService: CohabitDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.襲撃)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        if (village.latestDay() == 1) return listOf(village.dummyParticipant())
        val baseTargetList = village.participants
            .filterAlive()
            .sortedByRoomNumber().list
            .filterNot { it.skill!!.hasAttackAbility() }

        return when (myself.skill!!.toCdef()) {
            CDef.Skill.歩狼 -> {
                val roomList = roomDomainService.detectWasdRoomNumbers(myself.room!!, village.roomSize!!)
                val targetList = baseTargetList.filter { roomList.contains(it.room!!.number) }
                if (targetList.isEmpty()) baseTargetList
                else targetList
            }
            CDef.Skill.銀狼 -> {
                val roomList = roomDomainService.detectGinRoomNumbers(myself.room!!, village.roomSize!!)
                val targetList = baseTargetList.filter { roomList.contains(it.room!!.number) }
                if (targetList.isEmpty()) baseTargetList
                else targetList
            }
            CDef.Skill.金狼 -> {
                val roomList = roomDomainService.detectKinRoomNumbers(myself.room!!, village.roomSize!!)
                val targetList = baseTargetList.filter { roomList.contains(it.room!!.number) }
                if (targetList.isEmpty()) baseTargetList
                else targetList
            }
            CDef.Skill.王狼 -> {
                val roomList = roomDomainService.detectKingRoomNumbers(myself.room!!, village.roomSize!!)
                val targetList = baseTargetList.filter { roomList.contains(it.room!!.number) }
                if (targetList.isEmpty()) baseTargetList
                else targetList
            }
            CDef.Skill.飛狼 -> {
                val roomList = roomDomainService.detectHishaRoomNumbers(myself.room!!, village.roomSize!!)
                val targetList = baseTargetList.filter { roomList.contains(it.room!!.number) }
                if (targetList.isEmpty()) baseTargetList
                else targetList
            }
            CDef.Skill.角狼 -> {
                val roomList = roomDomainService.detectKakuRoomNumbers(myself.room!!, village.roomSize!!)
                val targetList = baseTargetList.filter { roomList.contains(it.room!!.number) }
                if (targetList.isEmpty()) baseTargetList
                else targetList
            }
            else -> baseTargetList
        }
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? {
        val attackerCharaIds = village.participants.list.filter { it.skill!!.hasAttackAbility() }.map { it.charaId }
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .list
            .firstOrNull { attackerCharaIds.contains(it.charaId) }
            ?.let { village.participants.chara(it.targetCharaId!!) }
    }

    override fun isAvailableNoTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): Boolean = false

    override fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        val attacks = abilities.filterByType(abilityType).filterPastDay(day).sortedByDay()
        val wolfCharaIds = village.participants.list.filter { it.skill!!.hasAttackAbility() }.map { it.charaId }
        val wolfFootsteps = footsteps.list.filter { wolfCharaIds.contains(it.charaId) }
        return attacks.list.map { ability ->
            val footstep = wolfFootsteps.firstOrNull { it.day == ability.day }?.roomNumbers ?: "なし"
            val from = village.participants.chara(ability.charaId)
            val target = village.participants.chara(ability.targetCharaId!!)
            "${ability.day}日目 ${from.nameWhen(ability.day)} が ${target.nameWhen(ability.day)} を襲撃する（$footstep）"
        }
    }

    override fun assertAbility(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?,
        abilities: Abilities,
        footsteps: Footsteps
    ) {
        // 襲撃者
        if (getAttackableWolfs(village, village.latestDay(), abilities).none { it.charaId == charaId }) {
            throw WolfMansionBusinessException("選択できない襲撃者を指定しています")
        }
        // 襲撃対象
        if (getSelectableTargetList(village, myself, abilities).none { it.charaId == targetCharaId }) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        // 足音
        footstepDomainService.assertFootstep(village, charaId!!, targetCharaId, footstep)
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val attacker = village.participants.chara(charaId!!)
        val target = village.participants.chara(targetCharaId!!)
        return "襲撃者を${attacker.name()}に、襲撃対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetPrefix(): String? = "襲撃対象"
    override fun isTargetingAndFootstep(): Boolean = true

    fun getAttackableWolfs(village: Village, day: Int, abilities: Abilities): List<VillageParticipant> {
        val aliveWolfs =
            village.participants.filterAlive().sortedByRoomNumber().list.filter { it.skill!!.hasAttackAbility() }
        if (village.setting.rule.isAvailableSameWolfAttack || aliveWolfs.size == 1) {
            return aliveWolfs
        }
        // 連続襲撃不可なので昨日襲撃した狼以外
        val yesterdayAttack =
            abilities.filterByDay(day - 1).filterByType(AbilityType(CDef.AbilityType.襲撃)).list.firstOrNull()
        return aliveWolfs.filterNot { it.charaId == yesterdayAttack?.charaId }
    }

    fun getSelectingAttacker(village: Village, myself: VillageParticipant, abilities: Abilities): VillageParticipant? {
        val attackerCharaIds = village.participants.list.filter { it.skill!!.hasAttackAbility() }.map { it.charaId }
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .list
            .firstOrNull { attackerCharaIds.contains(it.charaId) }
            ?.let { village.participants.chara(it.charaId) }
    }

    fun getSelectingFootstep(
        village: Village,
        myself: VillageParticipant,
        footsteps: Footsteps
    ): String? {
        val attackerCharaIds = village.participants.list.filter { it.skill!!.hasAttackAbility() }.map { it.charaId }
        return footsteps
            .filterByDay(village.latestDay())
            .list
            .firstOrNull { attackerCharaIds.contains(it.charaId) }
            ?.roomNumbers
    }

    fun assertAttacker(village: Village, myself: VillageParticipant, charaId: Int, abilities: Abilities) {
        if (myself.skill?.hasAttackAbility() != true) throw WolfMansionBusinessException("不正")
        if (getAttackableWolfs(village, village.latestDay(), abilities).none { it.charaId == charaId }) {
            throw WolfMansionBusinessException("選択できない襲撃者を選択しています")
        }
    }

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        // 襲撃担当
        val attackers = getAttackableWolfs(village, village.latestDay(), daychange.abilities)
        if (attackers.isEmpty()) return daychange
        val attacker = attackers.shuffled().first()
        // 襲撃対象
        val target = getSelectableTargetList(village, attacker, daychange.abilities).shuffled().first()
        val ability = Ability(
            day = village.latestDay(),
            charaId = attacker.charaId,
            targetCharaId = target.charaId,
            type = abilityType
        )
        val footstep = Footstep(
            day = village.latestDay(),
            charaId = attacker.charaId,
            roomNumbers = footstepDomainService.getCandidateList(village, attacker.charaId, target.charaId).shuffled()
                .first()
        )
        return daychange.copy(
            abilities = daychange.abilities.add(ability),
            footsteps = daychange.footsteps.add(footstep)
        )
    }

    fun attack(daychange: Daychange, charas: Charas): Daychange {
        var village = daychange.village.copy()

        // 人狼が生存していない、能力セットしていない場合終了
        if (village.participants.filterAlive().list.none { it.skill!!.hasAttackAbility() }) return daychange
        val ability = daychange.abilities
            .filterByDay(village.latestDay() - 1)
            .filterByType(abilityType).list.firstOrNull()
            ?: return daychange
        val target = village.participants.chara(ability.targetCharaId!!)

        var messages = daychange.messages.copy()
        // 襲撃メッセージ
        if (village.latestDay() > 2) messages = messages.add(createAttackMessage(village, target, charas))

        // 襲撃失敗していたら誰も死亡させず終了
        if (!isAttackSuccess(daychange, target)) {
            return daychange.copy(messages = messages)
        }

        // 同棲者がいる部屋だったら移動元の同棲者も死亡
        if (cohabitDomainService.isCohabiting(daychange, target)) {
            val cohabitor = target.getTargetCohabitor(village)!!
            village = village.attackedParticipant(cohabitor.id)
        }
        // 対象者は死亡
        village = village.attackedParticipant(target.id)

        // 襲撃したのが智狼の場合メッセージ追加
        village.participants.chara(ability.charaId).let { attacker ->
            if (attacker.skill!!.toCdef() == CDef.Skill.智狼 && attacker.isAlive()) {
                messages = messages.add(createWiseWolfMessage(village))
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createAttackMessage(
        village: Village,
        target: VillageParticipant,
        charas: Charas
    ): Message {
        val attacker =
            village.participants.filterAlive().list.filter { it.skill!!.hasAttackAbility() }.shuffled().first()
        val text = "${target.name()}！今日がお前の命日だ！"
        val faceType = if (hasFaceType(attacker, charas)) CDef.FaceType.囁き else CDef.FaceType.通常
        return messageDomainService.createAttackMessage(
            village,
            attacker,
            text,
            faceType.toModel(),
            CDef.MessageType.人狼の囁き.toModel()
        )
    }

    private fun hasFaceType(attacker: VillageParticipant, charas: Charas): Boolean =
        charas.chara(attacker.charaId).images.list.any { it.faceType.toCdef() == CDef.FaceType.囁き }

    fun isAttackSuccess(daychange: Daychange, target: VillageParticipant): Boolean {
        // 既に死亡している
        if (target.isDead()) return false
        // 護衛されている
        if (daychange.guarded.any { it.id == target.id }) return false
        // 襲撃耐性あり
        if (target.skill!!.isNoDeadByAttack()) return false
        // 不在
        if (cohabitDomainService.isAbsence(daychange, target)) return false

        return true
    }

    private fun createWiseWolfMessage(village: Village): Message {
        val text = village.participants
            .filterDeadDay(village.latestDay()).list
            .filter { it.dead.isAttacked() }
            .joinToString(separator = "\n") {
                "${it.name()}は${it.skill!!.name}だったようだ。"
            }
        return messageDomainService.createPublicAbilityMessage(
            village = village,
            text = text,
            messageType = CDef.MessageType.襲撃結果.toModel()
        )
    }
}