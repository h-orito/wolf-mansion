package com.ort.app.api.view

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef

data class VillageAnchorMessageContent(
    val message: VillageMessageContent?
) {
    constructor(
        message: Message?,
        village: Village,
        player: Player?,
        charas: Charas,
        abilities: Abilities
    ) : this(
        message = message?.let {
            VillageMessageContent(
                village = village,
                message = it,
                fromParticipant = it.fromParticipantId?.let { village.allParticipants().member(it) },
                player = player,
                charas = charas,
                hasBigEar = false,
                isRainbow = isRainbow(message, abilities, village),
                isLoud = isLoud(message, abilities, village)
            )
        }
    )

    companion object {
        private fun isRainbow(message: Message, abilities: Abilities, village: Village): Boolean {
            if (message.fromParticipantId == null) return false
            // 対象は発言系メッセージのみ
            if (!message.content.type.isSayType()) return false
            // 虹塗りされている
            val charaId = village.allParticipants().member(message.fromParticipantId).charaId
            return abilities.filterByDay(message.time.day - 1)
                .filterByType(CDef.AbilityType.虹塗り.toModel()).list.any { it.targetCharaId == charaId }
        }

        private fun isLoud(message: Message, abilities: Abilities, village: Village): Boolean {
            if (message.fromParticipantId == null) return false
            // 対象は発言系メッセージのみ
            if (!message.content.type.isSayType()) return false
            // 大声にされている
            val charaId = village.allParticipants().member(message.fromParticipantId).charaId
            return abilities.filterByDay(message.time.day - 1)
                .filterByType(CDef.AbilityType.拡声.toModel()).list.any { it.targetCharaId == charaId }
        }
    }
}