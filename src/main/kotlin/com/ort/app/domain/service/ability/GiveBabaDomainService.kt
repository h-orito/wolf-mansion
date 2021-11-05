package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
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
class GiveBabaDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    private val abilityType = CDef.AbilityType.ババを渡す.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        // ババになったことがない人
        return village.participants
            .filterNotDummy(village.dummyParticipant()).list
            .filterNot {
                it.skill!!.histories.list.any { h -> h.skill.toCdef() == CDef.Skill.ババ }
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
            .filterByCharaId(myself.charaId).list.firstOrNull()
            ?.let { village.participants.chara(it.targetCharaId!!) }
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} にババを押し付ける（$footstep）"
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
        return if (targetCharaId == null) "${myself.name()}がババを押し付ける対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}がババを押し付ける対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String = "ババを押し付ける対象"

    fun giveBaba(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.ババ.toModel()).list.shuffled().forEach { baba ->
            val ability = daychange.abilities.findYesterday(village, baba, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 既に死亡していたり同棲者の場合は交換しない
            val messageText = createGiveBabaMessage(baba, target)
            if (target.isDead() || target.skill!!.toCdef() == CDef.Skill.同棲者) return@forEach
            // 役職交換
            val targetSkill = target.skill
            village = village.assignParticipantSkill(baba.id, targetSkill)
            village = village.assignParticipantSkill(target.id, CDef.Skill.ババ.toModel())
            messages = messages.add(
                messageDomainService.createPrivateAbilityMessage(
                    village = village,
                    myself = baba,
                    text = messageText,
                    messageType = CDef.MessageType.非公開システムメッセージ.toModel()
                )
            )
        }

        return daychange.copy(messages = messages, village = village)
    }

    private fun createGiveBabaMessage(baba: VillageParticipant, target: VillageParticipant): String {
        return when {
            target.isDead() -> "${target.name()}は死亡しているため、${baba.name()}はババを押し付けられなかった。"
            target.skill!!.toCdef() == CDef.Skill.同棲者 -> "${target.name()}は同棲者のため、${baba.name()}はババを押し付けられなかった。"
            target.skill.toCdef() == CDef.Skill.絶対人狼 -> "${target.name()}は絶対人狼のため、${baba.name()}はババを押し付けられなかった。"
            else -> "${baba.name()}は、${target.name()}にババを押し付けた。"
        }
    }
}