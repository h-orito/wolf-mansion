package com.ort.app.api.response.village

import com.ort.app.domain.model.village.Village
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村設定 (read-only)")
data class VillageSettingsView(
    @field:Schema(description = "最小人数")
    val personMin: Int,
    @field:Schema(description = "最大人数")
    val personMax: Int,
    @field:Schema(description = "プロローグの開始予定日時")
    val startDatetime: LocalDateTime,
    @field:Schema(description = "日付更新の間隔 (秒)")
    val dayChangeIntervalSeconds: Int,
    @field:Schema(description = "入村パスワードの有無")
    val joinPasswordRequired: Boolean,
    @field:Schema(description = "オリジナルキャラチップの村か")
    val isOriginalCharachip: Boolean,
) {
    constructor(village: Village) : this(
        personMin = village.setting.personMin,
        personMax = village.setting.personMax,
        startDatetime = village.setting.startDatetime,
        dayChangeIntervalSeconds = village.setting.dayChangeIntervalSeconds,
        joinPasswordRequired = !village.setting.joinPassword.isNullOrEmpty(),
        isOriginalCharachip = village.setting.chara.isOriginalCharachip,
    )
}
