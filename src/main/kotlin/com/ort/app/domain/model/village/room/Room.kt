package com.ort.app.domain.model.village.room

data class Room(
    val number: Int,
    val histories: RoomHistories
) {
    constructor(
        number: Int,
        day: Int
    ) : this(
        number = number,
        histories = RoomHistories(
            list = listOf(RoomHistory(day = day, number = number))
        )
    )

    // フルーツバスケット等があった場合は移動後が取得される
    fun numberWhen(day: Int): Int? {
        val d = if (day <= 1) 1 else day // 0日目を指定すると部屋が取れない
        if (histories.list.isEmpty()) return null
        val maxDay = histories.list.filter { it.day <= day }.map { it.day }.maxOrNull() ?: return null
        return histories.list.lastOrNull { it.day == maxDay }?.number
    }

    fun isSame(other: Room): Boolean {
        return number == other.number
                && histories.isSame(other.histories)
    }

    fun reAssign(number: Int, day: Int): Room {
        return copy(
            number = number,
            histories = histories.copy(
                list = histories.list + RoomHistory(day = day, number = number)
            )
        )
    }
}
