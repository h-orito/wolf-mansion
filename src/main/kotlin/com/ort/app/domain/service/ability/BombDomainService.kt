package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
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

    private val abilityType = AbilityType(CDef.AbilityType.爆弾設置)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): List<VillageParticipant> {
        val day = village.latestDay()
        if (day < 2) return emptyList()
        // 前日以前に能力行使していたらもう使えない
        if (abilities.filterPastDay(day).filterByType(abilityType).filterByCharaId(myself.charaId).list.isNotEmpty()) {
            return emptyList()
        }
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
                val target = village.participants.chara(it.targetCharaId!!)
                "${abilityDay}日目 ${target.nameWhen(abilityDay)} の部屋に爆弾を設置する"
            }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}が爆弾を解除しました。"
        val target = village.participants.chara(targetCharaId)
        return "${myself.name()}が${target.name()}の部屋に爆弾を設置しました。"
    }

    override fun getTargetPrefix(): String? = "爆弾を設置する部屋"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun addBombMessages(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages
        village.participants.filterAlive().filterBySkill(CDef.Skill.爆弾魔.toModel()).list.forEach {
            val ability = daychange.abilities
                .filterByDay(village.latestDay())
                .filterByType(abilityType)
                .filterByCharaId(it.charaId)
                .list.firstOrNull() ?: return@forEach
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
            messageType = CDef.MessageType.非公開システムメッセージ.toModel()
        )
    }

    fun bomb(daychange: Daychange): Daychange {
        var village = daychange.village
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
            // 通過した人は死亡
            passedParticipants.forEach { village = village.trapKillParticipant(it.id) }
            // 通過した人がいれば設置された部屋の人も死亡、いなければ爆弾魔が死亡
            if (passedParticipants.isNotEmpty()) {
                // 同棲で不在でなければ設置された部屋の人も死亡
                if (!cohabitDomainService.isAbsence(daychange, target)) {
                    village = village.bombKillParticipant(target.id)
                }
                // 同棲で部屋に同棲者が来ていたら同棲者も死亡
                if (cohabitDomainService.isCohabiting(daychange, target)) {
                    village = village.bombKillParticipant(target.getTargetCohabitor(village)!!.id)
                }
            } else {
                // 誰も通過しなかったので爆弾魔が死亡
                village = village.bombKillParticipant(bomber.id)
            }
        }

        return daychange.copy(village = village)
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