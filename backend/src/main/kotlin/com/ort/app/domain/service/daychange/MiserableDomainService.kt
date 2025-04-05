package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.service.MessageDomainService
import org.springframework.stereotype.Service

@Service
class MiserableDomainService(
    private val messageDomainService: MessageDomainService
) {

    fun addMiserableMessages(daychange: Daychange): Daychange {
        var messages = daychange.messages.copy()
        val victims = daychange.village.participants
            .filterDeadDay(daychange.village.latestDay())
            .filterMiserable()
            .sortedByRoomNumber().list
        val text = if (victims.isEmpty()) {
            "今日は犠牲者がいないようだ。人狼は襲撃に失敗したのだろうか。"
        } else {
            victims.joinToString(
                separator = "と",
                prefix = "次の日の朝、",
                postfix = "が無惨な姿で発見された。"
            ) { it.name() }
        }
        messages = messages.add(messageDomainService.createMiserableMessage(daychange.village, text))
        return daychange.copy(messages = messages)
    }
}
