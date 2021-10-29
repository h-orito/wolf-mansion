package com.ort.app.domain.model.player

data class Players(val list: List<Player>) {
    fun player(id: Int): Player = list.first { it.id == id }

    fun isSame(other: Players): Boolean {
        return list.size == other.list.size
                && list.all { p -> other.list.any { op -> p.isSame(p) } }
    }

    fun restrictParticipation(id: Int): Players = copy(
        list = list.map {
            if (it.id == id) it.restrictParticipation() else it.copy()
        }
    )
}