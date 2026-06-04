package com.ort.app.domain.model.village

data class VillageDays(val list: List<VillageDay>) {
    fun latestDay(): VillageDay = list.last()

    fun isSame(other: VillageDays): Boolean {
        return list.size == other.list.size
                && list.all { day -> other.list.any { otherDay -> day.isSame(otherDay) } }
    }

    fun extenrPrologue(): VillageDays = copy(
        list = list.map {
            if (it.day == 0) it.copy(dayChangeDatetime = it.dayChangeDatetime.plusDays(1L))
            else it
        }
    )

    fun addNewDay(intervalSeconds: Int): VillageDays {
        val latest = latestDay()
        return copy(
            list = list + VillageDay(
                day = latest.day + 1,
                dayChangeDatetime = latest.dayChangeDatetime.plusSeconds(intervalSeconds.toLong())
            )
        )
    }

    fun toEpilogue(): VillageDays =
        copy(
            list = list.map {
                // 最新日の長さを24hにする
                if (it.day == latestDay().day) it.copy(
                    dayChangeDatetime = list[list.size - 2].dayChangeDatetime.plusDays(1L)
                )
                else it.copy()
            }
        )
}