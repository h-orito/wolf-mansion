package com.ort.app.domain.model.chara

import com.fasterxml.jackson.annotation.JsonProperty

data class Charachip(
    val id: Int,
    val name: String,
    val designer: Designer,
    val descriptionUrl: String,
    @JsonProperty("isAvailableChangeName") val isAvailableChangeName: Boolean,
    val charas: Charas
) {
    fun dummyChara(): Chara = charas.list.firstOrNull { !it.defaultJoinMessage.isNullOrBlank() }
        ?: charas.list.first()
}