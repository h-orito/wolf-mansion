package com.ort.app.api.response.village

import com.ort.app.domain.model.village.Villages
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "村一覧")
data class VillagesView(
    @field:Schema(description = "村のリスト (新着順)")
    val list: List<SimpleVillageView>,
) {
    // DB の取得順 (現状 ASC) に依存せず、view 層で明示的に新着順に並べる。
    // 件数が増えた際は DataSource にページネーション + ORDER BY DESC を導入し、
    // ここの再ソートは取り除くこと (Step 6 以降の課題)。
    constructor(villages: Villages) : this(
        list = villages.list.sortedByDescending { it.id }.map { SimpleVillageView(it) },
    )
}
