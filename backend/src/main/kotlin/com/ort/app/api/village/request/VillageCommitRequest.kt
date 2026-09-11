package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull

/**
 * コミットの ON/OFF。永続化はトグル (現在の状態を反転) で、[commit] は
 * システムメッセージの文言 (コミットした / 取り消した) にのみ使われる。
 */
data class VillageCommitRequest(
    @field:NotNull
    val commit: Boolean? = null,
)
