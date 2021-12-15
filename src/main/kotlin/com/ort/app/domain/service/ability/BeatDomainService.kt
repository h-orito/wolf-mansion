package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.chara.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
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
class BeatDomainService(
    private val attackDomainService: AttackDomainService,
    private val messageDomainService: MessageDomainService,
    private val cohabitDomainService: CohabitDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.殴打)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 一度使うと使えない
        return if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) emptyList()
        // 自分に投票してきたことがある人
        else village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber().list
            .filter { p ->
                votes.filterByCharaId(p.charaId).list.any { v ->
                    v.day < village.latestDay() && v.targetCharaId == myself.charaId
                }
            }
    }

    override fun getHistories(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        footsteps: Footsteps,
        day: Int
    ): List<String> {
        return getHistoryStrings(
            village = village,
            myself = myself,
            abilities = abilities,
            footsteps = footsteps,
            day = day,
            abilityType = abilityType,
            existsFootstep = isTargetingAndFootstep(),
            suffix = "を殴打する"
        )
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val targetName = targetCharaId?.let { village.participants.chara(it).name() } ?: "なし"
        return "${myself.name()}が殴打する対象を${targetName}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetPrefix(): String? = "殴打する対象"
    override fun isTargetingAndFootstep(): Boolean = true
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 2

    fun beat(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.バールのようなもの.toModel()).list.shuffled().forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType)
                ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createBeatMessage(village, it, target))
            messages = messages.add(createBeatSayMessage(village, it))
            if (!attackDomainService.isAttackSuccess(daychange, target)) return@forEach
            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                village = village.attackedParticipant(cohabitor.id)
            }
            village = village.attackedParticipant(target.id)
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createBeatMessage(village: Village, bar: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village,
            bar,
            "${bar.name()}は、投票の恨みを晴らすべく、${target.name()}を殴打した。",
            CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createBeatSayMessage(village: Village, bar: VillageParticipant): Message {
        return messageDomainService.createAttackMessage(
            village,
            bar,
            "ついカッとなってやった。今では反省している。",
            CDef.FaceType.通常.toModel(),
            CDef.MessageType.独り言.toModel()
        )
    }
}