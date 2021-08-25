package com.ort.app.domain.model.village.room

data class RoomHistory(val day: Int, val number: Int) {

    fun isSame(other: RoomHistory): Boolean {
        return day == other.day
                && number == other.number
    }
}