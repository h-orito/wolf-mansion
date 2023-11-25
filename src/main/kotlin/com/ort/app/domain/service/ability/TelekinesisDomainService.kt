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
class TelekinesisDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = CDef.AbilityType.念力付与.toModel()

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getAliveTargets(village).filter {
        it.skill!!.isFoxCount()
    }

    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun getTargetPrefix(): String? = "念力を付与する対象"
    override fun getTargetSuffix(): String? = "に念力を付与する"

    fun telekinesis(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()
        // 効果は1日なので、全員の念力を除去
        village.participants.list.forEach {
            village = village.clearTelekinesis(it.id)
        }

        village.participants.filterAlive().filterBySkill(CDef.Skill.念狐.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)

            village = village.addTelekinesis(target.id)
            messages = messages.add(createTelekinesisMessage(village, it, target))
            messages = messages.add(createTelekinesisGivenMessage(village, target))
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun createTelekinesisMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = "${myself.name()}は${target.name()}に念力を付与した。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createTelekinesisGivenMessage(
        village: Village,
        target: VillageParticipant
    ): Message {
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = target,
            text = "あなたは、念力を付与された。",
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}
