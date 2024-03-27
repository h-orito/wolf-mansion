package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.message.MessageType

data class ParticipantSaySituation(
    val isAvailableSay: Boolean,
    val selectableMessageTypeList: List<ParticipantSayMessageTypeSituation> = listOf(),
    val selectableCharaImageList: List<CharaImage>,
    val defaultMessageType: MessageType?
)