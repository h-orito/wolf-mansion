package com.ort.app.domain.model.message

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

data class Message(
    val fromParticipantId: Int?,
    val fromCharacterName: String?,
    val toParticipantId: Int?,
    val toCharacterName: String?,
    val time: MessageTime,
    val content: MessageContent
) {
    fun shouldPostSlack(): Boolean = content.shouldPostSlack()

    companion object {
        fun ofSystemMessage(
            day: Int,
            message: String,
            messageType: MessageType = MessageType(CDef.MessageType.公開システムメッセージ),
            participantId: Int? = null,
            isConvertDisable: Boolean = true
        ): Message = Message(
            fromParticipantId = participantId,
            fromCharacterName = null,
            toParticipantId = null,
            toCharacterName = null,
            time = MessageTime(
                day = day,
                datetime = LocalDateTime.now()
            ),
            content = MessageContent(
                type = messageType,
                num = null,
                text = message,
                faceTypeCode = null,
                isConvertDisable = isConvertDisable
            )
        )

        fun ofSayMessage(
            day: Int,
            participantId: Int,
            targetParticipantId: Int? = null,
            messageContent: MessageContent
        ): Message = Message(
            fromParticipantId = participantId,
            fromCharacterName = null,
            toParticipantId = targetParticipantId,
            toCharacterName = null,
            time = MessageTime(
                day = day,
                datetime = LocalDateTime.now()
            ),
            content = messageContent
        )

        fun ofVillageInitialMessage(): Message =
            ofSystemMessage(
                day = 0,
                message = """
                    昼間は人間のふりをして、夜に正体を現すという人狼。
                    その人狼が、この館に紛れ込んでいるという噂が広がった。
                    
                    村人達は半信半疑ながらも、館の大広間に集められることになった。
                """.trimIndent()
            )
    }
}