package com.ort.app.domain.service.ability

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.daychange.Daychange
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
class CheatDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.誑かす.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String? = "誑かす対象"
    override fun getTargetSuffix(): String? = "を誑かす"

    fun cheat(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.誑狐.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            // 狐憑きにする
            village = village.foxPossessionParticipant(it.id, target.id)
            messages = messages.add(createFoxPossessionMessage(village, it, target))
            messages = messages.add(createFoxPossessionedMessage(village, it, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createFoxPossessionMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${target.name()}を誑かし、仲間に引き入れた。",
            messageType = CDef.MessageType.妖狐メッセージ.toModel()
        )
    }

    private fun createFoxPossessionedMessage(
        village: Village,
        fox: VillageParticipant,
        myself: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "あなたは${fox.name()}に誑かされ、妖狐に与するものとなりました。",
            messageType = CDef.MessageType.妖狐メッセージ.toModel()
        )
    }
}