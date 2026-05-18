package com.ort.app.api.response.village

import com.ort.app.domain.model.village.VillageDay
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村の日付")
data class VillageDayView(
    @field:Schema(description = "何日目か (0 = プロローグ)")
    val day: Int,
    @field:Schema(description = "次の日への更新日時")
    val dayChangeDatetime: LocalDateTime,
) {
    constructor(day: VillageDay) : this(
        day = day.day,
        dayChangeDatetime = day.dayChangeDatetime,
    )
}
