package com.ort.app.api.response.village

import com.ort.app.domain.model.village.Village
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村一覧用の軽量ビュー")
data class SimpleVillageView(
    @field:Schema(description = "村ID")
    val id: Int,
    @field:Schema(description = "村番号 (4桁ゼロ埋め)")
    val number: String,
    @field:Schema(description = "村名")
    val name: String,
    @field:Schema(description = "ステータスコード (例: 募集中, 進行中, エピローグ, 終了, 廃村)")
    val statusCode: String,
    @field:Schema(description = "ステータス表示名")
    val statusName: String,
    @field:Schema(description = "参加者数 (見学含まない)")
    val participantCount: Int,
    @field:Schema(description = "見学者数")
    val spectatorCount: Int,
    @field:Schema(description = "募集開始日時")
    val createDatetime: LocalDateTime,
    @field:Schema(description = "村作成者プレイヤー名")
    val createPlayerName: String,
) {
    constructor(village: Village) : this(
        id = village.id,
        number = village.id.toString().padStart(4, '0'),
        name = village.name,
        statusCode = village.status.code,
        statusName = village.status.name,
        participantCount = village.participants.count,
        spectatorCount = village.spectators.count,
        createDatetime = village.createDatetime,
        createPlayerName = village.createPlayerName,
    )
}
