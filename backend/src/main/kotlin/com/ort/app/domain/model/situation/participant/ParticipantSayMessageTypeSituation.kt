package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.village.participant.VillageParticipant

data class ParticipantSayMessageTypeSituation(
    val messageType: MessageType,
    val restrict: ParticipantSayRestrictSituation,
    // 秘話用
    val targetList: List<VillageParticipant>
)