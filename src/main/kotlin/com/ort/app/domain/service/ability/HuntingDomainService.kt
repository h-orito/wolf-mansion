package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class HuntingDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val attackDomainService: AttackDomainService,
    private val messageDomainService: MessageDomainService,
    private val cohabitDomainService: CohabitDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.狩猟)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        // 一度使うと使えない
        if (abilities.filterByType(abilityType).filterByCharaId(myself.charaId)
                .filterPastDay(village.latestDay()).list.isNotEmpty()
        ) {
            return emptyList()
        }
        return village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber().list
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? {
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.firstOrNull()
            ?.let { village.participants.chara(it.targetCharaId!!) }
    }

    override fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        return abilities
            .filterPastDay(day)
            .filterByCharaId(myself.charaId)
            .filterByType(abilityType)
            .sortedByDay().list.map {
                val abilityDay = it.day
                val footstep = footsteps
                    .filterByDay(abilityDay)
                    .filterByCharaId(it.charaId).list
                    .firstOrNull()
                    ?.roomNumbers ?: "なし"
                val target = village.participants.chara(it.targetCharaId!!)
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を狩猟する（${footstep}）"
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
        // 襲撃対象
        if (targetCharaId != null && getSelectableTargetList(
                village,
                myself,
                abilities
            ).none { it.charaId == targetCharaId }
        ) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        // 足音
        footstepDomainService.assertFootstep(village, myself.charaId, targetCharaId, footstep)
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val targetName = targetCharaId?.let { village.participants.chara(it).name() } ?: "なし"
        return "${myself.name()}が狩猟対象を${targetName}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetPrefix(): String? = "狩猟対象"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun hunting(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.マタギ.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType)
                ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 狩猟メッセージ
            messages = messages.add(createAttackMessage(village, it, target))

            if (!isAttackSuccess(daychange, target)) return@forEach

            village = village.attackedParticipant(target.id)

            // 対象が人狼系、妖狐系、一匹狼でない場合、自身も襲撃死
            if (!isBeast(target)) {
                messages = messages.add(createSuicideMessage(village, it))
                village = village.attackedParticipant(it.id)
            }

            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                village = village.attackedParticipant(cohabitor.id)
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createAttackMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は猟銃を構え、${target.name()}に向かって発砲した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createSuicideMessage(village: Village, myself: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${myself.name()}は、獣でない者に発砲したことに責任を感じ、自身に発砲した。",
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    fun isAttackSuccess(daychange: Daychange, target: VillageParticipant): Boolean {
        // 既に死亡している
        if (target.isDead()) return false
        // 護衛されている
        if (daychange.guarded.any { it.id == target.id }) return false
        // 不在
        if (cohabitDomainService.isAbsence(daychange, target)) return false

        return true
    }

    private fun isBeast(target: VillageParticipant): Boolean {
        return target.skill!!.let {
            it.hasAttackAbility() ||
                    it.isFoxCount() ||
                    it.toCdef() == CDef.Skill.一匹狼
        }
    }
}