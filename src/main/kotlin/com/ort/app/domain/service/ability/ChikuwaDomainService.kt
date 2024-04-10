package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.AbilityType
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.chara.toModel
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class ChikuwaDomainService(
    private val messageDomainService: MessageDomainService,
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.誰だ今の)

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
        else "挟まる"
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
            .sortedByDay().list.map { "${it.day}日目 挟まる" }
    }

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        targetCharaId ?: return "${myself.name()}が挟まるのをやめました。"
        return "${myself.name()}が挟まることにしました。"
    }

    override fun getTargetPrefix(): String? = "挟まる場合自分を選択してください"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true

    fun chikuwa(daychange: Daychange, charas: Charas): Daychange {
        val village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.ちくわ大明神.toModel()).list.forEach {
            daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            messages = messages.add(createChikuwaMessage(village, it, charas))
        }
        return daychange.copy(village = village, messages = messages)
    }

    private fun createChikuwaMessage(
        village: Village,
        it: VillageParticipant,
        charas: Charas
    ): Message {
        return messageDomainService.createSayMessage(
            village = village,
            myself = it,
            target = null,
            messageContent = MessageContent.invoke(
                messageType = CDef.MessageType.通常発言.code(),
                text = "ちくわ大明神",
                faceCode = charas.chara(it.charaId).defaultImage().faceType.code,
                isConvertDisable = false
            ),
        )
    }

    private fun createAttackMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant,
        charas: Charas
    ): Message {
        val text = "${target.name()}！今日がお前の命日だ！"
        val myselfChara = charas.chara(myself.charaId)
        val faceType =
            if (hasFaceType(myselfChara)) CDef.FaceType.囁き.toModel()
            else charas.chara(myself.charaId).defaultImage().faceType
        return messageDomainService.createAttackMessage(
            village,
            myself,
            text,
            faceType,
            CDef.MessageType.独り言.toModel()
        )
    }

    private fun hasFaceType(chara: Chara): Boolean =
        chara.images.list.any { it.faceType.toCdef() == CDef.FaceType.囁き }
}