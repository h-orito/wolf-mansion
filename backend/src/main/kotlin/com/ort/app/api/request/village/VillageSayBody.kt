package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "発言リクエスト")
data class VillageSayBody(
    @field:NotBlank
    @field:Size(max = 400)
    @field:Schema(description = "発言本文 (改行を含めて 400 文字以内、改行は 20 行まで)")
    val message: String,
    @field:NotNull
    @field:NotBlank
    @field:Schema(description = "発言種別コード (CDef.MessageType)")
    val messageType: String,
    @field:Schema(description = "秘話相手のキャラ ID (秘話のときのみ)")
    val secretSayTargetCharaId: Int? = null,
    @field:Schema(description = "変換無効フラグ")
    val convertDisable: Boolean? = null,
    @field:Schema(description = "表情種別コード")
    val faceType: String? = null,
)
