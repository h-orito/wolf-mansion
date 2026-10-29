package com.ort.app.domain.service.message.say

import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class ActionSayDomainService : SayTypeDomainService {
    override fun isViewable(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        day: Int,
    ): Boolean = true

    override fun isSayable(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
    ): Boolean = myself?.isAdmin() ?: false || village.isSayableActionSay()

    /**
     * アクション発言の本文「{自分}は、{対象}{本文}」を作る
     * 行為者名はサーバーで付け、対象は「全員」か村にいる人の名前だけを受け付ける
     */
    fun createActionText(
        village: Village,
        myself: VillageParticipant,
        targetText: String?,
        messageText: String,
    ): String {
        val message = messageText.trim()
        val target = targetText?.takeIf { it.isNotEmpty() }
        if (target != null && target != TARGET_EVERYONE && village.allParticipants().list.none { it.name() == target }) {
            throw WolfMansionBusinessException("指定できない対象です")
        }
        val text = "${myself.name()}は、${target ?: ""}$message"
        if (message.isEmpty() || text.length > MessageContent.defaultLengthMax) {
            throw WolfMansionBusinessException("発言内容は1文字以上${MessageContent.defaultLengthMax}文字以内にしてください")
        }
        return text
    }

    companion object {
        private const val TARGET_EVERYONE = "全員"
    }
}
