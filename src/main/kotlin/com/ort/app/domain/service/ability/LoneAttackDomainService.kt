package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.toModel
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
class LoneAttackDomainService(
    private val footstepDomainService: FootstepDomainService,
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
            .sortedByDay().list.map {
                val abilityDay = it.day
                val footstep = footsteps
                    .filterByDay(abilityDay)
                    .filterByCharaId(it.charaId).list
                    .firstOrNull()
                    ?.roomNumbers ?: "なし"
                val target = village.participants.chara(it.targetCharaId!!)
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を襲撃する（${footstep}）"
            }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val targetName = targetCharaId?.let { village.participants.chara(it).name() } ?: "なし"
        return "${myself.name()}が単独襲撃対象を${targetName}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetPrefix(): String? = "襲撃対象"
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
        val faceType = if (hasFaceType(myself, charas)) CDef.FaceType.囁き else CDef.FaceType.通常
        return messageDomainService.createAttackMessage(
            village,
            myself,
            text,
            faceType.toModel(),
            CDef.MessageType.独り言.toModel()
        )
    }

    private fun hasFaceType(myself: VillageParticipant, charas: Charas): Boolean =
        charas.chara(myself.charaId).images.list.any { it.faceType.toCdef() == CDef.FaceType.囁き }
}