package com.ort.app.api.response.chara

import com.ort.app.domain.model.chara.Charachip
import io.swagger.v3.oas.annotations.media.Schema

/**
 * キャラチップ詳細 (キャラ一覧 + メタ情報) を返す。
 * `/charachips/{id}` の表示および新規村作成時のダミーキャラ選択候補に使用する想定。
 */
@Schema(description = "キャラチップ詳細 (キャラ一覧つき)")
data class CharachipDetailView(
    @field:Schema(description = "キャラチップ ID") val id: Int,
    @field:Schema(description = "キャラチップ名") val name: String,
    @field:Schema(description = "作者名") val designerName: String,
    @field:Schema(description = "キャラチップ説明ページ URL (nullable)") val descriptionUrl: String?,
    @field:Schema(description = "キャラ名変更を許可するキャラチップか") val isAvailableChangeName: Boolean,
    @field:Schema(description = "キャラ一覧 (デフォルト画像のみ)") val charas: List<CharaView>,
) {
    constructor(charachip: Charachip) : this(
        id = charachip.id,
        name = charachip.name,
        designerName = charachip.designer?.name ?: "",
        descriptionUrl = charachip.descriptionUrl,
        isAvailableChangeName = charachip.isAvailableChangeName,
        charas = charachip.charas.list.map { CharaView(it) },
    )
}
