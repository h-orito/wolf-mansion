package com.ort.app.domain.model.footstep

data class Footstep(
    val day: Int,
    val registerCharaId: Int,
    val charaId: Int,
    val roomNumbers: String
) {
    constructor(
        day: Int,
        charaId: Int,
        roomNumbers: String
    ) : this(
        day = day,
        registerCharaId = charaId,
        charaId = charaId,
        roomNumbers = roomNumbers
    )

    fun isSame(other: Footstep): Boolean {
        return day == other.day
                && registerCharaId == other.registerCharaId
                && charaId == other.charaId
                && roomNumbers == other.roomNumbers
    }
}