package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class ForceReincarnationDomainService(
    private val cohabitDomainService: CohabitDomainService,
    private val roomDomainService: RoomDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.強制転生.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        // 一度でも使用していたら使えない
        if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) return emptyList()
        // 対象は直線上の生存者のみ
        val roomList = roomDomainService.detectHishaRoomNumbers(myself.room!!, village.roomSize!!)
        return village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber().list
            .filter { roomList.contains(it.room!!.number) }
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1
    override fun getTargetPrefix(): String = "強制転生させる対象"
    override fun getTargetSuffix(): String? = "を強制転生させる"

    fun forceReincarnation(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.トラック.toModel()).list.forEach { track ->
            val ability = daychange.abilities.findYesterday(village, track, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 既に死亡している場合は転生しない
            if (target.isDead()) return@forEach
            // 対象が同棲者で、不在の場合は転生しない
            if (cohabitDomainService.isAbsence(daychange, target)) return@forEach
            // ランダム役職で転生
            val skill = village.getRevivableSkills().shuffled().first()
            village = village.forceReincarnation(target.id, skill)
            messages = messages.add(createForceReincarnationMessage(village, target))
            // 対象が同棲者で、相方が来ている場合、相方も転生する
            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                val cohabitorSkill = Skills.revivables().list.shuffled().first()
                village = village.forceReincarnation(cohabitor.id, cohabitorSkill)
                messages = messages.add(createForceReincarnationMessage(village, cohabitor))
            }
        }

        return daychange.copy(messages = messages, village = village)
    }

    private fun createForceReincarnationMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${target.name()}の部屋に異世界転生トラックが突っ込んだ。\n${target.name()}は、転生してしまった。"
        )
    }
}