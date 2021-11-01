package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class ForceReincarnationDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val cohabitDomainService: CohabitDomainService,
    private val roomDomainService: RoomDomainService
) : AbilityTypeDomainService {

    private val abilityType = CDef.AbilityType.強制転生.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        // 一度でも使用していたら使えない
        if (abilities.filterPastDay(village.latestDay()).filterByCharaId(myself.charaId)
                .filterByType(abilityType).list.isNotEmpty()
        ) {
            return emptyList()
        }
        // 対象は直線上の生存者のみ
        val roomList = roomDomainService.detectHishaRoomNumbers(myself.room!!, village.roomSize!!)
        return village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber().list
            .filter { roomList.contains(it.room!!.number) }
    }

    override fun getSelectingTarget(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): VillageParticipant? {
        return abilities
            .filterByDay(village.latestDay())
            .filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.firstOrNull()
            ?.let { village.participants.chara(it.targetCharaId!!) }
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を強制転生させる（$footstep）"
            }
    }

    override fun assertAbility(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?,
        abilities: Abilities,
        footsteps: Footsteps
    ) {
        if (targetCharaId != null
            && getSelectableTargetList(village, myself, abilities).none { it.charaId == targetCharaId }
        ) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        if (targetCharaId != null) {
            // 足音
            footstepDomainService.assertFootstep(village, myself.charaId, targetCharaId, footstep)
        }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) "${myself.name()}が強制転生させる対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            "${myself.name()}が強制転生させる対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String = "強制転生させる対象"

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
            val skill = Skills.revivables().list.shuffled().first()
            village = village.forceReincarnation(target.id, skill)
            messages = messages.add(createForceReincarnationMessage(village, target))
            // 絶対人狼に転生した場合、メッセージ追加
            if (skill.toCdef() == CDef.Skill.絶対人狼) {
                messages = messages.add(
                    Message.ofSystemMessage(day = village.latestDay(), message = "${target.name()}は絶対人狼のようだ。")
                )
            }

            // 対象が同棲者で、相方が来ている場合、相方も転生する
            if (cohabitDomainService.isCohabiting(daychange, target)) {
                val cohabitor = target.getTargetCohabitor(village)!!
                val cohabitorSkill = Skills.revivables().list.shuffled().first()
                village = village.forceReincarnation(cohabitor.id, cohabitorSkill)
                messages = messages.add(createForceReincarnationMessage(village, cohabitor))
                // 絶対人狼に転生した場合、メッセージ追加
                if (cohabitorSkill.toCdef() == CDef.Skill.絶対人狼) {
                    messages = messages.add(
                        Message.ofSystemMessage(day = village.latestDay(), message = "${cohabitor.name()}は絶対人狼のようだ。")
                    )
                }
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