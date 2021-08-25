package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.village.participant.VillageParticipant

data class ParticipantParticipateSituation(
    val isParticipating: Boolean,
    val isAvailableParticipate: Boolean,
    val isAvailableSpectate: Boolean,
    val selectableCharaList: List<Chara>,
    val isAvailableLeave: Boolean,
    val myself: VillageParticipant?
)