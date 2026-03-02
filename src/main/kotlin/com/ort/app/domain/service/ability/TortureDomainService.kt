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
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class TortureDomainService(
    private val messageDomainService: MessageDomainService
) : AbilityTypeDomainService {

    override val abilityType = AbilityType(CDef.AbilityType.拷問)

    override fun getSelectableTargetList(
        village: Village,
        myself: VillageParticipant,
        abilities: Abilities,
        votes: Votes
    ): List<VillageParticipant> = getOnlyOneTimeAliveTargets(village, myself, abilities, abilityType)

    override fun createSetMessageText(
        village: Village,
        myself: VillageParticipant,
        charaId: Int?,
        targetCharaId: Int?,
        footstep: String?
    ): String {
        return if (targetCharaId == null) "${myself.name()}が拷問対象をなしに設定しました。"
        else {
            val target = village.participants.chara(targetCharaId)
            return "${myself.name()}が拷問対象を${target.name()}に、通過する部屋を${footstep!!}に設定しました。"
        }
    }

    override fun getTargetPrefix(): String? = "拷問する対象"
    override fun getTargetSuffix(): String? = "を拷問する"
    override fun isAvailableNoTarget(village: Village, myself: VillageParticipant, abilities: Abilities): Boolean = true
    override fun isTargetingAndFootstep(): Boolean = true
    override fun canUseDay(day: Int): Boolean = day > 1

    fun torture(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()

        village.participants.filterAlive().filterBySkill(CDef.Skill.拷問者.toModel()).list.forEach {
            val ability = daychange.abilities.findYesterday(village, it, abilityType) ?: return@forEach
            val target = village.participants.chara(ability.targetCharaId!!)
            messages = messages.add(createTortureMessage(village, it, target))
            messages = messages.add(createTorturedMessage(village, it, target))
        }

        return daychange.copy(messages = messages)
    }

    private fun createTortureMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant
    ): Message {
        // 対象の勝敗判定陣営と同じ陣営の生存者を取得（対象自身と自分を除く）
        val allies = village.participants.filterAlive()
            .filterNotParticipant(myself)
            .filterNotParticipant(target)
            .list
            .filter { it.camp?.code == target.camp?.code }
            .shuffled()
            .take(2)

        val text = if (allies.isEmpty()) {
            "${myself.name()}は、${target.name()}を拷問した。\n${target.name()}の仲間はいないようだ。"
        } else {
            val allyNames = allies.joinToString("と") { it.name() }
            "${myself.name()}は、${target.name()}を拷問した。\n${target.name()}の仲間には、${allyNames}がいるようだ。"
        }
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = text,
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }

    private fun createTorturedMessage(
        village: Village,
        torturer: VillageParticipant,
        target: VillageParticipant
    ): Message {
        val text = "あなたは、${torturer.name()}に拷問されました。"
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = target,
            text = text,
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}
