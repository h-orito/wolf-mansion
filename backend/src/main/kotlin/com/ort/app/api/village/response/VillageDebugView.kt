package com.ort.app.api.village.response

import io.swagger.v3.oas.annotations.media.Schema

data class VillageDebugView(
    /** デバッグモード (app.debug) が有効か。無効時は players は常に空。 */
    val isDebugMode: Boolean,
    val players: List<DebugPlayerView>,
) {
    @Schema(name = "VillageDebugPlayer")
    data class DebugPlayerView(
        /** ダミーログインに使うユーザー ID (プレイヤー名) */
        val userId: String,
        /** 表示ラベル (キャラ名 + 役職名) */
        val label: String,
    )
}
