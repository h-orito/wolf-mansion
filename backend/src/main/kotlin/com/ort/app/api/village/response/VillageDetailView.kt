package com.ort.app.api.village.response

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageDays
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.room.RoomSize

/**
 * 村詳細。ドメインの [Village] から、ビューアに依らず公開してよい情報のみを返す。
 * 入村パスワードは [VillageSettingView] が除外する。参加者まわりは視点依存マスクが
 * 必要なため村状況 API (VillageSituationView) が担い、本ビューには含めない。
 */
data class VillageDetailView(
    val id: Int,
    val name: String,
    val status: VillageStatus,
    val days: VillageDays,
    val epilogueDay: Int?,
    val roomSize: RoomSize?,
    val setting: VillageSettingView,
) {
    constructor(village: Village) : this(
        id = village.id,
        name = village.name,
        status = village.status,
        days = village.days,
        epilogueDay = village.epilogueDay,
        roomSize = village.roomSize,
        setting = VillageSettingView(village.setting),
    )
}
