package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(description = "簡易メモ変更リクエスト")
data class VillageMemoBody(
    @field:Size(max = 20)
    @field:Schema(description = "メモ内容 (20 文字以内、空文字でクリア)")
    val memo: String,
)
