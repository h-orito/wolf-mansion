package com.ort.app.api.view

import com.ort.app.domain.model.village.participant.VillageParticipant

data class OptionContent(
    val name: String,
    val value: String
) {
    constructor(participant: VillageParticipant) : this(
        name = participant.name(),
        value = participant.charaId.toString()
    )
}