package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
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
class CheatDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    private val abilityType = CDef.AbilityType.誑かす.toModel()

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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を仲間に引き入れる（$footstep）"
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
        return if (targetCharaId == null) {
            "${myself.name()}が誑かす対象をなしに設定しました。"
        } else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が誑かす対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String? = "仲間に引き入れる対象"

    fun cheat(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.誑狐.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 狐憑きにする
            village = village.foxPossessionParticipant(it.id, target.id)
            messages = messages.add(createFoxPossessionMessage(village, it, target))
            messages = messages.add(createFoxPossessionedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createFoxPossessionMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${target.name()}を誑かし、仲間に引き入れた。",
            messageType = CDef.MessageType.妖狐メッセージ.toModel()
        )
    }

    private fun createFoxPossessionedMessage(
        village: Village,
        fox: VillageParticipant,
        myself: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "あなたは${fox.name()}に誑かされ、妖狐に与するものとなりました。",
            messageType = CDef.MessageType.妖狐メッセージ.toModel()
        )
    }
}