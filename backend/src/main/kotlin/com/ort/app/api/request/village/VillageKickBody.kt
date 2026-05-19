package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(description = "村建てによる強制退村リクエスト")
data class VillageKickBody(
    @field:NotNull
    @field:Schema(description = "退村させるキャラ ID")
    val charaId: Int,
)
