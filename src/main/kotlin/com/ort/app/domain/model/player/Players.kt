package com.ort.app.domain.model.player

data class Players(
    val list: List<Player>,
    val allPageCount: Int = 0,
    val isExistPrePage: Boolean = false,
    val isExistNextPage: Boolean = false,
    val currentPageNum: Int = 0
) {
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