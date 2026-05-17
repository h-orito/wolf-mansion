package com.ort.app.api.view

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef

data class VillageAnchorMessagesContent(
    val messageList: List<VillageMessageContent>
) {
    constructor(
        messages: List<Message>,
        village: Village,
        players: Players,
        charas: Charas,
        abilities: Abilities
    ) : this(
        messageList = messages.map {
            val participant = it.fromParticipantId?.let { village.allParticipants().member(it) }
            val player = it.fromParticipantId?.let {
                players.list.find { p -> p.id == participant?.playerId }
            }
            VillageMessageContent(
                village = village,
                myself = null,
                myselfPlayer = null,
                message = it,
                fromParticipant = it.fromParticipantId?.let { village.allParticipants().member(it) },
                player = player,
                charas = charas,
                hasBigEar = false,
                isRainbow = isRainbow(it, abilities, village),
                isLoud = isLoud(it, abilities, village),
                isLatestDay = false
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