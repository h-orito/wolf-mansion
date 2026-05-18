package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(description = "表情差分編集リクエスト (オリジナルキャラチップ用)")
data class VillageFaceTypeModifyBody(
    @field:NotEmpty
    @field:Valid
    @field:Schema(description = "編集対象の表情差分リスト")
    val faceTypeList: List<FaceTypeItem>,
) {
    @Schema(description = "表情差分 1 件")
    data class FaceTypeItem(
        @field:NotBlank
        @field:Schema(description = "表情コード")
        val code: String,
        @field:NotBlank
        @field:Size(min = 1, max = 5)
        @field:Schema(description = "表情名 (1-5 文字)")
        val name: String,
        @field:Schema(description = "表示する場合 true、非表示なら false")
        val display: Boolean,
    )
}
