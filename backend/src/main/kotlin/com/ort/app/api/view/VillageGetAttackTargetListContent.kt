package com.ort.app.api.view

import com.ort.app.domain.model.village.participant.VillageParticipants

data class VillageGetAttackTargetListContent(
    val attackTargetList: List<OptionContent>
) {
    constructor(participants: VillageParticipants) : this(
        attackTargetList = participants.list.map { OptionContent(it) }
    )
}