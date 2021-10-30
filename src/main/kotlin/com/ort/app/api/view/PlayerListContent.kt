package com.ort.app.api.view

import com.ort.app.domain.model.player.Players

data class PlayerListContent(
    val players: List<PlayerContent>,
    val allPageCount: Int,
    val isExistPrePage: Boolean,
    val isExistNextPage: Boolean,
    val currentPageNum: Int,
    val pageNumList: List<Int>,
) {

    data class PlayerContent(
        val name: String
    )

    constructor(
        players: Players
    ) : this(
        players = players.list.map { PlayerContent(name = it.name) },
        allPageCount = players.allPageCount,
        isExistNextPage = players.isExistNextPage,
        isExistPrePage = players.isExistPrePage,
        currentPageNum = players.currentPageNum,
        pageNumList = mapPageNumList(players)
    )

    companion object {
        private fun mapPageNumList(players: Players): List<Int> {
            val allPageCount: Int = players.allPageCount
            val currentPageNumber: Int = players.currentPageNum
            var startPage = currentPageNumber - 2
            var endPage = currentPageNumber + 2
            if (startPage < 1) {
                startPage = 1
                endPage = 5
            }
            if (endPage > allPageCount) {
                endPage = allPageCount
                startPage = allPageCount - 4
                if (startPage < 1) {
                    startPage = 1
                }
            }
            return (startPage..endPage).toList()
        }
    }
}
