package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class BadgerGameDomainService(
    private val seduceDomainService: SeduceDomainService,
    private val attackDomainService: AttackDomainService,
    private val cohabitDomainService: CohabitDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.美人局)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun getTargetPrefix(): String? = "誘惑して脅す対象"
    override fun getTargetSuffix(): String? = "を誘惑して脅す"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true

    fun badgerGame(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.美人局.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 恋絆を結ぶ
            village = village.seduceParticipant(it.id, target.id)
            messages = messages.add(seduceDomainService.createSeduceMessage(village, it, target))
            messages = messages.add(seduceDomainService.createSeducedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    fun badgerGameAttack(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.美人局.toModel()).list.forEach {
            val ability = daychange.abilities
                .filterByDay(village.latestDay() - 2) // 2日前
                .filterByType(abilityType)
                .filterByCharaId(it.charaId)
                .list.firstOrNull() ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            if (!attackDomainService.isAttackSuccess(daychange, target)) return@forEach

            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                village = village.attackedParticipant(cohabitor.id)
            }
            village = village.attackedParticipant(target.id)
        }

        return daychange.copy(village = village)
    }
}