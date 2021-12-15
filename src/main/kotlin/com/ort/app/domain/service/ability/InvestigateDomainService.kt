package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class InvestigateDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.捜査)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = emptyList()

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? = null

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
            .sortedByDay().list.map { "${it.day}日目 ${it.targetFootstep} を調査する" }
    }

    override fun assertAbility(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?,
        abilities: Abilities,
        footsteps: Footsteps,
        votes: Votes,
        defaultFootstepAsserter: () -> Unit
    ) {
        // 足音
        if (getSelectableFootstepList(village, myself, footsteps).none { it == footstep }) {
            throw WolfMansionBusinessException("選択できない足音を指定しています")
        }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return "${myself.name()}が調査する部屋を${footstep!!}に設定しました。"
    }

    override fun canUseDay(day: Int): Boolean = day > 1

    fun getSelectableFootstepList(
        village: Village,
        myself: VillageParticipant,
        footsteps: Footsteps
    ): List<String> = footstepDomainService.getDisplayFootstepList(village, footsteps, village.latestDay())

    fun getSelectingFootstep(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): String? {
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.firstOrNull()?.targetFootstep
    }

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        if (!canUseDay(village.latestDay())) return daychange
        village.participants.filterAlive().list
            .filter { it.skill!!.getAbility()?.toCdef() == abilityType.toCdef() }
            .forEach {
                val target =
                    getSelectableFootstepList(village, it, daychange.footsteps).shuffled().firstOrNull()
                        ?: return@forEach
                val ability = Ability(
                    day = village.latestDay(),
                    type = abilityType,
                    charaId = it.charaId,
                    targetFootstep = target
                )
                abilities = abilities.add(ability)
            }
        return daychange.copy(abilities = abilities)
    }

    fun investigate(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().list
            .filter { it.skill!!.getAbility()?.toCdef() == abilityType.toCdef() }
            .forEach {
                val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
                val targetFootstep = ability.targetFootstep!!
                messages =
                    messages.add(createInvestigateResultMessage(village, it, targetFootstep, daychange.footsteps))
            }

        return daychange.copy(messages = messages)
    }

    private fun createInvestigateResultMessage(
        village: Village,
        myself: VillageParticipant,
        targetFootstep: String,
        footsteps: Footsteps
    ): Message {
        val participant =
            footstepDomainService.getParticipantByFootstep(village, village.latestDay() - 2, targetFootstep, footsteps)
        val result =
            if (myself.skill?.toCdef() == CDef.Skill.探偵) participant.skill!!.name
            else participant.name()
        val text = "${myself.name()}は、昨日響いた足音${targetFootstep}について調査した。\n${targetFootstep}の足音を響かせたのは${result}のようだ。"
        return messageDomainService.createPrivateAbilityMessage(
            village,
            myself,
            text,
            CDef.MessageType.足音調査結果.toModel()
        )
    }
}