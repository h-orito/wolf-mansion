package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull

/**
 * アクション発言の確認/投稿。「{自分}は、{対象}{本文}」をサーバーで結合してアクション種別で投稿する (行為者名はサーバーで付ける)。
 */
data class VillageActionRequest(
    /** 対象 (選択しない場合は null) */
    val target: String? = null,
    @field:NotNull
    val message: String? = null,
    /** 装飾・変換を無効にするか */
    val convertDisable: Boolean? = null,
)
