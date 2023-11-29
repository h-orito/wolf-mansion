package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.toModel
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
class LoneAttackDomainService(
    private val attackDomainService: AttackDomainService,
    private val messageDomainService: MessageDomainService,
    private val cohabitDomainService: CohabitDomainService,
    private val footstepDomainService: FootstepDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.単独襲撃)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getAliveTargetsWithoutMyself(village, myself)

    override fun getTargetPrefix(): String? = "単独襲撃対象"
    override fun getTargetSuffix(): String? = "を単独襲撃する"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean {
        // 一匹狼は対象なしが可能、暴狼は不可能
        return myself.skill!!.toCdef() == CDef.Skill.一匹狼
    }

    override fun canUseDay(day: Int): Boolean = day > 1

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()

        // 一匹狼は対象なし、暴狼は対象あり
        village.participants.filterAlive().filterBySkill(CDef.Skill.暴狼.toModel()).list.forEach {
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

    fun loneAttack(daychange: Daychange, charas: Charas): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().list.filter { it.skill!!.hasLoneAttackAbility() }.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType)
                ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            // 襲撃メッセージ
            messages = messages.add(createAttackMessage(village, it, target, charas))

            if (!attackDomainService.isAttackSuccess(daychange, target)) {
                // 襲撃されたのが夜狐の場合、狐憑きを付与する
                if (attackDomainService.shouldFoxPossession(daychange, it, target)) {
                    village = village.foxPossessionParticipant(target.id, it.id)
                    messages = messages.add(attackDomainService.createNightFoxPossessionMessage(village, it, target))
                    messages = messages.add(attackDomainService.createNightFoxPossessionedMessage(village, it, target))
                }
                return@forEach
            }

            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                village = village.attackedParticipant(cohabitor.id)
            }
            village = village.attackedParticipant(target.id)
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createAttackMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant,
        charas: Charas
    ): Message {
        val text = "${target.name()}！今日がお前の命日だ！"
        val myselfChara = charas.chara(myself.charaId)
        val faceType =
            if (hasFaceType(myselfChara)) CDef.FaceType.囁き.toModel()
            else charas.chara(myself.charaId).defaultImage().faceType
        return messageDomainService.createAttackMessage(
            village,
            myself,
            text,
            faceType,
            CDef.MessageType.独り言.toModel()
        )
    }

    private fun hasFaceType(chara: Chara): Boolean =
        chara.images.list.any { it.faceType.toCdef() == CDef.FaceType.囁き }
}