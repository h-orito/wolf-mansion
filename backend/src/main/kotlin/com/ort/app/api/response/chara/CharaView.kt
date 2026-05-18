package com.ort.app.api.response.chara

import com.ort.app.domain.model.chara.Chara
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "キャラクター (軽量)")
data class CharaView(
    @field:Schema(description = "キャラID")
    val id: Int,
    @field:Schema(description = "キャラ名")
    val name: String,
    @field:Schema(description = "短縮名 (2 文字)")
    val shortName: String,
    @field:Schema(description = "デフォルト画像 URL")
    val defaultImageUrl: String,
    @field:Schema(description = "画像の幅 (px)")
    val imageWidth: Int,
    @field:Schema(description = "画像の高さ (px)")
    val imageHeight: Int,
) {
    constructor(chara: Chara) : this(
        id = chara.id,
        name = chara.name,
        shortName = chara.shortName,
        defaultImageUrl = chara.defaultImage().url,
        imageWidth = chara.size.width,
        imageHeight = chara.size.height,
    )
}
