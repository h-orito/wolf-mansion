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
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class OmniscienceDomainService(
    private val messageDomainService: MessageDomainService,
    private val roomDomainService: RoomDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.全知)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> {
        return if (hasAlreadyUseAbility(village, myself, abilities, abilityType)) emptyList()
        else listOf(myself)
    }

    override fun getSelectingTargetMessage(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities
    ): String? {
        return if (getSelectingTarget(village, myself, abilities) == null) "何もしない"
        else "全知の能力を行使する"
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
            .sortedByDay().list.map { "${it.day}日目 全知" }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}が全知の能力使用を取り消しました。"
        return "${myself.name()}が全知の能力を行使することにしました。"
    }

    override fun getTargetPrefix(): String? = "発動させる場合自分を選択してください"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun omniscience(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages
        village.participants.filterAlive().filterBySkill(CDef.Skill.全知者.toModel()).list.forEach {
            daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            messages = messages.add(createMessageText(village, it))
        }
        return daychange.copy(
            messages = messages
        )
    }

    private fun createMessageText(
        village: Village,
        myself: VillageParticipant
    ): Message {
        val text = village.participants.filterAlive().list
            .groupBy { it.skill!! }.entries
            .sortedBy { it.key.toCdef().order().toInt() }
            .joinToString(
                separator = "、",
                prefix = "${myself.name()}は、この村の全容を明らかにした。\nこの村には、",
                postfix = "生存しているようだ。"
            ) {
                "${it.key.name}が${it.value.size}名"
            }
        return messageDomainService.createPrivateAbilityMessage(
            village,
            myself,
            text,
            CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}