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
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class HuntingDomainService(
    private val messageDomainService: MessageDomainService,
    private val cohabitDomainService: CohabitDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.狩猟)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun getTargetPrefix(): String? = "狩猟対象"
    override fun getTargetSuffix(): String? = "を狩猟する"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun hunting(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.マタギ.toModel()).list.shuffled().forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType)
                ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 狩猟メッセージ
            messages = messages.add(createAttackMessage(village, it, target))

            if (!isAttackSuccess(daychange, target)) return@forEach

            village = village.attackedParticipant(target.id)

            // 対象が人狼系、妖狐系、一匹狼でない場合、自身も襲撃死
            if (!isBeast(target)) {
                messages = messages.add(createSuicideMessage(village, it))
                village = village.attackedParticipant(it.id)
            }

            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                village = village.attackedParticipant(cohabitor.id)
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createAttackMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は猟銃を構え、${target.name()}に向かって発砲した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createSuicideMessage(village: Village, myself: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${myself.name()}は、獣でない者に発砲したことに責任を感じ、自身に発砲した。",
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    fun isAttackSuccess(daychange: Daychange, target: VillageParticipant): Boolean {
        // 既に死亡している
        if (target.isDead()) return false
        // 護衛されている
        if (daychange.guarded.any { it.id == target.id }) return false
        // 不在
        if (cohabitDomainService.isAbsence(daychange, target)) return false

        return true
    }

    private fun isBeast(target: VillageParticipant): Boolean {
        return target.skill!!.let {
            it.hasAttackAbility() ||
                    it.isFoxCount() ||
                    it.toCdef() == CDef.Skill.一匹狼
        }
    }
}