package com.ort.app.api.response.village

import com.ort.app.domain.model.village.Village
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村の現在時刻情報")
data class VillageTimeView(
    @field:Schema(description = "最新日 (現在進行中の日)")
    val latestDay: Int,
    @field:Schema(description = "次の日への更新日時 (終了している場合は null)")
    val nextDayChangeDatetime: LocalDateTime?,
) {
    constructor(village: Village) : this(
        latestDay = village.latestDay(),
        nextDayChangeDatetime = if (village.status.isFinished()) null
        else village.days.latestDay().dayChangeDatetime,
    )
}
