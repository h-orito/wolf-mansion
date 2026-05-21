package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * 入村リクエスト。
 *
 * 非オリジナル村 (公式キャラチップ) は JSON endpoint `POST /participate` で `charaId` 必須。
 * オリジナルキャラチップ村は multipart endpoint `POST /participate` (`consumes=multipart/form-data`)
 * で `body` (この DTO の JSON) と `charaImage` (画像) を送る。multipart 経路では `charaId=null` で
 * 構わない (chara は backend で動的に作成される)。
 */
@Schema(description = "入村リクエスト")
data class VillageParticipateBody(
    @field:Schema(description = "選択するキャラ ID (非オリジナル村で必須、オリジナル村では null)")
    val charaId: Int? = null,
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
