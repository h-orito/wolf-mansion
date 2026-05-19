package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "村建て発言リクエスト")
data class VillageCreatorSayBody(
    @field:NotBlank
    @field:Size(max = 400)
    @field:Schema(description = "発言本文 (400 文字以内)")
    val message: String,
    @field:Schema(description = "変換無効フラグ")
    val convertDisable: Boolean? = null,
)
