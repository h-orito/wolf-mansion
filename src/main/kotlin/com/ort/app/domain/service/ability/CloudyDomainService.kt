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
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class CloudyDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.曇天)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        val day = village.latestDay()
        // 前日以前に2回以上能力行使していたらもう使えない
        val count = abilities.filterPastDay(day).filterByType(abilityType)
            .filterByCharaId(myself.charaId).list.size
        return if (count >= 2) emptyList() else listOf(myself)
    }

    override fun getSelectingTargetMessage(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): String? {
        return if (getSelectingTarget(village, myself, abilities) == null) "何もしない"
        else "雲を発生させる"
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
            .sortedByDay().list.map { "${it.day}日目 曇天" }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}が雲を発生させることにしました。"
        return "${myself.name()}が雲を発生させるのをやめました。"
    }

    override fun getTargetPrefix(): String = "発動させる場合自分を選択してください"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true

    fun cloud(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.曇天者.toModel()).list.forEach {
            daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            messages = messages.add(createCloudMessage(village, it))
        }

        return daychange.copy(messages = messages)
    }

    private fun createCloudMessage(
        village: Village,
        myself: VillageParticipant,
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は、空いっぱいに雲を発生させた。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}