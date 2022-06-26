package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
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

    override val abilityType = AbilityType(CDef.AbilityType.襲撃希望)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        if (village.latestDay() == 1) return listOf(village.dummyParticipant())
        val baseTargetList = village.participants
            .filterAlive()
            .sortedByRoomNumber().list
            .filterNot { it.skill!!.hasAttackAbility() }

        return when (myself.skill!!.toCdef()) {
            CDef.Skill.歩狼 -> {
                val roomList = roomDomainService.detectWasdRoomNumbers(myself.room!!, village.roomSize!!)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            CDef.Skill.銀狼 -> {
                val roomList = roomDomainService.detectGinRoomNumbers(myself.room!!, village.roomSize!!)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            CDef.Skill.金狼 -> {
                val roomList = roomDomainService.detectKinRoomNumbers(myself.room!!, village.roomSize!!)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            CDef.Skill.王狼 -> {
                val roomList = roomDomainService.detectKingRoomNumbers(myself.room!!, village.roomSize!!)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            CDef.Skill.飛狼 -> {
                val roomList = roomDomainService.detectHishaRoomNumbers(myself.room!!, village.roomSize!!)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            CDef.Skill.角狼 -> {
                val roomList = roomDomainService.detectKakuRoomNumbers(myself.room!!, village.roomSize!!)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            CDef.Skill.静狼 -> {
                val roomList = roomDomainService.detectSilentRoomNumbers(myself, village)
                baseTargetList.filter { roomList.contains(it.room!!.number) }.ifEmpty { baseTargetList }
            }
            else -> baseTargetList
        }
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? {
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId)
            .list
            .firstOrNull()
            ?.let { village.participants.chara(it.targetCharaId!!) }
    }

    override fun getSelectingTargetMessage(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): String? {
        val attacker = getSelectingAttacker(village, myself, abilities) ?: return null
        val target = getSelectingTarget(village, myself, abilities) ?: return null
        return "${attacker.name()} が ${target.name()} を襲撃する"
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean =
        false

    override fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        val attacks = abilities.filterByType(CDef.AbilityType.襲撃.toModel()).filterPastDay(day).sortedByDay()
        val wolfCharaIds = village.participants.list.filter { it.skill!!.hasAttackAbility() }.map { it.charaId }
        val wolfFootsteps = footsteps.list.filter { wolfCharaIds.contains(it.charaId) }
        return attacks.list.map { ability ->
            val footstep = wolfFootsteps.firstOrNull { it.day == ability.day }?.roomNumbers ?: "なし"
            val from = village.participants.chara(ability.attackerCharaId!!)
            val target = village.participants.chara(ability.targetCharaId!!)
            "${ability.day}日目 ${from.nameWhen(ability.day)} が ${target.nameWhen(ability.day)} を襲撃する（$footstep）"
        }
    }

    override fun assertAbility(
        village: Village,
        myself: VillageParticipant,
        attackerCharaId: Int?,
        targetCharaId: Int?,
        footstep: String?,
        abilities: Abilities,
        footsteps: Footsteps,
        votes: Votes,
        defaultFootstepAsserter: () -> Unit
    ) {
        // 襲撃者
        if (getAttackableWolfs(village, village.latestDay(), abilities).none { it.charaId == attackerCharaId }) {
            throw WolfMansionBusinessException("選択できない襲撃者を指定しています")
        }
        // 襲撃対象
        val attacker = village.participants.chara(attackerCharaId!!)
        if (getSelectableTargetList(village, attacker, abilities, votes).none { it.charaId == targetCharaId }) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        // 足音
        defaultFootstepAsserter.invoke()
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        attackerCharaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val attacker = village.participants.chara(attackerCharaId!!)
        val target = village.participants.chara(targetCharaId!!)
        return "${myself.name()}が、襲撃者を${attacker.name()}に、襲撃対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetPrefix(): String = "襲撃対象"
    override fun getTargetSuffix(): String = "を襲撃する"
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
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId)
            .list
            .firstOrNull()
            ?.let { village.participants.chara(it.attackerCharaId!!) }
    }

    fun getSelectingFootstep(
        village: Village,
        myself: VillageParticipant,
        footsteps: Footsteps
    ): String? {
        return footsteps
            .filterByDay(village.latestDay())
            .filterByRegisterCharaId(myself.charaId)
            .list
            .firstOrNull()
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
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()

        // 生存している人狼
        village.participants.filterAlive().list.filter { it.skill!!.hasAttackAbility() }.forEach { wolf ->
            val attacker = attackers.shuffled().first()
            // 襲撃対象
            val target =
                getSelectableTargetList(village, attacker, daychange.abilities, daychange.votes).shuffled().first()
            val ability = Ability(
                day = village.latestDay(),
                charaId = wolf.charaId,
                attackerCharaId = attacker.charaId,
                targetCharaId = target.charaId,
                type = abilityType
            )
            val footstep = Footstep(
                day = village.latestDay(),
                registerCharaId = wolf.charaId,
                charaId = attacker.charaId,
                roomNumbers = footstepDomainService.getCandidateList(village, attacker.charaId, target.charaId)
                    .shuffled()
                    .first()
            )
            abilities = abilities.add(ability)
            footsteps = footsteps.add(footstep)
        }

        return daychange.copy(
            abilities = abilities,
            footsteps = footsteps
        )
    }

    fun attack(daychange: Daychange, charas: Charas): Daychange {
        var village = daychange.village.copy()
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        var messages = daychange.messages.copy()

        // 人狼が生存していない、能力セットしていない場合終了
        if (village.participants.filterAlive().list.none { it.skill!!.hasAttackAbility() }) return daychange

        // 集計して襲撃内容を確定
        val ability = decideAbility(daychange) ?: return daychange
        abilities = abilities.add(ability.copy(type = CDef.AbilityType.襲撃.toModel()))
        createEachAttackRequestMessage(daychange)?.let { messages = messages.add(it) }

        // 前日の襲撃希望の足音を削除
        footsteps = footsteps.copy(
            list = footsteps.list.filterNot { footstep ->
                village.participants.chara(footstep.registerCharaId).skill!!.hasAttackAbility()
                        && footstep.day == village.latestDay() - 1
                        && footstep.registerCharaId != ability.charaId
            }
        )

        // 襲撃者
        val attacker = village.participants.chara(ability.charaId)
        // 襲撃対象
        val target = village.participants.chara(ability.targetCharaId!!)

        // 襲撃メッセージ
        if (village.latestDay() > 2) messages = messages.add(createAttackMessage(village, attacker, target, charas))

        // 襲撃失敗していたら誰も死亡させず終了
        if (!isAttackSuccess(daychange, target)) {
            // 将棋狼で特定条件を満たした場合、人狼に成る
            if (shouldUpgradeShogiWolf(daychange, attacker, target)) {
                village = village.assignParticipantSkill(attacker.id, CDef.Skill.人狼.toModel())
                messages = messages.add(createUpgradeShogiWolfMessage(village, attacker))
            }
            // 静狼が特定条件を満たした場合、呪狼になる
            if (shouldUpgradeSilentWolf(daychange, attacker, target)) {
                village = village.assignParticipantSkill(attacker.id, CDef.Skill.呪狼.toModel())
                messages = messages.add(createUpgradeSilentWolfMessage(village, attacker))
            }

            return daychange.copy(
                village = village,
                messages = messages,
                abilities = abilities,
                footsteps = footsteps
            )
        }

        // 同棲者がいる部屋だったら移動元の同棲者も死亡
        if (cohabitDomainService.isCohabiting(daychange, target)) {
            val cohabitor = target.getTargetCohabitor(village)!!
            village = village.attackedParticipant(cohabitor.id)
        }
        // 対象者は死亡
        village = village.attackedParticipant(target.id)

        // 襲撃したのが智狼の場合メッセージ追加
        if (attacker.skill!!.toCdef() == CDef.Skill.智狼 && attacker.isAlive()) {
            messages = messages.add(createWiseWolfMessage(village))
        }

        return daychange.copy(
            village = village,
            messages = messages,
            abilities = abilities,
            footsteps = footsteps
        )
    }

    private fun decideAbility(daychange: Daychange): Ability? {
        val abilities = daychange.abilities
            .filterByDay(daychange.village.latestDay() - 1)
            .filterByType(abilityType).list
        if (abilities.isEmpty()) return null
        val footsteps = daychange.footsteps
            .filterByDay(daychange.village.latestDay() - 1)
        val groups = abilities.groupBy { ability ->
            val footstep = footsteps.list.first { it.charaId == ability.attackerCharaId }
            "${ability.attackerCharaId}_${ability.targetCharaId}_${footstep.roomNumbers}"
        }
        // 襲撃者x襲撃対象x足音で数を集計して一番多かったものを採用
        // 複数ある場合はランダム
        val maxCount = groups.map { it.value.size }.maxOrNull()!!
        return groups
            .filter { it.value.size == maxCount }
            .entries.shuffled().first().value.first()
    }

    private fun createEachAttackRequestMessage(daychange: Daychange): Message? {
        val village = daychange.village
        val abilities = daychange.abilities
            .filterByDay(village.latestDay() - 1)
            .filterByType(abilityType).list
        if (abilities.isEmpty()) return null
        val footsteps = daychange.footsteps
            .filterByDay(village.latestDay() - 1)

        val fromMaxLength = abilities.maxOf { village.participants.chara(it.charaId).name().length }
        val text = abilities.sortedBy { village.participants.chara(it.charaId).room!!.number }
            .joinToString(
                separator = "\n",
                prefix = "襲撃希望は以下の通り。\n"
            ) {
                val from = village.participants.chara(it.charaId).name().padEnd(fromMaxLength, '　')
                val attacker = village.participants.chara(it.attackerCharaId!!)
                val target = village.participants.chara(it.targetCharaId!!)
                val footstep = footsteps.filterByRegisterCharaId(it.charaId).list.first()
                "$from → ${attacker.shortName()}が${target.shortName()}を襲う（${footstep.roomNumbers}）"
            }
        return messageDomainService.createEachVoteMessage(village, text, CDef.MessageType.非公開システムメッセージ.toModel())
    }

    private fun createAttackMessage(
        village: Village,
        attacker: VillageParticipant,
        target: VillageParticipant,
        charas: Charas
    ): Message {
        val text = "${target.name()}！今日がお前の命日だ！"
        val attackerChara = charas.chara(attacker.charaId)
        val faceType = if (hasFaceType(attackerChara)) CDef.FaceType.囁き.toModel() else charas.chara(attacker.charaId)
            .defaultImage().faceType
        return messageDomainService.createAttackMessage(
            village,
            attacker,
            text,
            faceType,
            CDef.MessageType.人狼の囁き.toModel()
        )
    }

    private fun createUpgradeShogiWolfMessage(village: Village, attacker: VillageParticipant): Message {
        return messageDomainService.createPublicAbilityMessage(
            village = village,
            text = "${attacker.name()}は、人狼に成った。",
            messageType = CDef.MessageType.襲撃結果.toModel()
        )
    }

    private fun createUpgradeSilentWolfMessage(village: Village, attacker: VillageParticipant): Message {
        return messageDomainService.createPublicAbilityMessage(
            village = village,
            text = "${attacker.name()}は、静かに懐から藁人形を取り出し、呪狼となった。",
            messageType = CDef.MessageType.襲撃結果.toModel()
        )
    }

    private fun hasFaceType(chara: Chara): Boolean =
        chara.images.list.any { it.faceType.toCdef() == CDef.FaceType.囁き }

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

    private fun shouldUpgradeShogiWolf(
        daychange: Daychange,
        wolf: VillageParticipant,
        target: VillageParticipant
    ): Boolean {
        if (!wolf.skill!!.isShogiWolf()) return false
        // 対象が死亡していなくて護衛か襲撃耐性の場合
        if (target.isDead()) return false
        return daychange.guarded.any { it.id == target.id } || target.skill!!.isNoDeadByAttack()
    }

    private fun shouldUpgradeSilentWolf(
        daychange: Daychange,
        wolf: VillageParticipant,
        target: VillageParticipant
    ): Boolean {
        if (wolf.skill!!.toCdef() != CDef.Skill.静狼) return false
        // 対象が死亡していなくて護衛か襲撃耐性の場合
        if (target.isDead()) return false
        return daychange.guarded.any { it.id == target.id } || target.skill!!.isNoDeadByAttack()
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