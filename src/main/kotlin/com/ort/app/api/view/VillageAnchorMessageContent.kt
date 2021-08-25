package com.ort.app.api.view

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village

data class VillageAnchorMessageContent(
    val message: VillageMessageContent?
) {
    constructor(
        message: Message?,
        village: Village,
        player: Player?,
        charas: Charas
    ) : this(
        message = message?.let {
            VillageMessageContent(
                village = village,
                message = it,
                fromParticipant = it.fromParticipantId?.let { village.allParticipants().member(it) },
                player = player,
                charas = charas,
                hasBigEar = false
            )
        }
    )
}