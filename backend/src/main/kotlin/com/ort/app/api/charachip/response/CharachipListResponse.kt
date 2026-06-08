package com.ort.app.api.charachip.response

import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips

/**
 * キャラセット一覧 (`GET /api/v1/charachips`) のレスポンス。
 * 一覧・選択に必要な最小限 (id / name) のみを返す (キャラ明細などの重い情報は含めない)。
 */
data class CharachipListResponse(
    val charachips: List<SimpleCharachipView>,
) {
    constructor(charachips: Charachips) : this(charachips = charachips.list.map { SimpleCharachipView(it) })
}

/** キャラセットの軽量ビュー。 */
data class SimpleCharachipView(
    val id: Int,
    val name: String,
) {
    constructor(charachip: Charachip) : this(id = charachip.id, name = charachip.name)
}
