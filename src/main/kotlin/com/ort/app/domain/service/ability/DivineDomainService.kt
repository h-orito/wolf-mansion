package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.Ability
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class DivineDomainService(
    private val footstepDomainService: FootstepDomainService,
    private val messageDomainService: MessageDomainService,
    private val roomDomainService: RoomDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.占い)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getAliveTargetsWithoutMyself(village, myself)

    override fun getTargetPrefix(): String = "占い対象"
    override fun getTargetSuffix(): String = "を占う"
    override fun isTargetingAndFootstep(): Boolean = true

    fun addDefaultAbilities(daychange: Daychange): Daychange {
        val village = daychange.village
        var abilities = daychange.abilities.copy()
        var footsteps = daychange.footsteps.copy()
        village.participants.filterAlive().list
            .filter { it.skill!!.hasDivineAbility() }
            .forEach {
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

    fun divine(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        val existsCloud = daychange.abilities.filterByDay(village.latestDay() - 1)
            .filterByType(AbilityType(CDef.AbilityType.曇天)).list.isNotEmpty()

        village.participants.filterAlive().list.shuffled().filter { it.skill!!.hasDivineAbility() }.shuffled().forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            // 曇天の場合占星術は失敗
            if (existsCloud && it.skill!!.toCdef() == CDef.Skill.占星術師) {
                messages = messages.add(createDivineMessage(village, it, target, existsCloud))
                return@forEach
            }
            messages = messages.add(createDivineMessage(village, it, target, existsCloud))
            village = divineKillIfNeeded(village, it, target)
            village = counterDivineKillIfNeeded(village, it, target)
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createDivineMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant,
        existsCloud: Boolean
    ): Message {
        val text: String = when (myself.skill!!.toCdef()) {
            CDef.Skill.占い師 -> createSeerDivineMessageText(myself, target)
            CDef.Skill.賢者 -> createWiseDivineMessageText(myself, target)
            CDef.Skill.管狐 -> createWiseDivineMessageText(myself, target)
            CDef.Skill.占星術師 -> {
                if (existsCloud) createAstrologerFailedMessageText(myself, target)
                else createAstrologerDivineMessageText(village, myself, target)
            }

            CDef.Skill.花占い師 -> createFlowerDivineMessageText(myself, target)
            CDef.Skill.感覚者 -> createSixthsensorDivineMessageText(village, myself, target)
            CDef.Skill.興信者 -> createDetectSerDivineMessageText(myself, target)
            else -> throw IllegalStateException("unknown skill. ${myself.skill.name}")
        }
        val type = when (myself.skill.toCdef()) {
            CDef.Skill.賢者 -> CDef.MessageType.役職占い結果.toModel()
            else -> CDef.MessageType.白黒占い結果.toModel()
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
            .groupBy { it.skill!! }.entries
            .sortedBy { it.key.toCdef().order().toInt() }
            .joinToString(
                separator = "、",
                prefix = "${myself.name()}は、${target.name()}のあたりを占った。\nこのあたりには、",
                postfix = "いるようだ。"
            ) {
                "${it.key.name}が${it.value.size}名"
            }
    }

    private fun createAstrologerFailedMessageText(
        myself: VillageParticipant,
        target: VillageParticipant
    ): String {
        return "${myself.name()}は、${target.name()}のあたりを占おうとしたが、本日は曇天のため占星術を行えなかった。"
    }

    private fun createFlowerDivineMessageText(myself: VillageParticipant, target: VillageParticipant): String {
        val result = if (target.status.hasLover()) "いる" else "いない"
        return "${myself.name()}は、${target.name()}を占った。\n${target.name()}は恋をして${result}ようだ。"
    }

    private fun createSixthsensorDivineMessageText(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): String {
        val targetRoomNumberList = roomDomainService.detectAroundRoomNumbers(target.room!!, village.roomSize!!)
        return village.participants.list
            .filter { targetRoomNumberList.contains(it.room!!.number) }
            .groupBy { it.camp!! }.entries
            .sortedBy {
                listOf(
                    CDef.Camp.村人陣営,
                    CDef.Camp.人狼陣営,
                    CDef.Camp.狐陣営,
                    CDef.Camp.恋人陣営,
                    CDef.Camp.愉快犯陣営
                ).indexOf(it.key.toCdef())
            }
            .joinToString(
                separator = "、",
                prefix = "${myself.name()}は、${target.name()}のあたりを占った。\nこのあたりには、",
                postfix = "いるようだ。"
            ) {
                "${it.key.name}に与する者が${it.value.size}名"
            }
    }

    private fun createDetectSerDivineMessageText(
        myself: VillageParticipant,
        target: VillageParticipant
    ): String {
        val result = if (target.skill!!.toCdef().campCode() == target.camp!!.code) "いない" else "いる"
        return "${myself.name()}は、${target.name()}を占った。\n${target.name()}は陣営変化して${result}ようだ。"
    }

    private fun divineKillIfNeeded(village: Village, myself: VillageParticipant, target: VillageParticipant): Village {
        return if (
            target.isAlive()
            && myself.skill!!.toCdef() != CDef.Skill.管狐
            && target.isDeadByDivine()
        ) {
            village.divineKillParticipant(target.id)
        } else village
    }

    private fun counterDivineKillIfNeeded(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Village {
        return if (
            target.isAlive()
            && target.isCounterKillByDivine()
        ) {
            village
                .divineKillParticipant(myself.id)
                .clearCounterCurseMark(target.id)
        } else village
    }
}