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
class ResuscitateDomainService(
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.蘇生.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 一度使うと使えない
        return if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) emptyList()
        else village.participants
            .filterDead()
            .filterNotDummy(village.dummyParticipant())
            .sortedByRoomNumber().list
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 2

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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を蘇生する（$footstep）"
            }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) "${myself.name()}が蘇生する対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が蘇生する対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String = "蘇生する対象"

    fun resuscitate(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.蘇生者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 蘇生済み/同棲者の場合は失敗
            if (target.isAlive() || target.skill!!.toCdef() == CDef.Skill.同棲者) return@forEach
            village = village.reviveParticipant(target.id)
            messages = messages.add(createResusciateMessage(village, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createResusciateMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}は、不思議な力により蘇った。"
        )
    }
}
