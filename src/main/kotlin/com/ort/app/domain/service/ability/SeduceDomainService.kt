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
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SeduceDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.誘惑)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を誘惑する（$footstep）"
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
            "${myself.name()}が誘惑する対象をなしに設定しました。"
        } else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が誘惑する対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String? = "誘惑する対象"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

    fun seduce(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.絡新婦.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 恋絆を結ぶ
            village = village.seduceParticipant(it.id, target.id)
            messages = messages.add(createSeduceMessage(village, it, target))
            messages = messages.add(createSeducedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    fun createSeduceMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}を誘惑した。",
            messageType = CDef.MessageType.恋人メッセージ.toModel()
        )
    }

    fun createSeducedMessage(village: Village, jorogumo: VillageParticipant, myself: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${jorogumo.name()}に誘惑され、恋をしてしまった。",
            messageType = CDef.MessageType.恋人メッセージ.toModel()
        )
    }
}