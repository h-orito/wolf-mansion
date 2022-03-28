package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class ResentDomainService(
    private val messageDomainService: MessageDomainService
) {
    fun resent(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        village.participants.filterAlive().filterBySkill(CDef.Skill.怨恨者.toModel()).list.forEach { myself ->
            val targets = village.participants
                .sortedByRoomNumber().list
                .filter { p ->
                    daychange.votes.filterByCharaId(p.charaId).filterByDay(village.latestDay() - 1).list.any { v ->
                        v.targetCharaId == myself.charaId
                    }
                }
            if (targets.isEmpty()) return@forEach
            messages = messages.add(createResentMessage(village, myself, targets))
        }
        return daychange.copy(messages = messages)
    }

    private fun createResentMessage(
        village: Village,
        myself: VillageParticipant,
        targets: List<VillageParticipant>
    ): Message {
        val text = targets.groupBy { it.skill!!.name }.entries
            .joinToString(
                separator = "、",
                prefix = "${myself.name()}は、自分に投票した村人の素性を調べた。\n投票してきたのは、",
                postfix = "のようだ。"
            ) {
                "${it.key}が${it.value.size}名"
            }
        return messageDomainService.createPrivateAbilityMessage(
            village = village,
            myself = myself,
            text = text,
            messageType = CDef.MessageType.能力行使メッセージ.toModel()
        )
    }
}
