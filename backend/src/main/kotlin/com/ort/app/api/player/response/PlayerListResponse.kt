package com.ort.app.api.player.response

import com.ort.app.domain.model.player.Players

data class PlayerListResponse(
    val players: List<PlayerView>,
    val allPageCount: Int,
    val isExistPrePage: Boolean,
    val isExistNextPage: Boolean,
    val currentPageNum: Int,
) {
    constructor(players: Players) : this(
        players = players.list.map { PlayerView(name = it.name) },
        allPageCount = players.allPageCount,
        isExistPrePage = players.isExistPrePage,
        isExistNextPage = players.isExistNextPage,
        currentPageNum = players.currentPageNum,
    )
}

data class PlayerView(
    val name: String,
)
