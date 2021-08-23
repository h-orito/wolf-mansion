package com.ort.app.domain.model.player

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class ParticipateVillage(
    val village: Village,
    val participant: VillageParticipant
) {
    constructor(
        player: Player,
        village: Village
    ) : this(
        village = village,
        participant = village.allParticipants().list.first { !it.isGone && it.playerId == player.id }
    )
}