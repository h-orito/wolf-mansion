package com.ort.app.domain.model.chara

data class Charas(val list: List<Chara>) {
    fun chara(charaId: Int): Chara = list.first { it.id == charaId }
}