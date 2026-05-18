package com.ort.app.api.response.village

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "村参加者一覧")
data class VillageParticipantsView(
    @field:Schema(description = "参加者リスト (部屋番号昇順、見学者は末尾)")
    val list: List<VillageParticipantView>,
    @field:Schema(description = "見学含まない参加者数")
    val count: Int,
    @field:Schema(description = "見学者数")
    val spectatorCount: Int,
)
