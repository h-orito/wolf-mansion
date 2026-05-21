package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

data class VillageMessageForm(
    /** アンカー文字列 */
    @NotNull
    val anchors: String? = null,
)