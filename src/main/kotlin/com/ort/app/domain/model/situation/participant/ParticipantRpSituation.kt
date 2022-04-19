package com.ort.app.domain.model.situation.participant

data class ParticipantRpSituation(
    val isAvailableChangeName: Boolean,
    val isAvailableMemo: Boolean,
    val canAddImage: Boolean
)