package com.ort.app.api.response.village

import com.ort.app.domain.model.village.Villages
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "村一覧")
data class VillagesView(
    @field:Schema(description = "村のリスト (新着順)")
    val list: List<SimpleVillageView>,
) {
    constructor(villages: Villages) : this(
        list = villages.list.reversed().map { SimpleVillageView(it) },
    )
}
