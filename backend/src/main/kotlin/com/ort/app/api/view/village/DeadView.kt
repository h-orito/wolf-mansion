package com.ort.app.api.view.village

import com.ort.app.domain.model.village.participant.dead.Dead

data class DeadView(
    val isDead: Boolean,
    val deadDay: Int?,
    val reason: DeadReasonView?,
    val histories: DeadHistoriesView
) {
    constructor(
        org: Dead
    ): this(
        isDead = org.isDead,
        deadDay = org.deadDay,
        reason = org.reason?.let { DeadReasonView.of(it)},
        histories = DeadHistoriesView(org.histories)
    )
}