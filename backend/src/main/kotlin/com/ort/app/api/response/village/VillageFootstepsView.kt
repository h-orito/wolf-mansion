package com.ort.app.api.response.village

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "足音一覧 (隠蔽済み)")
data class VillageFootstepsView(
    @field:Schema(description = "足音のリスト (日付昇順 → 経路昇順)")
    val list: List<VillageFootstepView>,
)
