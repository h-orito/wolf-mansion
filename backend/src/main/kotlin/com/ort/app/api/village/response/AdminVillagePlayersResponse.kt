package com.ort.app.api.village.response

import com.ort.app.application.service.VillageCharaPlayer
import io.swagger.v3.oas.annotations.media.Schema

data class AdminVillagePlayersResponse(
    val players: List<AdminVillageCharaPlayer>,
) {
    @Schema(name = "AdminVillageCharaPlayer")
    data class AdminVillageCharaPlayer(
        val charaName: String,
        val playerName: String,
    )

    companion object {
        fun of(players: List<VillageCharaPlayer>): AdminVillagePlayersResponse =
            AdminVillagePlayersResponse(
                players = players.map { AdminVillageCharaPlayer(charaName = it.charaName, playerName = it.playerName) },
            )
    }
}
