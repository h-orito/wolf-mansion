package com.ort.app.api.response.player

import com.ort.app.domain.model.player.Players
import io.swagger.v3.oas.annotations.media.Schema

/**
 * プレイヤー一覧 (ページング付き)。
 *
 * 旧 `/user-list` (Thymeleaf) の置き換え。1 件あたりは `name` のみで、詳細は
 * `/api/v1/players/{userName}` で別途取得する。
 */
@Schema(description = "プレイヤー一覧 (ページング)")
data class PlayersView(
    @field:Schema(description = "プレイヤーリスト")
    val list: List<PlayerSummaryView>,
    @field:Schema(description = "総ページ数")
    val allPageCount: Int,
    @field:Schema(description = "前ページが存在するか")
    val isExistPrePage: Boolean,
    @field:Schema(description = "次ページが存在するか")
    val isExistNextPage: Boolean,
    @field:Schema(description = "現在のページ番号 (1 起点)")
    val currentPageNum: Int,
    @field:Schema(description = "ページャに表示するページ番号 (現在ページ前後 ±2 / 端でクランプ)")
    val pageNumList: List<Int>,
) {
    @Schema(description = "プレイヤーサマリ")
    data class PlayerSummaryView(
        @field:Schema(description = "プレイヤー名")
        val name: String,
    )

    constructor(players: Players) : this(
        list = players.list.map { PlayerSummaryView(name = it.name) },
        allPageCount = players.allPageCount,
        isExistPrePage = players.isExistPrePage,
        isExistNextPage = players.isExistNextPage,
        currentPageNum = players.currentPageNum,
        pageNumList = buildPageNumList(players),
    )

    companion object {
        private fun buildPageNumList(players: Players): List<Int> {
            val allPageCount = players.allPageCount
            if (allPageCount <= 0) return emptyList()
            val currentPageNumber = players.currentPageNum
            var startPage = currentPageNumber - 2
            var endPage = currentPageNumber + 2
            if (startPage < 1) {
                startPage = 1
                endPage = 5
            }
            if (endPage > allPageCount) {
                endPage = allPageCount
                startPage = allPageCount - 4
                if (startPage < 1) startPage = 1
            }
            return (startPage..endPage).toList()
        }
    }
}
