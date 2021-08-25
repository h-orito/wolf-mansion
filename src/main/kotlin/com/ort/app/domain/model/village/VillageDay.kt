package com.ort.app.domain.model.village

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class VillageDay(
    val day: Int,
    val dayChangeDatetime: LocalDateTime
) {
    private val formatter = DateTimeFormatter.ofPattern("uuuuMMddhhmm")

    fun isSame(other: VillageDay): Boolean {
        return day == other.day
                && dayChangeDatetime.format(formatter) == other.dayChangeDatetime.format(formatter)
    }
}