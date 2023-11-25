package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class DeadDivineDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService,
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.死者占い)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        return village.participants
            .filterDead()
            .sortedByRoomNumber().list
    }

    override fun getTargetPrefix(): String = "占い対象"
    override fun getTargetSuffix(): String = "を占う"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day >= 2

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().list
            .filter { it.skill!!.hasDeadDivineAbility() }
            .forEach {
                val target = getSelectableTargetList(village, it, abilities, daychange.votes).shuffled().first()
                val ability = Ability(
                    day = village.latestDay(),
                    type = abilityType,
                    charaId = it.charaId,
                    targetCharaId = target.charaId
                )
                abilities = abilities.add(ability)
                val footstep = Footstep(
                    day = village.latestDay(),
                    charaId = it.charaId,
                    roomNumbers = footstepDomainService.getCandidateList(village, it.charaId, target.charaId).shuffled()
                        .first()
                )
                footsteps = footsteps.add(footstep)
            }

        return daychange.copy(
            abilities = abilities,
            footsteps = footsteps
        )
    }

    fun divine(daychange: Daychange): Daychange {
        val village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().list.shuffled()
            .filter { it.skill!!.hasDeadDivineAbility() }.shuffled()
            .forEach {
                val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
                val target = village.participants.chara(ability.targetCharaId!!)
                messages = messages.add(createDivineMessage(village, it, target))
            }

        return daychange.copy(messages = messages)
    }

    private fun createDivineMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant,
    ): Message {
        val text: String = when (myself.skill!!.toCdef()) {
            CDef.Skill.覚者 -> createRememberSeerMessageText(myself, target)
            else -> throw IllegalStateException("unknown skill. ${myself.skill.name}")
        }
        val type = CDef.MessageType.白黒占い結果.toModel()
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = text,
            messageType = CDef.MessageType.白黒占い結果.toModel()
        )
    }

    private fun createRememberSeerMessageText(myself: VillageParticipant, target: VillageParticipant): String =
        "${myself.name()}は、${target.name()}を占った。\n${target.name()}は${target.skill!!.name}のようだ。"
}