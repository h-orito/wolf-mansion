package com.ort.app.domain.model.chara

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
}