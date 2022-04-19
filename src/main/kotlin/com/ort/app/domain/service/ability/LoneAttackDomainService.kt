package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.toModel
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
class LoneAttackDomainService(
    private val attackDomainService: AttackDomainService,
    private val messageDomainService: MessageDomainService,
    private val cohabitDomainService: CohabitDomainService
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
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun loneAttack(daychange: Daychange, charas: Charas): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.一匹狼.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType)
                ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            // 襲撃メッセージ
            messages = messages.add(createAttackMessage(village, it, target, charas))

            if (!attackDomainService.isAttackSuccess(daychange, target)) return@forEach

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