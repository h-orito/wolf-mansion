package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.web.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class GuardDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.護衛)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        val day = village.latestDay()
        if (day < 2) return emptyList()
        val targets = village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber()
        if (village.setting.rule.isAvailableGuardSameTarget) return targets.list
        val yesterdayAbility =
            abilities.filterByType(abilityType).filterByCharaId(myself.charaId).filterByDay(day - 1).list.firstOrNull()
        return targets.list.filterNot { it.charaId == yesterdayAbility?.targetCharaId }
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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を護衛する（${footstep}）"
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
        // 対象
        if (getSelectableTargetList(village, myself, abilities).none { it.charaId == targetCharaId }) {
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
        val target = village.participants.chara(targetCharaId!!)
        return "${myself.name()}が護衛対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetSuffix(): String? = "を護衛する"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        if (!canUseDay(village.latestDay())) return daychange
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.狩人.toModel()).list.forEach {
            val target = getSelectableTargetList(village, it, abilities).shuffled().first()
            val ability = Ability(
                day = village.latestDay(),
                type = abilityType,
                charaId = it.charaId,
                targetCharaId = target.charaId
            )
            abilities = abilities.add(ability)
            val footstep = Footstep(
                day = village.latestDay(),
                charaId = it.charaId,
                roomNumbers = footstepDomainService.getCandidateList(village, it.charaId, target.charaId).shuffled()
                    .first()
            )
            footsteps = footsteps.add(footstep)
        }
        return daychange.copy(
            abilities = abilities,
            footsteps = footsteps
        )
    }

    fun guard(daychange: Daychange): Daychange {
        val village = daychange.village
        var guarded = daychange.guarded
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.狩人.toModel()).list.forEach {
            val ability = daychange.abilities
                .filterByDay(village.latestDay() - 1)
                .filterByType(abilityType)
                .filterByCharaId(it.charaId)
                .list.firstOrNull() ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            guarded = (guarded + target).distinct()
            messages = messages.add(createGuardMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createGuardMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}を護衛している。",
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }
}