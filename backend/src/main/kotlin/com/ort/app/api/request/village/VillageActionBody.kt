package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "アクション発言リクエスト")
data class VillageActionBody(
    @field:NotNull
    @field:NotBlank
    @field:Schema(description = "アクション主体 (例: 主語フレーズ)")
    val myself: String,
    @field:Schema(description = "アクション対象フレーズ (省略可)")
    val target: String? = null,
    @field:NotNull
    @field:NotBlank
    @field:Schema(description = "アクション本文。`myself + target + message` の合計が 1-400 文字に収まる必要がある")
    val message: String,
    @field:Schema(description = "変換無効フラグ")
    val convertDisable: Boolean? = null,
)
