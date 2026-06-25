package com.ort.app.api.village.request

import com.ort.app.api.request.VillageSayForm
import jakarta.validation.constraints.NotNull

/**
 * 発言の確認/投稿。検証は SSR と共通の SayFormValidator を [toForm] 変換後に流用する。
 */
data class VillageSayRequest(
    @field:NotNull
    val message: String? = null,
    /** 発言種別コード */
    @field:NotNull
    val messageType: String? = null,
    /** 表情種別コード */
    @field:NotNull
    val faceType: String? = null,
    /** 装飾・変換を無効にするか */
    val convertDisable: Boolean? = null,
    /** 秘話の宛先キャラ ID (秘話のみ必須) */
    val secretSayTargetCharaId: Int? = null,
) {
    fun toForm(): VillageSayForm =
        VillageSayForm(
            message = message,
            messageType = messageType,
            secretSayTargetCharaId = secretSayTargetCharaId,
            convertDisable = convertDisable,
            faceType = faceType,
        )
}
