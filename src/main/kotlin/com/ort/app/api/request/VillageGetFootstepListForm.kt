package com.ort.app.api.request

import javax.validation.constraints.NotNull

data class VillageGetFootstepListForm(
    /** 村ID  */
    @field:NotNull
    val villageId: Int? = null,

    /** 実行者キャラID(狼のみ)  */
    val charaId: Int? = null,

    /** 対象キャラID  */
    val targetCharaId: Int? = null
)