package com.ort.app.domain.model.village.participant.dead

data class Dead(
    val isDead: Boolean,
    val deadDay: Int?,
    val reason: DeadReason?,
    val histories: DeadHistories
)