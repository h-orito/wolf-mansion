package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageGetAnchorMessageForm(
    /** 村ID */
    @NotNull
    val villageId: Int? = null,

    /** 発言番号 */
    @NotNull
    val messageNumber: Int? = null,

    /** 発言種別 */
    @NotNull
    val messageType: String? = null
)