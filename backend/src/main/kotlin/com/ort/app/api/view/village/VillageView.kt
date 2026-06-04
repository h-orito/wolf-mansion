package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageDays
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.room.RoomSize

data class VillageView(
    val id: Int,
    val name: String,
    val status: VillageStatus,
    val roomSize: RoomSize?,
    val participants: VillageParticipantsView,
    val days: VillageDays,
    val setting: VillageSettingView,
    val epilogueDay: Int?,
) {
    constructor(
        org: Village,
        participantIdToChara: Map<Int, Chara>
    ) : this(
        id = org.id,
        name = org.name,
        status = org.status,
        roomSize = org.roomSize,
        participants = VillageParticipantsView(org.participants, participantIdToChara),
        days = org.days,
        setting = VillageSettingView(org.setting),
        epilogueDay = org.epilogueDay,
    )
}