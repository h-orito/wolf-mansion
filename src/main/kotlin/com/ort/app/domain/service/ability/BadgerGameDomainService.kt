package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class BadgerGameDomainService(
    private val seduceDomainService: SeduceDomainService,
    private val attackDomainService: AttackDomainService,
    private val cohabitDomainService: CohabitDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.美人局)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        // 前日以前に能力行使していたらもう使えない
        if (abilities.filterPastDay(village.latestDay()).filterByType(abilityType)
                .filterByCharaId(myself.charaId).list.isNotEmpty()
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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を誘惑して脅す（$footstep）"
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
        if (targetCharaId != null
            && getSelectableTargetList(village, myself, abilities).none { it.charaId == targetCharaId }
        ) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        if (targetCharaId != null) {
            // 足音
            footstepDomainService.assertFootstep(village, myself.charaId, targetCharaId, footstep)
        }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) "${myself.name()}が美人局の対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が美人局の対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String? = "誘惑して脅す対象"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

    fun badgerGame(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.美人局.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 恋絆を結ぶ
            village = village.seduceParticipant(it.id, target.id)
            messages = messages.add(seduceDomainService.createSeduceMessage(village, it, target))
            messages = messages.add(seduceDomainService.createSeducedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    fun badgerGameAttack(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.美人局.toModel()).list.forEach {
            val ability = daychange.abilities
                .filterByDay(village.latestDay() - 2) // 2日前
                .filterByType(abilityType)
                .filterByCharaId(it.charaId)
                .list.firstOrNull() ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            if (!attackDomainService.isAttackSuccess(daychange, target)) return@forEach

            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                village = village.attackedParticipant(cohabitor.id)
            }
            village.attackedParticipant(target.id)
        }

        return daychange.copy(village = village)
    }
}