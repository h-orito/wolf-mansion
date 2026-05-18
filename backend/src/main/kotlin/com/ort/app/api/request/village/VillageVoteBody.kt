package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(description = "投票リクエスト")
data class VillageVoteBody(
    @field:NotNull
    @field:Schema(description = "投票対象キャラ ID")
    val targetCharaId: Int,
)
