package com.ort.app.api.view.player

import com.ort.app.domain.model.player.Player

data class PlayerView(
    val id: Int,
    val name: String,
    val canCreateVillage: Boolean = false,
) {
    constructor(org: Player) : this(
        id = org.id,
        name = org.name,
        canCreateVillage = org.isAvailableCreateVillage()
    )
}