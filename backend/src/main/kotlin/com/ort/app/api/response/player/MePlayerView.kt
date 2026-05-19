package com.ort.app.api.response.player

import com.ort.app.domain.model.player.Player
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 自分視点のプレイヤー基本情報。
 *
 * `/api/v1/players/me` で返す軽量プロフィール。村作成 / 参加可否などの導線判定に必要な
 * フラグも含む。戦績や参加履歴は `PlayerDetailView` (`/api/v1/players/{userName}`) を参照。
 *
 * NOTE: 旧 `com.ort.app.api.view.player.PlayerView` (Step 9 で削除予定の legacy) と
 * OpenAPI スキーマ名が衝突するため `MePlayerView` で別名にしている。
 */
@Schema(description = "プレイヤー (自分視点)")
data class MePlayerView(
    @field:Schema(description = "プレイヤー名")
    val name: String,
    @field:Schema(description = "Twitter ユーザ名 (未設定なら null)", nullable = true)
    val twitterUserName: String?,
    @field:Schema(description = "自己紹介 (未設定なら null)", nullable = true)
    val introduction: String?,
    @field:Schema(description = "権限コード", example = "プレイヤー")
    val authorityCode: String,
    @field:Schema(description = "権限名", example = "プレイヤー")
    val authorityName: String,
    @field:Schema(description = "参加制限中か")
    val isRestrictedParticipation: Boolean,
    @field:Schema(description = "村作成可能か")
    val isAvailableCreateVillage: Boolean,
) {
    constructor(player: Player) : this(
        name = player.name,
        twitterUserName = player.twitterUserName,
        introduction = player.introduction,
        authorityCode = player.authority.code,
        authorityName = player.authority.name,
        isRestrictedParticipation = player.isRestrictedParticipation,
        isAvailableCreateVillage = player.isAvailableCreateVillage(),
    )
}
