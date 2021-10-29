package com.ort.app.api.view

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class VillageSayConfirmContent(
    /** 発言 */
    val message: VillageMessageContent,
    /** ランダムキーワード(カンマ区切り) */
    val randomKeywords: String
) {
    constructor(
        village: Village,
        message: Message,
        fromParticipant: VillageParticipant?,
        player: Player?,
        charas: Charas,
        keywords: RandomKeywords
    ) : this(
        message = VillageMessageContent(
            village = village,
            message = message,
            fromParticipant = fromParticipant,
            player = player,
            charas = charas,
            hasBigEar = false
        ),
        randomKeywords = keywords.list.joinToString(",") { it.keyword }
    )
}