package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class WallPunchDomainService(
    private val roomDomainService: RoomDomainService,
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.壁殴り)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        if (village.latestDay() < 2) return emptyList()
        val candidateRoomNumbers = roomDomainService.detectWasdRoomNumbers(
            room = myself.room!!,
            size = village.roomSize!!
        )
        return village.participants.filterAlive().sortedByRoomNumber()
            .list.filter { candidateRoomNumbers.contains(it.room!!.number) }
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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} の部屋の壁を殴る"
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
        return "${myself.name()}が壁殴り対象を${target.name()}に設定しました。"
    }

    override fun canUseDay(day: Int): Boolean = day > 1

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        if (!canUseDay(village.latestDay())) return daychange
        village.participants.filterAlive().filterBySkill(CDef.Skill.壁殴り代行.toModel()).list.forEach {
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

    fun wallPunch(daychange: Daychange): Daychange {
        val village = daychange.village
        var guarded = daychange.guarded
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.壁殴り代行.toModel()).list.forEach {
            val ability = daychange.abilities
                .filterByDay(village.latestDay() - 1)
                .filterByType(abilityType)
                .filterByCharaId(it.charaId)
                .list.firstOrNull() ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            guarded = (guarded + target).distinct()
            messages = messages.add(createWallPunchMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createWallPunchMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}の部屋の壁を殴っている。",
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }
}