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
class WandererDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.風来護衛)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        val day = village.latestDay()
        if (day < 2) return emptyList()
        // 一度守った人は守れない
        val pastTargetCharaIds = abilities
            .filterPastDay(village.latestDay())
            .filterByCharaId(myself.charaId)
            .filterByType(abilityType).list.map { it.targetCharaId }
        return village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber()
            .list.filterNot { pastTargetCharaIds.contains(it.charaId) }
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
        targetCharaId ?: return // 対象なしを選べる
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
        targetCharaId ?: return "${myself.name()}が護衛対象をなしに設定しました。" // 護衛なしを選べる
        val target = village.participants.chara(targetCharaId!!)
        return "${myself.name()}が護衛対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetSuffix(): String? = "を護衛する"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    // 護衛なしを選べる
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true

    fun wandererGuard(daychange: Daychange): Daychange {
        val village = daychange.village
        var guarded = daychange.guarded
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.風来狩人.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            guarded = (guarded + target).distinct()
            messages = messages.add(createGuardMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages, guarded = guarded)
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