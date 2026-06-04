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
class PsychicDomainService(
    private val messageDomainService: MessageDomainService
) {
    fun psychic(daychange: Daychange): Daychange {
        val village = daychange.village
        val targets = village.participants.filterDeadDay(village.latestDay()).filterPsychicable().list
        if (targets.isEmpty()) return daychange

        var messages = daychange.messages.copy()
        // 霊能
        if (village.participants.filterAlive().filterBySkill(CDef.Skill.霊能者.toModel()).list.isNotEmpty()) {
            messages = messages.add(createPsychicMessage(village, targets))
        }
        // 役職霊視
        if (village.participants.filterAlive().list.any { it.skill!!.hasSkillPsychicAbility() }) {
            messages = messages.add(createSkillPsychicMessage(village, targets))
        }

        return daychange.copy(messages = messages)
    }

    private fun createPsychicMessage(village: Village, targets: List<VillageParticipant>): Message {
        val text = targets.joinToString(
            separator = "\n"
        ) {
            val result = if (it.skill!!.isPsychicResultWolf()) "人狼" else "人間"
            "${it.name()}は${result}のようだ。"
        }
        return messageDomainService.createPublicAbilityMessage(village, text, CDef.MessageType.白黒霊視結果.toModel())
    }

    private fun createSkillPsychicMessage(village: Village, targets: List<VillageParticipant>): Message {
        val text = targets.joinToString(
            separator = "\n"
        ) {
            "${it.name()}は${it.skill!!.name}のようだ。"
        }
        return messageDomainService.createPublicAbilityMessage(village, text, CDef.MessageType.役職霊視結果.toModel())
    }
}
