package com.ort.app.api.village.request

import jakarta.validation.constraints.NotBlank

/**
 * 村建て発言 (天の声)。文字数・行数制限は CreatorSayFormValidator (通常発言より寛容な 1000 字 / 40 行) を流用して検証する。
 */
data class VillageCreatorSayRequest(
    @field:NotBlank val message: String? = null,
    val convertDisable: Boolean? = null,
)
