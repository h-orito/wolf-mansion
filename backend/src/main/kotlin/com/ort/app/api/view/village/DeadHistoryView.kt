package com.ort.app.api.view.village

import com.ort.app.domain.model.village.participant.dead.DeadHistory

data class DeadHistoryView(
    val day: Int,
    // true: 死亡, false: 復活
    val isDead: Boolean,
    val reason: DeadReasonView?
) {
    constructor(
        org: DeadHistory
    ) : this(
        day = org.day,
        isDead = org.isDead,
        reason = org.reason?.let { DeadReasonView.of(it) }
    )
}