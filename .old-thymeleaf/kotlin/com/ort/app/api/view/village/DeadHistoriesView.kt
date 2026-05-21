package com.ort.app.api.view.village

import com.ort.app.domain.model.village.participant.dead.DeadHistories

data class DeadHistoriesView(
    val list: List<DeadHistoryView>
) {
    constructor(
        org: DeadHistories
    ): this(
        list = org.list.map { DeadHistoryView(it) }
    )
}