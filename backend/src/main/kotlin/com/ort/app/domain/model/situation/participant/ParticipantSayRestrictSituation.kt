package com.ort.app.domain.model.situation.participant

data class ParticipantSayRestrictSituation(
    val isRestricted: Boolean,
    val maxCount: Int?,
    val remainingCount: Int?,
    val maxLength: Int,
    val maxLine: Int
)