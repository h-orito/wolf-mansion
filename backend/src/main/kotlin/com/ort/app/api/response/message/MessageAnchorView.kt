package com.ort.app.api.response.message

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "発言アンカー (発言種別 + 番号)")
data class MessageAnchorView(
    @field:Schema(description = "発言種別コード")
    val typeCode: String,
    @field:Schema(description = "発言番号")
    val number: Int,
)
