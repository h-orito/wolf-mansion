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
    val isLoud: Boolean
) {
    /** css指定用  */
    fun getMinHeightCss(): String = "min-height: ${height}px;"

    constructor(
        village: Village,
        message: Message,
        fromParticipant: VillageParticipant?,
        player: Player?,
        charas: Charas,
        hasBigEar: Boolean,
        isRainbow: Boolean,
        isLoud: Boolean
    ) : this(
        playerName = if (village.status.isSettleOrCanceled()) player?.name else null,
        characterName = message.fromCharacterName,
        characterId = fromParticipant?.charaId,
        characterImageUrl = if (fromParticipant != null && message.content.faceTypeCode != null) {
            charas.chara(fromParticipant.charaId).images.findByFaceType(message.content.faceTypeCode)?.url
        } else null,
        messageType = message.content.type.code,
        messageNumber = if (message.content.type.toCdef() == CDef.MessageType.独り言 && !village.status.isSettleOrCanceled()) null else message.content.num,
        messageContent = message.content.text,
        messageDatetime = message.time.datetime,
        width = if (fromParticipant != null) charas.chara(fromParticipant.charaId).size.width else null,
        height = if (fromParticipant != null) charas.chara(fromParticipant.charaId).size.height else null,
        targetCharacterName = message.toCharacterName,
        isConvertDisable = message.content.isConvertDisable,
        isBigEars = hasBigEar && MessageType(CDef.MessageType.codeOf(message.content.type.code)).isOwlViewableType(),
        isRainbow = isRainbow,
        isLoud = isLoud
    )
}