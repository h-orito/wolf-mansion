package com.ort.app.domain.model.footstep

data class Footstep(
    val day: Int,
    val charaId: Int,
    val roomNumbers: String
) {
    fun isSame(other: Footstep): Boolean {
        return day == other.day
                && charaId == other.charaId
                && roomNumbers == other.roomNumbers
    }
}