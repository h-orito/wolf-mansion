package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
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
class TrapDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.罠設置)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun getTargetPrefix(): String? = "罠を設置する部屋"
    override fun getTargetSuffix(): String? = "の部屋に罠を設置する"
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