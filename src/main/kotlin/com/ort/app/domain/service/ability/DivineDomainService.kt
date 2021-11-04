package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class DivineDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService,
    private val roomDomainService: RoomDomainService
) : AbilityTypeDomainService {

    private val abilityType = AbilityType(CDef.AbilityType.占い)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        return village.participants
            .filterAlive()
            .filterNotParticipant(myself)
            .sortedByRoomNumber().list
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
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} を占う（${footstep}）"
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
        // 対象
        if (getSelectableTargetList(village, myself, abilities).none { it.charaId == targetCharaId }) {
            throw WolfMansionBusinessException("選択できない対象を指定しています")
        }
        // 足音
        footstepDomainService.assertFootstep(village, myself.charaId, targetCharaId, footstep)
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        val target = village.participants.chara(targetCharaId!!)
        return "${myself.name()}が占い対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
    }

    override fun getTargetSuffix(): String? = "を占う"
    override fun isTargetingAndFootstep(): Boolean = true

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().list
            .filter { it.skill!!.hasDivineAbility() }
            .forEach {
                val target = getSelectableTargetList(village, it, abilities).shuffled().first()
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

    fun divine(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.participants.filterAlive().list.filter { it.skill!!.hasDivineAbility() }.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createDivineMessage(village, it, target))
            village = divineKillIfNeeded(village, it, target)
            village = counterDivineKillIfNeeded(village, it, target)
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createDivineMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        val text: String = when (myself.skill!!.toCdef()) {
            CDef.Skill.占い師 -> createSeerDivineMessageText(myself, target)
            CDef.Skill.賢者 -> createWiseDivineMessageText(myself, target)
            CDef.Skill.占星術師 -> createAstrologerDivineMessageText(village, myself, target)
            CDef.Skill.花占い師 -> createFlowerDivineMessageText(myself, target)
            else -> throw IllegalStateException("unknown skill. ${myself.skill.name}")
        }
        val type = when (myself.skill.toCdef()) {
            CDef.Skill.占い師 -> CDef.MessageType.白黒占い結果.toModel()
            CDef.Skill.賢者 -> CDef.MessageType.役職占い結果.toModel()
            CDef.Skill.占星術師 -> CDef.MessageType.白黒占い結果.toModel()
            CDef.Skill.花占い師 -> CDef.MessageType.白黒占い結果.toModel()
            else -> throw IllegalStateException("unknown skill. ${myself.skill.name}")
        }
        return messageDomainService.createPrivateAbilityMessage(village, myself, text, type)
    }

    private fun createSeerDivineMessageText(myself: VillageParticipant, target: VillageParticipant): String {
        val result = if (target.skill!!.isDivineResultWolf()) "人狼" else "人間"
        return "${myself.name()}は、${target.name()}を占った。\n${target.name()}は${result}のようだ。"
    }

    private fun createWiseDivineMessageText(myself: VillageParticipant, target: VillageParticipant): String =
        "${myself.name()}は、${target.name()}を占った。\n${target.name()}は${target.skill!!.name}のようだ。"

    private fun createAstrologerDivineMessageText(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): String {
        val targetRoomNumberList = roomDomainService.detectAroundRoomNumbers(target.room!!, village.roomSize!!)
        return village.participants.list
            .filter { targetRoomNumberList.contains(it.room!!.number) }
            .groupBy { it.skill!!.name }.entries
            .joinToString(
                separator = "、",
                prefix = "${myself.name()}は、${target.name()}のあたりを占った。\nこのあたりには、",
                postfix = "いるようだ。"
            ) {
                "${it.key}が${it.value.size}名"
            }
    }

    private fun createFlowerDivineMessageText(myself: VillageParticipant, target: VillageParticipant): String {
        val result = if (target.status.hasLover()) "いる" else "いない"
        return "${myself.name()}は、${target.name()}を占った。\n${target.name()}は恋をして${result}ようだ。"
    }

    private fun divineKillIfNeeded(village: Village, myself: VillageParticipant, target: VillageParticipant): Village {
        return if (target.isAlive() && target.skill!!.isDeadByDivine()) village.divineKillParticipant(target.id)
        else village
    }

    private fun counterDivineKillIfNeeded(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Village {
        return if (target.isAlive() && target.skill!!.isCounterDeadByDivine()) village.divineKillParticipant(myself.id)
        else village
    }
}