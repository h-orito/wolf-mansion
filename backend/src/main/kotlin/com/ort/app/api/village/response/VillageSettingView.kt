package com.ort.app.api.village.response

import com.ort.app.domain.model.village.VillageSetting
import com.ort.app.domain.model.village.setting.SayRestriction
import com.ort.app.domain.model.village.setting.VillageCharaSetting
import com.ort.app.domain.model.village.setting.VillageOrganize
import com.ort.app.domain.model.village.setting.VillageRule
import com.ort.app.domain.model.village.setting.VillageTags
import java.time.LocalDateTime

/**
 * 村設定。ドメインの [VillageSetting] から非公開の入村パスワードのみ除外して返す
 * (REST API 設計方針: マスクが要る場合のみ Response DTO)。ネストした設定は
 * ドメインモデルをそのまま返す。
 */
data class VillageSettingView(
    val chara: VillageCharaSetting,
    val personMin: Int,
    val personMax: Int,
    val startDatetime: LocalDateTime,
    val dayChangeIntervalSeconds: Int,
    val rule: VillageRule,
    val organize: VillageOrganize,
    val sayRestriction: SayRestriction,
    val tags: VillageTags,
    val hasJoinPassword: Boolean,
) {
    constructor(setting: VillageSetting) : this(
        chara = setting.chara,
        personMin = setting.personMin,
        personMax = setting.personMax,
        startDatetime = setting.startDatetime,
        dayChangeIntervalSeconds = setting.dayChangeIntervalSeconds,
        rule = setting.rule,
        organize = setting.organize,
        sayRestriction = setting.sayRestriction,
        tags = setting.tags,
        hasJoinPassword = !setting.joinPassword.isNullOrEmpty(),
    )
}
