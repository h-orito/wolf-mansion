package com.ort.app.domain.model.vote

data class Vote(
    val day: Int,
    val charaId: Int,
    val targetCharaId: Int
) {
    fun isSame(other: Vote): Boolean {
        return day == other.day
                && charaId == other.charaId
                && targetCharaId == other.targetCharaId
    }
}