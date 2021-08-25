package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.ability.BombDomainService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EpilogueDomainService(
    private val bombDomainService: BombDomainService
) {

    fun transitionToEpilogueIfNeeded(daychange: Daychange): Daychange {
        return if (daychange.village.isSettled()) epilogue(daychange)
        else daychange
    }

    private fun epilogue(orgDaychange: Daychange): Daychange {
        // エピローグ遷移
        var daychange = orgDaychange.copy(village = orgDaychange.village.toEpilogue())
        // 爆弾魔が爆弾設置していなかったら死亡
        daychange = bombDomainService.deadByUnexplodedIfNeeded(daychange)
        // 勝敗
        daychange = judgeParticipantsWin(daychange)
        // 遷移メッセージ
        daychange = addTransitionEpilogueMessage(daychange)
        // 参加者一覧メッセージ
        daychange = addPlayersMessage(daychange)
        // ツイート
        daychange = addTweetIfNeeded(daychange)

        return daychange
    }

    private fun judgeParticipantsWin(daychange: Daychange): Daychange =
        daychange.copy(village = daychange.village.judgeParticipantsWin())

    private fun addTransitionEpilogueMessage(daychange: Daychange): Daychange =
        daychange.copy(messages = daychange.messages.add(createEpilogueMessage(daychange)))

    private fun createEpilogueMessage(daychange: Daychange): Message {
        return Message.ofSystemMessage(
            day = daychange.village.latestDay(),
            message = daychange.village.getEpilogueTransitionMessage()
        )
    }

    private fun addPlayersMessage(daychange: Daychange): Daychange {
        val text = daychange.village.allParticipants()
            .sortedByRoomNumber().list
            .joinToString(separator = "\n") {
                val player = daychange.players.player(it.playerId)
                if (it.isSpectator) {
                    "${it.name()} (${player.name})、見学参加だった。"
                } else {
                    val dead = if (it.isDead()) "死亡" else "生存"
                    val win = if (it.isWin!!) "勝利" else "敗北"
                    "${it.name()} (${player.name})、$dead、$win。${it.skill!!.name}だった。"
                }
            }
        val message = Message.ofSystemMessage(
            day = daychange.village.latestDay(),
            message = text
        )
        return daychange.copy(messages = daychange.messages.add(message))
    }

    private fun addTweetIfNeeded(daychange: Daychange): Daychange {
        return if (!daychange.village.setting.joinPassword.isNullOrEmpty()) daychange
        else daychange.copy(tweets = daychange.tweets + "${daychange.village.name}がエピローグを迎えました。")
    }

    fun changeDayIfNeeded(daychange: Daychange): Daychange {
        if (!shouldChangeDay(daychange.village)) return daychange
        return changeDay(daychange.copy(village = daychange.village.addNewDay()))
    }

    private fun changeDay(daychange: Daychange): Daychange {
        return daychange.copy(village = daychange.village.toFinished())
    }

    private fun shouldChangeDay(village: Village): Boolean =
        !LocalDateTime.now().isBefore(village.days.latestDay().dayChangeDatetime)
}