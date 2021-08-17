package com.ort.app.domain.model.village.participant.dead

data class DeadHistory(
    val day: Int,
    // true: 死亡, false: 復活
    val isDead: Boolean,
    val reason: DeadReason?
)