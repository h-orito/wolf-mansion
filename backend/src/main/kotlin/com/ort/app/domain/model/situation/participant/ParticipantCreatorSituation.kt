package com.ort.app.domain.model.situation.participant

data class ParticipantCreatorSituation(
    val isCreator: Boolean,
    val isAvailableCreatorSay: Boolean,
    val isAvailableCancelVillage: Boolean,
    val isAvailableKick: Boolean,
    val isAvailableModifySetting: Boolean,
    val isAvailableExtendEpilogue: Boolean
)
