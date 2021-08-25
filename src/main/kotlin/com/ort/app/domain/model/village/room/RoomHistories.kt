package com.ort.app.domain.model.village.room

data class RoomHistories(val list: List<RoomHistory>) {

    fun isSame(other: RoomHistories): Boolean {
        return list.size == other.list.size
                && list.all { rh -> other.list.any { orh -> rh.isSame(orh) } }
    }
}