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
class BreakupDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.破局)

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
            suffix = "を破局させる"
        )
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) {
            "${myself.name()}が破局させる対象をなしに設定しました。"
        } else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が破局させる対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String? = "破局させる対象"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

    fun breakup(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.破局者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 破局させる
            village = village.breakupParticipant(target.id)
            messages = messages.add(createBreakupMessage(village, it, target))
            messages = messages.add(createBreakedupMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    fun createBreakupMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}を破局させた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    fun createBreakedupMessage(village: Village, separator: VillageParticipant, myself: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${separator.name()}に破局させられてしまった。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}