package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.chara.FaceType
import com.ort.app.domain.model.message.MessageType

data class ParticipantSaySituation(
    val isAvailableSay: Boolean,
    val selectableMessageTypeList: List<ParticipantSayMessageTypeSituation> = listOf(),
    val selectableFaceTypeList: List<FaceType>,
    val defaultMessageType: MessageType?
)