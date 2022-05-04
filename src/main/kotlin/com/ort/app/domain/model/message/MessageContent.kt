package com.ort.app.domain.model.message

import com.ort.dbflute.allcommon.CDef

data class MessageContent(
    val type: MessageType,
    val num: Int?,
    val text: String,
    val faceTypeCode: String?,
    val isConvertDisable: Boolean
) {
    companion object {
        val defaultLineMax: Int = 20
        val defaultLengthMax: Int = 400

        fun invoke(
            messageType: String,
            text: String,
            faceCode: String?,
            isConvertDisable: Boolean?
        ): MessageContent {
            val cdef = checkNotNull(CDef.MessageType.codeOf(messageType))
            return MessageContent(
                type = MessageType(cdef),
                num = null,
                text = text.trim(),
                faceTypeCode = faceCode,
                isConvertDisable = isConvertDisable ?: false
            )
        }
    }

    fun shouldPostSlack(): Boolean = text.contains("@国主") || text.contains("＠国主")
}