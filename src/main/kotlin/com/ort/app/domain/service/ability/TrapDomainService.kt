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
class TrapDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.罠設置)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        val day = village.latestDay()
        if (day < 2) return emptyList()
        // 前日以前に能力行使していたらもう使えない
        if (abilities.filterPastDay(day).filterByType(abilityType).filterByCharaId(myself.charaId).list.isNotEmpty()) {
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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} の部屋に罠を設置する（$footstep）"
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
        targetCharaId ?: return "${myself.name()}が罠を解除しました。"
        val target = village.participants.chara(targetCharaId)
        return "${myself.name()}が罠を設置する部屋を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetPrefix(): String? = "罠を設置する部屋"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1
    override fun isTargetingAndFootstep(): Boolean = true

    fun addTrapMessages(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.罠師.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = daychange.village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createTrapMessage(village, it, target))
        }

        return daychange.copy(messages = messages)
    }

    private fun createTrapMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}の部屋に罠を設置した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    fun trap(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterBySkill(CDef.Skill.罠師.toModel()).list.filterNot {
            // 突然死でない限りは発動
            it.dead.isSuddenlyDead()
        }.forEach { trapper ->
            val ability = daychange.abilities.findYesterday(village, trapper, abilityType)
                ?: return@forEach
            // 設置された部屋
            val roomNumber = village.participants.chara(ability.targetCharaId!!).room!!.number
            // 設置された部屋を通過した人は死亡
            val passedParticipants = footstepDomainService.findPassedParticipants(
                village = village,
                footsteps = daychange.footsteps,
                day = village.latestDay() - 1,
                roomNumber = roomNumber
            )
            passedParticipants.forEach { participant ->
                village = village.trapKillParticipant(participant.id)
            }
            if (passedParticipants.isNotEmpty()) {
                messages = messages.add(createSuccessMessage(village, trapper, passedParticipants))
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createSuccessMessage(
        village: Village,
        trapper: VillageParticipant,
        passedParticipants: List<VillageParticipant>
    ): Message {
        val message = passedParticipants.joinToString(
            prefix = "${trapper.name()}の設置した罠が作動し、",
            separator = "と",
            postfix = "が罠死した。"
        ) { it.name() }
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = message,
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }
}