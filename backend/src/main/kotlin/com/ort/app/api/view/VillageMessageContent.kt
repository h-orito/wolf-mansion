package com.ort.app.api.view

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

data class VillageMessageContent(
    /** プレイヤー名 */
    val playerName: String?,
    /** キャラクター名 */
    val characterName: String?,
    /** キャラクターID */
    val characterId: Int?,
    /** キャラクター画像URL */
    val characterImageUrl: String?,
    /** メッセージ種別 */
    val messageType: String,
    /** メッセージ番号 */
    val messageNumber: Int?,
    /** メッセージ内容 */
    val messageContent: String,
    /** メッセージ日時 */
    val messageDatetime: LocalDateTime,
    /** メッセージ表示横幅px */
    val width: Int?,
    /** メッセージ表示縦幅px */
    val height: Int?,
    /** 秘話相手キャラクター名 */
    val targetCharacterName: String?,
    /** 変換無効か */
    val isConvertDisable: Boolean,
    /** 梟による地獄耳か */
    val isBigEars: Boolean,
    /** 虹色にするか */
    val isRainbow: Boolean,
    /** 大声にするか */
    val isLoud: Boolean,
    /** 返信可能か */
    val canReply: Boolean,
    /** 秘話可能か */
    val canSecret: Boolean,
) {
    /** css指定用  */
    fun getMinHeightCss(): String = "min-height: ${height}px;"

    companion object {
        /**
         * 発言メッセージ 1 件を表示用に組み立てる。
         *
         * 梟の地獄耳で見えている発言 ([isBigEarsMasked]) は、発言者を特定できる情報
         * (キャラ名・ID・画像・表示サイズ・プレイヤー名・発言番号・秘話相手・虹/大声) を
         * レスポンスから除去する。表示上は「地獄耳」固定で発言者を伏せる仕様だが、これらを
         * 残すと生 JSON から囁き主 (＝人狼等) を特定できてしまうため、API 層でマスクする。
         */
        fun of(
            village: Village,
            myself: VillageParticipant?,
            myselfPlayer: Player?,
            message: Message,
            fromParticipant: VillageParticipant?,
            player: Player?,
            charas: Charas,
            hasBigEar: Boolean,
            isRainbow: Boolean,
            isLoud: Boolean,
            isLatestDay: Boolean,
        ): VillageMessageContent {
            val isBigEars = isBigEarsMasked(hasBigEar, message)
            return VillageMessageContent(
                playerName =
                    if (isBigEars) {
                        null
                    } else if (shouldDispPlayerName(village, myself, myselfPlayer)) {
                        player?.name
                    } else {
                        null
                    },
                characterName = if (isBigEars) null else message.fromCharacterName,
                characterId = if (isBigEars) null else fromParticipant?.charaId,
                characterImageUrl =
                    if (!isBigEars && fromParticipant != null && message.content.faceTypeCode != null) {
                        charas
                            .chara(fromParticipant.charaId)
                            .images
                            .findByFaceType(message.content.faceTypeCode)
                            ?.url
                    } else {
                        null
                    },
                messageType = message.content.type.code,
                messageNumber =
                    if (!isBigEars && shouldDispMessageNumber(message, village)) message.content.num else null,
                messageContent = message.content.text,
                messageDatetime = message.time.datetime,
                width = if (!isBigEars && fromParticipant != null) charas.chara(fromParticipant.charaId).size.width else null,
                height = if (!isBigEars && fromParticipant != null) charas.chara(fromParticipant.charaId).size.height else null,
                targetCharacterName = if (isBigEars) null else message.toCharacterName,
                isConvertDisable = message.content.isConvertDisable,
                isBigEars = isBigEars,
                isRainbow = if (isBigEars) false else isRainbow,
                isLoud = if (isBigEars) false else isLoud,
                canReply = !isBigEars && isLatestDay && shouldDispMessageNumber(message, village),
                canSecret = isLatestDay && (village.isSayableSecretSay() || myself?.isAdmin() ?: false),
            )
        }

        /** 梟の地獄耳で見えている (＝発言者を伏せるべき) 発言か。 */
        fun isBigEarsMasked(
            hasBigEar: Boolean,
            message: Message,
        ): Boolean = hasBigEar && MessageType(CDef.MessageType.codeOf(message.content.type.code)).isOwlViewableType()

        fun shouldDispMessageNumber(
            message: Message,
            village: Village,
        ): Boolean =
            if (village.status.isSettleOrCanceled()) {
                true
            } else {
                message.content.type
                    .toCdef()
                    .let { it != CDef.MessageType.独り言 && it != CDef.MessageType.秘話 }
            }

        fun shouldDispPlayerName(
            village: Village,
            myself: VillageParticipant?,
            player: Player?,
        ): Boolean = village.status.isSettleOrCanceled() || myself?.isAdmin() ?: false || village.isProducer(player)
    }
}
