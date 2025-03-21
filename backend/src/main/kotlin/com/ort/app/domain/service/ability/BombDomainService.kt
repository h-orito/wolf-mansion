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
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class BombDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService,
    private val cohabitDomainService: CohabitDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.爆弾設置)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun getTargetPrefix(): String? = "爆弾を設置する部屋"
    override fun getTargetSuffix(): String? = "の部屋に爆弾を設置する"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1
    override fun isTargetingAndFootstep(): Boolean = true

    fun addBombMessages(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.爆弾魔.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = daychange.village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createBombMessage(village, it, target))
        }

        return daychange.copy(messages = messages)
    }

    private fun createBombMessage(village: Village, myself: VillageParticipant, target: VillageParticipant): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、${target.name()}の部屋に爆弾を設置した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    fun bomb(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterBySkill(CDef.Skill.爆弾魔.toModel()).list.filterNot {
            // 突然死でない限りは発動
            it.dead.isSuddenlyDead()
        }.forEach { bomber ->
            val ability = daychange.abilities.findYesterday(village, bomber, abilityType)
                ?: return@forEach
            // 設置された部屋の人
            val target = village.participants.chara(ability.targetCharaId!!)
            // 設置された部屋を通過した人
            val passedParticipants = footstepDomainService.findPassedParticipants(
                village = village,
                footsteps = daychange.footsteps,
                day = village.latestDay() - 1,
                roomNumber = target.room!!.number
            )
            // 死亡した人
            val deadParticipants = mutableListOf<VillageParticipant>()

            // 通過した人は死亡
            passedParticipants.forEach {
                village = village.bombKillParticipant(it.id)
                deadParticipants.add(it)
            }
            // 通過した人がいれば設置された部屋の人も死亡、いなければ爆弾魔が死亡
            if (passedParticipants.isNotEmpty()) {
                // 同棲で不在でなければ設置された部屋の人も死亡
                if (!cohabitDomainService.isAbsence(daychange, target)) {
                    village = village.bombKillParticipant(target.id)
                    deadParticipants.add(target)
                }
                // 同棲で部屋に同棲者が来ていたら同棲者も死亡
                if (cohabitDomainService.isCohabiting(daychange, target)) {
                    village = village.bombKillParticipant(target.getTargetCohabitor(village)!!.id)
                    deadParticipants.add(target)
                }
                messages = messages.add(createSuccessMessage(village, bomber, deadParticipants))
            } else {
                // 誰も通過しなかったので爆弾魔が死亡
                village = village.bombKillParticipant(bomber.id)
                messages = messages.add(createFailureMessage(village, bomber))
            }
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createSuccessMessage(
        village: Village,
        bomber: VillageParticipant,
        deadParticipants: MutableList<VillageParticipant>
    ): Message {
        val message = deadParticipants.joinToString(
            prefix = "${bomber.name()}が設置した爆弾が起爆し、",
            separator = "と",
            postfix = "が爆死した。"
        ) { it.name() }
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = message,
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    private fun createFailureMessage(village: Village, bomber: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${bomber.name()}は爆弾が不発だったため、自分の部屋を爆破した。",
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    fun deadByUnexplodedIfNeeded(daychange: Daychange): Daychange {
        var village = daychange.village
        var messages = daychange.messages

        village.participants
            .filterAlive()
            .filterBySkill(CDef.Skill.爆弾魔.toModel()).list
            .filter {
                daychange.abilities.filterByType(abilityType).filterByCharaId(it.charaId).list.isEmpty()
            }.forEach {
                messages = messages.add(createUnexplodedMessage(village, it))
                village = village.bombKillParticipant(it.id)
            }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createUnexplodedMessage(village: Village, bomber: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${bomber.name()}は、物足りないので自分の部屋を爆破した。"
        )
    }
}