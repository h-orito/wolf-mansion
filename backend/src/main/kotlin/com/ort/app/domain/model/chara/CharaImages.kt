package com.ort.app.domain.model.chara

data class CharaImages(val list: List<CharaImage>) {
    fun findByFaceType(code: String): CharaImage? = list.find { it.faceType.code == code }
}