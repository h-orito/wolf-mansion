package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.service.MessageDomainService
import org.springframework.stereotype.Service

@Service
class SuddenlyDeathDomainService(
    private val messageDomainService: MessageDomainService
) {

    fun deadIfNeeded(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        // 1日目は投票がない
        if (village.latestDay() < 3) return daychange
        // 突然死あり設定でなければ何もしない
        if (!village.setting.rule.isAvailableSuddenlyDeath) return daychange

        var players = daychange.players.copy()
        var messages = daychange.messages.copy()

        // 前日に投票していない人が対象
        daychange.village.participants
            .filterNotDummy(village.dummyParticipant())
            .filterAlive().list.filter { member ->
                daychange.votes.filterByDay(village.latestDay() - 1).list.none { it.charaId == member.charaId }
            }.forEach {
                // 突然死
                village = village.suddenlyDeathParticipant(it.id)
                // 入村制限
                players = players.restrictParticipation(it.playerId)
                // 突然死メッセージ
                messages =
                    messages.add(
                        messageDomainService.createSuddenlyDeathMessage(village, it)
                    )
            }

        return daychange.copy(
            village = village,
            messages = messages,
            players = players
        )
    }
}