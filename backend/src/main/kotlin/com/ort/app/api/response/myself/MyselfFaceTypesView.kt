package com.ort.app.api.response.myself

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "自分のキャラに紐づく表情差分一覧 (オリジナルキャラチップ)")
data class MyselfFaceTypesView(
    @field:Schema(description = "表情差分の一覧 (キャラの登録順)")
    val list: List<MyselfFaceTypeView>,
)

@Schema(description = "表情差分 1 件")
data class MyselfFaceTypeView(
    @field:Schema(description = "表情コード (= original_chara_image_id を文字列化したもの)")
    val code: String,
    @field:Schema(description = "表情差分名 (1-5 文字)")
    val name: String,
    @field:Schema(description = "差分画像 URL")
    val url: String,
    @field:Schema(description = "発言時に表示するか")
    val isDisplay: Boolean,
)
