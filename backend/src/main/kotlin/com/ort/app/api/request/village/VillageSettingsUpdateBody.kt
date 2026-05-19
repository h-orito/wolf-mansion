package com.ort.app.api.request.village

import com.ort.app.api.request.VillageSettingForm
import com.ort.app.api.request.setting.MessageTypeSayRestrictForm
import com.ort.app.api.request.setting.RandomOrganizationCampForm
import com.ort.app.api.request.setting.RandomOrganizationSkillForm
import com.ort.app.api.request.setting.RandomOrganizationWolfForm
import com.ort.app.api.request.setting.SkillSayRestrictForm
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * 村設定変更 (PUT /api/v1/villages/{id}/settings) リクエスト body。
 *
 * 旧 `VillageSettingForm` (Thymeleaf form) の JSON 受信用スリム版。表示専用フィールド
 * (`campName` / `skillName` / `messageTypeName` 等) は含まない。
 *
 * 入力後の cross-field バリデーションは `SettingFormValidator` を再利用するため、
 * `toForm()` で旧 form に変換してから検証する (一貫性を保つため重複定義は避ける)。
 *
 * フィールド単位の必須/範囲チェックは Jakarta Bean Validation で行う
 * (`@RequestBody @Valid` で自動実行)。
 */
@Schema(description = "村設定変更 (creator) 用リクエスト body")
data class VillageSettingsUpdateBody(
    @field:Schema(description = "村表示名 (5〜40文字)")
    @field:NotNull
    @field:Size(min = 5, max = 40)
    val villageName: String?,

    @field:Schema(description = "最少開始人数 (8〜999)")
    @field:NotNull
    @field:Min(8)
    @field:Max(999)
    val startPersonMinNum: Int?,

    @field:Schema(description = "定員 (8〜999)")
    @field:NotNull
    @field:Min(8)
    @field:Max(999)
    val personMaxNum: Int?,

    @field:Schema(description = "更新間隔: 時間 (0-72)")
    @field:NotNull
    @field:Min(0)
    @field:Max(72)
    val dayChangeIntervalHours: Int?,

    @field:Schema(description = "更新間隔: 分 (0-59)")
    @field:NotNull
    @field:Min(0)
    @field:Max(59)
    val dayChangeIntervalMinutes: Int?,

    @field:Schema(description = "更新間隔: 秒 (0-59)")
    @field:NotNull
    @field:Min(0)
    @field:Max(59)
    val dayChangeIntervalSeconds: Int?,

    @field:Schema(description = "開始年")
    @field:NotNull
    @field:Min(0)
    val startYear: Int?,

    @field:Schema(description = "開始月 (1-12)")
    @field:NotNull
    @field:Min(1)
    @field:Max(12)
    val startMonth: Int?,

    @field:Schema(description = "開始日 (1-31)")
    @field:NotNull
    @field:Min(1)
    @field:Max(31)
    val startDay: Int?,

    @field:Schema(description = "開始時 (0-23)")
    @field:NotNull
    @field:Min(0)
    @field:Max(23)
    val startHour: Int?,

    @field:Schema(description = "開始分 (0-59)")
    @field:NotNull
    @field:Min(0)
    @field:Max(59)
    val startMinute: Int?,

    @field:Schema(description = "募集範囲タグ (VillageTagItem code: ANYONE_WELCOME / RELATIVES_ONLY)。未指定なら null。")
    val welcomeRange: String?,

    @field:Schema(description = "年齢制限タグ (VillageTagItem code: R15 / R18)。未指定なら null。")
    val ageLimit: String?,

    @field:Schema(description = "記名投票か")
    @field:NotNull
    val openVote: Boolean?,

    @field:Schema(description = "連続襲撃ありか")
    @field:NotNull
    val availableSameWolfAttack: Boolean?,

    @field:Schema(description = "墓下役職公開ありか")
    @field:NotNull
    val openSkillInGrave: Boolean?,

    @field:Schema(description = "墓下見学発言を地上から見られるか")
    @field:NotNull
    val visibleGraveSpectateMessage: Boolean?,

    @field:Schema(description = "秘話可能範囲 (CDef.AllowedSecretSay code)")
    @field:NotNull
    val allowedSecretSayCode: String?,

    @field:Schema(description = "見学可能か")
    @field:NotNull
    val availableSpectate: Boolean?,

    @field:Schema(description = "突然死ありか")
    @field:NotNull
    val availableSuddenlyDeath: Boolean?,

    @field:Schema(description = "コミット可能か")
    @field:NotNull
    val availableCommit: Boolean?,

    @field:Schema(description = "連続ガードありか")
    @field:NotNull
    val availableGuardSameTarget: Boolean?,

    @field:Schema(description = "アクションありか")
    @field:NotNull
    val availableAction: Boolean?,

    @field:Schema(description = "構成 (改行区切り、闇鍋でないときに参照)")
    val organization: String?,

    @field:Schema(description = "闇鍋編成か")
    @field:NotNull
    val randomOrganization: Boolean?,

    @field:Schema(description = "転生時に全役職を候補とするか")
    @field:NotNull
    val reincarnationSkillAll: Boolean?,

    @field:Schema(description = "闇鍋編成詳細 (camp -> skills)")
    @field:Valid
    val campAllocationList: List<CampAllocationBody>?,

    @field:Schema(description = "闇鍋編成人狼配分")
    @field:Valid
    val wolfAllocation: WolfAllocationBody?,

    @field:Schema(description = "ダミーキャラ1日目発言 (400文字以内)")
    @field:Size(max = 400)
    val dummyDay1Message: String?,

    @field:Schema(description = "入村パスワード (3〜12文字、null/空なら不要)")
    val joinPassword: String?,

    @field:Schema(description = "通常発言の役職別制限一覧 (全役職分を送る)")
    @field:NotNull
    @field:Valid
    val sayRestrictList: List<SkillSayRestrictBody>?,

    @field:Schema(description = "役職発言制限一覧 (人狼の囁き等)")
    @field:NotNull
    @field:Valid
    val skillSayRestrictList: List<MessageTypeSayRestrictBody>?,

    @field:Schema(description = "RP 発言制限一覧 (アクション等)")
    @field:NotNull
    @field:Valid
    val rpSayRestrictList: List<MessageTypeSayRestrictBody>?,
) {
    /**
     * 旧 `VillageSettingForm` に変換する。`SettingFormValidator` を共有するため。
     * 表示用フィールド (campName / skillName / messageTypeName) は空のまま。
     */
    fun toForm(): VillageSettingForm = VillageSettingForm(
        villageName = villageName,
        startPersonMinNum = startPersonMinNum,
        personMaxNum = personMaxNum,
        dayChangeIntervalHours = dayChangeIntervalHours,
        dayChangeIntervalMinutes = dayChangeIntervalMinutes,
        dayChangeIntervalSeconds = dayChangeIntervalSeconds,
        startYear = startYear,
        startMonth = startMonth,
        startDay = startDay,
        startHour = startHour,
        startMinute = startMinute,
        welcomeRange = welcomeRange,
        ageLimit = ageLimit,
        openVote = openVote,
        availableSameWolfAttack = availableSameWolfAttack,
        openSkillInGrave = openSkillInGrave,
        visibleGraveSpectateMessage = visibleGraveSpectateMessage,
        allowedSecretSayCode = allowedSecretSayCode,
        availableSpectate = availableSpectate,
        availableSuddonlyDeath = availableSuddenlyDeath,
        availableCommit = availableCommit,
        availableGuardSameTarget = availableGuardSameTarget,
        availableAction = availableAction,
        organization = organization,
        randomOrganization = randomOrganization,
        reincarnationSkillAll = reincarnationSkillAll,
        campAllocationList = campAllocationList?.map { c ->
            RandomOrganizationCampForm(
                campCode = c.campCode,
                campName = null,
                minNum = c.minNum,
                maxNum = c.maxNum,
                allocation = c.allocation,
                reincarnationAllocation = c.reincarnationAllocation,
                skillAllocation = c.skillAllocation?.map { s ->
                    RandomOrganizationSkillForm(
                        skillCode = s.skillCode,
                        skillName = null,
                        minNum = s.minNum,
                        maxNum = s.maxNum,
                        allocation = s.allocation,
                        reincarnationAllocation = s.reincarnationAllocation,
                    )
                },
            )
        },
        wolfAllocation = wolfAllocation?.let {
            RandomOrganizationWolfForm(
                minNum = it.minNum,
                maxNum = it.maxNum,
            )
        },
        dummyDay1Message = dummyDay1Message,
        joinPassword = joinPassword,
        sayRestrictList = sayRestrictList?.map { r ->
            SkillSayRestrictForm(
                skillName = null,
                skillCode = r.skillCode,
                restrict = r.restrict,
                count = r.count,
                length = r.length,
            )
        },
        skillSayRestrictList = skillSayRestrictList?.map { r ->
            MessageTypeSayRestrictForm(
                messageTypeName = null,
                messageTypeCode = r.messageTypeCode,
                restrict = r.restrict,
                count = r.count,
                length = r.length,
            )
        },
        rpSayRestrictList = rpSayRestrictList?.map { r ->
            MessageTypeSayRestrictForm(
                messageTypeName = null,
                messageTypeCode = r.messageTypeCode,
                restrict = r.restrict,
                count = r.count,
                length = r.length,
            )
        },
    )

    @Schema(description = "闇鍋: 陣営配分")
    data class CampAllocationBody(
        @field:Schema(description = "陣営コード (CDef.Camp)")
        @field:NotNull
        val campCode: String?,
        @field:Schema(description = "最少人数 (0-100)")
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val minNum: Int?,
        @field:Schema(description = "最多人数 (0-100、null=制限なし)")
        @field:Min(0)
        @field:Max(100)
        val maxNum: Int?,
        @field:Schema(description = "配分 (0-100)")
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val allocation: Int?,
        @field:Schema(description = "転生配分 (0-100)")
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val reincarnationAllocation: Int?,
        @field:Schema(description = "陣営内役職配分")
        @field:NotNull
        @field:Valid
        val skillAllocation: List<SkillAllocationBody>?,
    )

    @Schema(description = "闇鍋: 役職配分")
    data class SkillAllocationBody(
        @field:Schema(description = "役職コード (CDef.Skill)")
        @field:NotNull
        val skillCode: String?,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val minNum: Int?,
        @field:Min(0)
        @field:Max(100)
        val maxNum: Int?,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val allocation: Int?,
        @field:NotNull
        @field:Min(0)
        @field:Max(100)
        val reincarnationAllocation: Int?,
    )

    @Schema(description = "闇鍋: 人狼カウント配分")
    data class WolfAllocationBody(
        @field:NotNull
        @field:Min(1)
        @field:Max(100)
        val minNum: Int?,
        @field:Min(1)
        @field:Max(100)
        val maxNum: Int?,
    )

    @Schema(description = "通常発言制限 (役職単位)")
    data class SkillSayRestrictBody(
        @field:Schema(description = "役職コード (CDef.Skill)")
        @field:NotNull
        val skillCode: String?,
        @field:Schema(description = "制限するか")
        @field:NotNull
        val restrict: Boolean?,
        @field:Schema(description = "発言回数 (restrict=true のとき必須、0-100)")
        val count: Int?,
        @field:Schema(description = "1発言文字数 (restrict=true のとき必須、0-400)")
        val length: Int?,
    )

    @Schema(description = "発言種別単位の制限 (人狼囁き / 共鳴 / 恋人 / 念話 / アクション)")
    data class MessageTypeSayRestrictBody(
        @field:Schema(description = "発言種別コード (CDef.MessageType)")
        @field:NotNull
        val messageTypeCode: String?,
        @field:NotNull
        val restrict: Boolean?,
        val count: Int?,
        val length: Int?,
    )
}
