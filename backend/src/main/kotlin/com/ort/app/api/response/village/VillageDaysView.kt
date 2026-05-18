package com.ort.app.api.response.village

import com.ort.app.domain.model.village.VillageDays
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "村の日付一覧")
data class VillageDaysView(
    @field:Schema(description = "日付リスト (昇順)")
    val list: List<VillageDayView>,
) {
    constructor(days: VillageDays) : this(
        list = days.list.map { VillageDayView(it) },
    )
}
