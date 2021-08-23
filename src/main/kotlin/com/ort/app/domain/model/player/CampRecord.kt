package com.ort.app.domain.model.player

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.village.Villages

data class CampRecord(
    val camp: Camp,
    val record: Record
) {
    constructor(
        camp: Camp,
        player: Player,
        villages: Villages
    ) : this(
        camp = camp,
        record = Record(player, villages)
    )
}