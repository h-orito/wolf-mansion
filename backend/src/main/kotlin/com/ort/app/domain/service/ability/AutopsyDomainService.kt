package com.ort.app.domain.service.ability

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class AutopsyDomainService(
    private val messageDomainService: MessageDomainService
) {

    fun autopsy(daychange: Daychange): Daychange {
        val village = daychange.village
        var messages = daychange.messages.copy()
        // 検死官が生存していない場合は何もしない
        if (village.participants.filterAlive().filterBySkill(CDef.Skill.検死官.toModel()).list.isEmpty()) return daychange
        val victims = daychange.village.participants
            .filterDeadDay(daychange.village.latestDay())
            .filterMiserable()
            .sortedByRoomNumber().list
        // 無惨な死体がない場合も何もしない
        if (victims.isEmpty()) return daychange
        val text = victims.joinToString(separator = "\n") {
            "${it.name()}の死因は、${it.dead.reason!!.name}のようだ。"
        }
        messages = messages.add(
            messageDomainService.createPublicAbilityMessage(
                daychange.village,
                text,
                CDef.MessageType.検死結果.toModel()
            )
        )
        return daychange.copy(messages = messages)
    }
}
