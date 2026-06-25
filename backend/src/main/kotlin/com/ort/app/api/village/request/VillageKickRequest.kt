package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull

/**
 * 強制退村 (プロローグ中のみ)。
 */
data class VillageKickRequest(
    @field:NotNull val charaId: Int? = null,
)
