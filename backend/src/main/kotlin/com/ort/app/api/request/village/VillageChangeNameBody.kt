package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "キャラ名変更リクエスト")
data class VillageChangeNameBody(
    @field:NotBlank
    @field:Size(min = 1, max = 40)
    @field:Schema(description = "新しい表示名")
    val name: String,
    @field:NotBlank
    @field:Size(min = 1, max = 1)
    @field:Schema(description = "新しい 1 文字略称")
    val shortName: String,
)
