package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(description = "管理者による強制退村リクエスト")
data class VillageAdminLeaveBody(
    @field:NotNull
    @field:Schema(description = "退村させる villagePlayerId (= 参加者 ID)")
    val villagePlayerId: Int,
)
