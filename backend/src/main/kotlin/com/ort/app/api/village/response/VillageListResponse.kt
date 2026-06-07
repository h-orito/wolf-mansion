package com.ort.app.api.village.response

import com.ort.app.domain.model.village.Villages

/**
 * 村一覧 (`GET /api/v1/villages`) のレスポンス。
 * 一覧用の軽量ビュー [SimpleVillageView] の list を返すだけ (**表示整形は画面側**)。
 * 複数画面 (トップ・村一覧) で共有する。
 */
data class VillageListResponse(
    val villages: List<SimpleVillageView>,
) {
    constructor(villages: Villages) : this(villages = villages.list.map { SimpleVillageView(it) })
}
