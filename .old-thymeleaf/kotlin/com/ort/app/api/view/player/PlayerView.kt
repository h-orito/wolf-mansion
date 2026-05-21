package com.ort.app.api.view.player

import com.ort.app.domain.model.player.Player

data class PlayerView(
    val id: Int,
    val name: String,
) {
    constructor(org: Player) : this(id = org.id, name = org.name)
}