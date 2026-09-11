package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/** 簡易メモの変更 (空文字でクリア)。参加者一覧に表示される公開情報。 */
data class VillageMemoRequest(
    @field:NotNull
    @field:Size(max = 20)
    val memo: String? = null,
)
