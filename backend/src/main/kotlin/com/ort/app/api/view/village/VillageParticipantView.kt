package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantName
import com.ort.app.domain.model.village.room.Room

data class VillageParticipantView(
    val id: Int,
    val charaName: VillageParticipantName,
    val chara: Chara,
    val room: Room?,
    val dead: DeadView,
) {
    constructor(
        participant: VillageParticipant,
        participantIdToChara: Map<Int, Chara>
    ) : this(
        id = participant.id,
        charaName = participant.charaName,
        chara = participantIdToChara[participant.id]!!,
        room = participant.room,
        dead = DeadView(participant.dead)
    )
}