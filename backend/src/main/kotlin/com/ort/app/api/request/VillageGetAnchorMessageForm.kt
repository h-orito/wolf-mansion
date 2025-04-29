package com.ort.app.api.request

import jakarta.validation.constraints.NotNull

data class VillageGetAnchorMessageForm(
    /** 発言番号 */
    @NotNull
    val messageNumber: Int? = null,
    /** 発言種別 */
    @NotNull
    val messageType: String? = null
)