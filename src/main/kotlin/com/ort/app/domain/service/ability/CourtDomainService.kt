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
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class CourtDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.求愛.toModel()

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
        return getHistoryStrings(
            village = village,
            myself = myself,
            abilities = abilities,
            footsteps = footsteps,
            day = day,
            abilityType = abilityType,
            existsFootstep = isTargetingAndFootstep(),
            suffix = "に求愛する"
        )
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) "${myself.name()}が求愛対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が求愛対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String? = "求愛対象"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

    fun court(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.求愛者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
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