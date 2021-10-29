package com.ort.app.domain.model.chara

import com.ort.app.domain.model.message.MessageType
import com.ort.dbflute.allcommon.CDef

data class Chara(
    val id: Int,
    val name: String,
    val shortName: String,
    val defaultJoinMessage: String?,
    val defaultFirstdayMessage: String?,
    val size: CharaSize,
    val images: CharaImages
) {
    fun defaultImage(): CharaImage = images.list.first { it.faceType.toCdef() == CDef.FaceType.通常 }

    fun detectDefaultFaceTypeCode(messageType: MessageType): String {
        val expected = messageTypeFaceTypeMap[messageType.toCdef()] ?: CDef.FaceType.通常
        return images.list.find { it.faceType.toCdef() == expected }?.faceType?.code ?: CDef.FaceType.通常.code()
    }

    companion object {
        private val messageTypeFaceTypeMap = mapOf(
            CDef.MessageType.通常発言 to CDef.FaceType.通常,
            CDef.MessageType.人狼の囁き to CDef.FaceType.囁き,
            CDef.MessageType.共鳴発言 to CDef.FaceType.共鳴,
            CDef.MessageType.独り言 to CDef.FaceType.独り言,
            CDef.MessageType.死者の呻き to CDef.FaceType.墓下,
            CDef.MessageType.見学発言 to CDef.FaceType.通常,
            CDef.MessageType.恋人発言 to CDef.FaceType.秘話,
            CDef.MessageType.秘話 to CDef.FaceType.秘話
        )
    }
}