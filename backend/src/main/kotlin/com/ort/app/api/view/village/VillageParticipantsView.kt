package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.village.participant.VillageParticipants

data class VillageParticipantsView(
    val count: Int,
    val list: List<VillageParticipantView>
) {
    constructor(
        org: VillageParticipants,
        participantIdToChara: Map<Int, Chara>
    ) : this(
        count = org.count,
        list = org.list.map { VillageParticipantView(it, participantIdToChara) }
    )
}