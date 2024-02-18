package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
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
class CheatLoveDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService,
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.浮気.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 一度選んだ人は選べない
        val pastTargetCharaIds = abilities
            .filterPastDay(village.latestDay())
            .filterByCharaId(myself.charaId)
            .filterByType(abilityType).list.map { it.targetCharaId }
        return village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber()
            .list.filterNot { pastTargetCharaIds.contains(it.charaId) }
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean {
        // 対象がいなくなったら可能になる
        return getSelectableTargetList(village, myself, abilities, Votes(emptyList())).isEmpty()
    }

    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String = "浮気相手"
    override fun getTargetSuffix(): String = "に乗り換える"

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        if (!canUseDay(village.latestDay())) return daychange
        village.participants.filterAlive().filterBySkill(CDef.Skill.浮気者.toModel()).list.forEach {
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
        return daychange.copy(abilities = abilities, footsteps = footsteps)
    }

    fun cheatLove(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.浮気者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 恋絆を解除して新たに結ぶ
            village = village.cheatLoveParticipant(it.id, target.id)
            messages = messages.add(createCheatLoveMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createCheatLoveMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}に浮気した。",
            messageType = CDef.MessageType.恋人メッセージ.toModel()
        )
    }
}