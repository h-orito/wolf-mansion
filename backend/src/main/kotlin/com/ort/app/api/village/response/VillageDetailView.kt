package com.ort.app.api.village.response

import com.ort.app.api.view.village.VillageParticipantsView
import com.ort.app.api.view.village.VillageSettingsContent
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageDays
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.room.RoomSize

/**
 * 村詳細。ドメインの [Village] から、ビューアに依らず公開してよい情報のみを返す。
 * 入村パスワードは [VillageSettingView] が除外する。
 * 参加者の役職・陣営はエピローグ以降のみ公開する (視点に依存しない)。
 */
data class VillageDetailView(
    val id: Int,
    val name: String,
    val status: VillageStatus,
    val days: VillageDays,
    val epilogueDay: Int?,
    val roomSize: RoomSize?,
    val setting: VillageSettingView,
    val participants: VillageParticipantsView,
    val spectators: VillageParticipantsView,
    val info: VillageSettingsContent,
) {
    constructor(village: Village, charachips: Charachips, players: Players) : this(
        id = village.id,
        name = village.name,
        status = village.status,
        days = village.days,
        epilogueDay = village.epilogueDay,
        roomSize = village.roomSize,
        setting = VillageSettingView(village.setting),
        participants =
            VillageParticipantsView(
                village.participants,
                charachips,
                !village.status.isSettled(),
                players,
                village.status.isPrologue(),
            ),
        spectators =
            VillageParticipantsView(
                village.spectators,
                charachips,
                !village.status.isSettled(),
                players,
                village.status.isPrologue(),
            ),
        info = VillageSettingsContent(village, charachips),
    )
}
