package com.ort.app.api.response.chara

import com.ort.app.domain.model.chara.Charachip
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "キャラチップ概要 (一覧表示用)")
data class CharachipView(
    @field:Schema(description = "キャラチップ ID") val id: Int,
    @field:Schema(description = "キャラチップ名") val name: String,
    @field:Schema(description = "作者名") val designerName: String,
    @field:Schema(description = "キャラ数") val charaCount: Int,
    @field:Schema(description = "ダミーキャラの代表画像 URL") val dummyImageUrl: String,
    @field:Schema(description = "ダミー画像の幅 (px)") val dummyImageWidth: Int,
    @field:Schema(description = "ダミー画像の高さ (px)") val dummyImageHeight: Int,
    @field:Schema(description = "キャラチップ説明ページ URL (nullable)") val descriptionUrl: String?,
    @field:Schema(description = "オリジナルキャラチップか (= プレイヤー登録による画像)") val isOriginal: Boolean,
) {
    constructor(charachip: Charachip, isOriginal: Boolean = false) : this(
        id = charachip.id,
        name = charachip.name,
        designerName = charachip.designer?.name ?: "",
        charaCount = charachip.charas.list.size,
        dummyImageUrl = charachip.dummyChara().defaultImage().url,
        dummyImageWidth = charachip.dummyChara().size.width,
        dummyImageHeight = charachip.dummyChara().size.height,
        descriptionUrl = charachip.descriptionUrl,
        isOriginal = isOriginal,
    )
}
