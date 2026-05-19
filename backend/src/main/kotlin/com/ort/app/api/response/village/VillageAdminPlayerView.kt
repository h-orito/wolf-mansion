package com.ort.app.api.response.village

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "管理者向け: 村参加プレイヤー (キャラ ↔ 中の人) 1 行")
data class VillageAdminPlayerView(
    @field:Schema(description = "キャラ名 (略称含む)")
    val charaName: String,
    @field:Schema(description = "中の人プレイヤー名")
    val playerName: String,
)
