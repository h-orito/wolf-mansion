package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class TranslateDomainService(
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.翻訳.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getAliveTargets(village)

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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} の言葉を翻訳する（$footstep）"
            }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) "${myself.name()}が翻訳する対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が翻訳する対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String = "翻訳する対象"

    fun translate(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.翻訳者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createRainbowMessage(village, target))
        }

        return daychange.copy(messages = messages)
    }

    private fun createRainbowMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}が突然他国の言葉しか話せなくなり、有識者に翻訳してもらうことになった。"
        )
    }
}