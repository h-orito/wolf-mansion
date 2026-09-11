package com.ort.app.api.village.request

import com.ort.app.api.request.VillageActionForm
import jakarta.validation.constraints.NotNull

/**
 * アクション発言の確認/投稿。「{自分}は、{対象}{本文}」を結合してアクション種別で投稿する。
 * 検証は SSR と共通の ActionFormValidator を [toForm] 変換後に流用する。
 */
data class VillageActionRequest(
    /** 「〜は、」の prefix (表示名由来) */
    @field:NotNull
    val myself: String? = null,
    /** 対象 (選択しない場合は null) */
    val target: String? = null,
    @field:NotNull
    val message: String? = null,
    /** 装飾・変換を無効にするか */
    val convertDisable: Boolean? = null,
) {
    fun toForm(): VillageActionForm =
        VillageActionForm(
            myself = myself,
            target = target,
            message = message,
            convertDisable = convertDisable,
        )
}
