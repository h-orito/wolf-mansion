package com.ort.app.api.response.message

import com.ort.app.domain.model.message.Message
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "発言")
data class MessageView(
    @field:Schema(description = "発言種別コード")
    val typeCode: String,
    @field:Schema(description = "発言種別表示名")
    val typeName: String,
    @field:Schema(description = "発言番号 (種別内通し番号、システム発言などで null になる)")
    val number: Int?,
    @field:Schema(description = "発言者参加者 ID (システム発言で null になる)")
    val fromParticipantId: Int?,
    @field:Schema(description = "発言者キャラ名 (システム発言で null になる)")
    val fromCharaName: String?,
    @field:Schema(description = "宛先参加者 ID (秘話などで利用、無ければ null)")
    val toParticipantId: Int?,
    @field:Schema(description = "宛先キャラ名")
    val toCharaName: String?,
    @field:Schema(description = "発言日 (何日目か)")
    val day: Int,
    @field:Schema(description = "発言日時")
    val datetime: LocalDateTime,
    @field:Schema(description = "本文")
    val text: String,
    @field:Schema(description = "表情コード")
    val faceTypeCode: String?,
    @field:Schema(description = "変換抑止フラグ")
    val isConvertDisable: Boolean,
) {
    constructor(message: Message) : this(
        typeCode = message.content.type.code,
        typeName = message.content.type.name,
        number = message.content.num,
        fromParticipantId = message.fromParticipantId,
        fromCharaName = message.fromCharacterName,
        toParticipantId = message.toParticipantId,
        toCharaName = message.toCharacterName,
        day = message.time.day,
        datetime = message.time.datetime,
        text = message.content.text,
        faceTypeCode = message.content.faceTypeCode,
        isConvertDisable = message.content.isConvertDisable,
    )
}
