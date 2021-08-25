package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class CourtDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    private val abilityType = CDef.AbilityType.求愛.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        if (village.latestDay() != 1) return emptyList()
        return village.participants
            .filterAlive()
            .filterNotDummy(village.dummyParticipant())
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
                val target = village.participants.chara(it.targetCharaId!!)
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} に求愛する"
            }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val target = village.participants.chara(targetCharaId!!)
        return "${myself.name()}が求愛対象を${target.name()}に設定しました。"
    }

    override fun getTargetPrefix(): String? = "求愛対象"
    override fun canUseDay(day: Int): Boolean = day == 1

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        if (!canUseDay(village.latestDay())) return daychange
        village.participants.filterAlive().filterBySkill(CDef.Skill.求愛者.toModel()).list.forEach {
            val target = getSelectableTargetList(village, it, abilities).shuffled().first()
            val ability = Ability(
                day = village.latestDay(),
                type = abilityType,
                charaId = it.charaId,
                targetCharaId = target.charaId
            )
            abilities = abilities.add(ability)
        }
        return daychange.copy(abilities = abilities)
    }

    fun court(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.求愛者.toModel()).list.forEach {
            val ability = daychange.abilities
                .filterByDay(village.latestDay() - 1)
                .filterByType(abilityType)
                .filterByCharaId(it.charaId)
                .list.firstOrNull() ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 相互恋絆を結ぶ
            village = village.courtParticipant(it.id, target.id)
            messages = messages.add(createCourtMessage(village, it, target))
            messages = messages.add(createCourtedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createCourtMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}に求愛した。",
            messageType = CDef.MessageType.恋人メッセージ.toModel()
        )
    }

    private fun createCourtedMessage(village: Village, court: VillageParticipant, myself: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${court.name()}に求愛された。",
            messageType = CDef.MessageType.恋人メッセージ.toModel()
        )
    }
}