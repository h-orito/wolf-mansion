package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class CohabitDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.同棲.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        return listOf(myself, myself.getTargetCohabitor(village)!!)
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? {
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .list.firstOrNull { it.charaId == myself.charaId || it.charaId == myself.getTargetCohabitor(village)!!.charaId }
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
            .filterByType(abilityType)
            .sortedByDay().list
            .filter { it.charaId == myself.charaId || it.charaId == myself.getTargetCohabitor(village)!!.charaId }
            .map {
                val abilityDay = it.day
                val target = village.participants.chara(it.targetCharaId!!)
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} の部屋で過ごす"
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
        val partner = target.getTargetCohabitor(village)!!
        return "${partner.name()}が今晩の滞在先を${target.name()}の部屋に設定しました。"
    }

    override fun getTargetSuffix(): String? = "の部屋で過ごす"

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        val cohabitorIds = mutableSetOf<Int>()
        village.participants.filterAlive().filterBySkill(CDef.Skill.同棲者.toModel()).list.forEach {
            val target = it.getTargetCohabitor(village)!!
            if (cohabitorIds.contains(it.id) || cohabitorIds.contains(target.id)) return@forEach
            val ability = Ability(
                day = village.latestDay(),
                type = abilityType,
                charaId = it.charaId,
                targetCharaId = target.charaId
            )
            abilities = abilities.add(ability)
            cohabitorIds.add(it.id)
            cohabitorIds.add(target.id)
        }

        return daychange.copy(
            abilities = abilities
        )
    }

    fun cohabit(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.同棲者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            if (target.isDead()) return@forEach
            messages = messages.add(createCohabitMessage(village, it, target))
        }

        return daychange.copy(messages = messages)
    }

    // 同棲によりtargetの部屋に不在か
    fun isAbsence(daychange: Daychange, target: VillageParticipant): Boolean {
        // 同棲により不在、かつ両方生存
        val ability = daychange.abilities.findYesterday(daychange.village, target, abilityType) ?: return false
        return daychange.village.participants.chara(ability.targetCharaId!!).isAlive()
                && target.isAlive()
    }

    // 同棲によりtargetと同じ部屋にいるか
    fun isCohabiting(daychange: Daychange, target: VillageParticipant): Boolean {
        val ability = daychange.abilities.filterByDay(daychange.village.latestDay() - 1)
            .filterByType(CDef.AbilityType.同棲.toModel())
            .list.find { it.targetCharaId == target.charaId } ?: return false
        return daychange.village.participants.chara(ability.charaId).isAlive()
                && target.isAlive()
    }

    private fun createCohabitMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        val text = "${myself.name()}は、${target.name()}の部屋で過ごすことにした。"
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = text,
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }
}