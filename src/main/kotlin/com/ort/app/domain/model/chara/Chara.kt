package com.ort.app.domain.model.chara

data class Chara(
    val id: Int,
    val name: String,
    val shortName: String,
    val defaultJoinMessage: String?,
    val defaultFirstdayMessage: String?,
    val size: CharaSize,
    val images: CharaImages
)