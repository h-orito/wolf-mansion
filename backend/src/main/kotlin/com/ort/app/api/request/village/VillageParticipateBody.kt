package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * 入村リクエスト。
 *
 * 現状はキャラチップ制の村のみサポート。オリジナルキャラチップ
 * (`village.setting.chara.isOriginalCharachip == true`) で `charaImageFile` を伴う
 * 入村フローは別 endpoint (multipart) として将来追加する想定で、本 DTO では未対応。
 */
@Schema(description = "入村リクエスト")
data class VillageParticipateBody(
    @field:NotNull
    @field:Schema(description = "選択するキャラ ID")
    val charaId: Int,
    @field:NotNull
    @field:NotBlank
    @field:Size(min = 1, max = 40)
    @field:Schema(description = "村内表示名")
    val charaName: String,
    @field:NotNull
    @field:NotBlank
    @field:Size(min = 1, max = 1)
    @field:Schema(description = "村内表示用 1 文字略称")
    val charaShortName: String,
    @field:Schema(description = "希望役職コード (未指定 = おまかせ)")
    val requestedSkill: String? = null,
    @field:Schema(description = "第二希望役職コード (未指定 = おまかせ)")
    val secondRequestedSkill: String? = null,
    @field:NotNull
    @field:NotBlank
    @field:Schema(description = "入村メッセージ (発言扱い)")
    val joinMessage: String,
    @field:Schema(description = "村が入村パスワード必須のとき必要")
    val joinPassword: String? = null,
    @field:Schema(description = "見学者として参加する場合 true")
    val spectator: Boolean = false,
)
