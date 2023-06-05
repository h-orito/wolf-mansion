package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class GiveWinDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.当選.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 当選者になったことがない人
        return village.participants
            .filterAlive()
            .sortedByRoomNumber()
            .filterNotDummy(village.dummyParticipant()).list
            .filterNot {
                it.skill!!.histories.list.any { h -> h.skill.toCdef() == CDef.Skill.当選者 }
            }.filterNot { Skills.openSkills.contains(it.skill!!.toCdef()) }
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean =
        false

    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String = "当選権利を譲る対象"
    override fun getTargetSuffix(): String = "に当選権利を譲る"

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.当選者.toModel()).list
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

    fun giveWin(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.当選者.toModel()).list.shuffled()
            .forEach { winner ->
                val ability = daychange.abilities.findYesterday(village, winner, abilityType) ?: return@forEach
                val target = village.participants.chara(ability.targetCharaId!!)
                messages = messages.add(createGiveWinMessage(village, winner, target))

                // 既に死亡していたり同棲者の場合は交換しない
                if (!isGiveSuccess(target)) return@forEach

                // 役職交換
                val targetSkill = target.skill!!
                village = village.assignParticipantSkill(winner.id, targetSkill)
                village = village.assignParticipantSkill(target.id, CDef.Skill.当選者.toModel())
                messages = messages.add(createGivenWinMessage(village, target))
            }

        return daychange.copy(messages = messages, village = village)
    }

    private fun isGiveSuccess(target: VillageParticipant): Boolean {
        if (target.isDead()) return false
        val skill = target.skill!!.toCdef()
        if (skill == CDef.Skill.同棲者) return false
        return !Skills.openSkills.contains(skill)
    }

    private fun createGiveWinMessage(
        village: Village,
        winner: VillageParticipant,
        target: VillageParticipant
    ): Message {
        val targetSkill = target.skill!!.toCdef()
        val text = when {
            target.isDead() -> "${target.name()}は死亡しているため、${winner.name()}は当選権利を譲れなかった。"
            targetSkill == CDef.Skill.同棲者 -> "${target.name()}は同棲者のため、${winner.name()}は当選権利を譲れなかった。"
            Skills.openSkills.contains(targetSkill) -> "${target.name()}は${targetSkill.toModel().name}のため、${winner.name()}は当選権利を譲れなかった。"
            else -> "${winner.name()}は、${target.name()}に当選権利を譲った。"
        }
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = winner,
            text = text,
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    private fun createGivenWinMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = target,
            text = "${target.name()}は、当選権利を譲ってもらった。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}