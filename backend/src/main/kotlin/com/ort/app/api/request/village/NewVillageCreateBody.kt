package com.ort.app.api.request.village

import com.ort.app.api.request.NewVillageForm
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
 * 新規村作成 (POST /api/v1/villages) リクエスト body。
 *
 * 旧 `NewVillageForm` (Thymeleaf form) の JSON 受信用スリム版。
 * 設定変更 (`VillageSettingsUpdateBody`) と多くのフィールドが共通だが、新規村作成固有の
 * フィールド (役職希望可否 / プロデューサー機能 / キャラチップ選択 / ダミーキャラ情報) を含む。
 * `VillageSettingsUpdateBody` の nested DTO (CampAllocationBody / SkillAllocationBody /
 * WolfAllocationBody / SkillSayRestrictBody / MessageTypeSayRestrictBody) を再利用する。
 *
 * `shouldOriginalImage=true` (オリジナルキャラチップ村) の場合は multipart endpoint
 * (`POST /api/v1/villages`, `consumes=multipart/form-data`) で `body` part として送られ、
 * 同時に `dummyCharaImage` (画像 file part) を要求する。JSON endpoint に
 * `shouldOriginalImage=true` を送ると 400 (multipart endpoint へ案内)。
 */
@Schema(description = "新規村作成リクエスト body (creator)")
data class NewVillageCreateBody(
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
    @field:NotNull @field:Min(0) @field:Max(72)
    val dayChangeIntervalHours: Int?,

    @field:Schema(description = "更新間隔: 分 (0-59)")
    @field:NotNull @field:Min(0) @field:Max(59)
    val dayChangeIntervalMinutes: Int?,

    @field:Schema(description = "更新間隔: 秒 (0-59)")
    @field:NotNull @field:Min(0) @field:Max(59)
    val dayChangeIntervalSeconds: Int?,

    @field:Schema(description = "開始年")
    @field:NotNull @field:Min(0)
    val startYear: Int?,

    @field:Schema(description = "開始月 (1-12)")
    @field:NotNull @field:Min(1) @field:Max(12)
    val startMonth: Int?,

    @field:Schema(description = "開始日 (1-31)")
    @field:NotNull @field:Min(1) @field:Max(31)
    val startDay: Int?,

    @field:Schema(description = "開始時 (0-23)")
    @field:NotNull @field:Min(0) @field:Max(23)
    val startHour: Int?,

    @field:Schema(description = "開始分 (0-59)")
    @field:NotNull @field:Min(0) @field:Max(59)
    val startMinute: Int?,

    @field:Schema(description = "募集範囲タグ (ANYONE_WELCOME / RELATIVES_ONLY)")
    val welcomeRange: String?,

    @field:Schema(description = "年齢制限タグ (R15 / R18)")
    val ageLimit: String?,

    @field:Schema(description = "記名投票か")
    @field:NotNull val openVote: Boolean?,
    @field:Schema(description = "役職希望を有効にするか")
    @field:NotNull val possibleSkillRequest: Boolean?,
    @field:Schema(description = "連続襲撃ありか")
    @field:NotNull val availableSameWolfAttack: Boolean?,
    @field:Schema(description = "墓下役職公開ありか")
    @field:NotNull val openSkillInGrave: Boolean?,
    @field:Schema(description = "墓下見学発言を地上から見られるか")
    @field:NotNull val visibleGraveSpectateMessage: Boolean?,
    @field:Schema(description = "見学可能か")
    @field:NotNull val availableSpectate: Boolean?,
    @field:Schema(description = "村建てがプロデューサー機能を持つか")
    @field:NotNull val creatorIsProducer: Boolean?,
    @field:Schema(description = "突然死ありか")
    @field:NotNull val availableSuddenlyDeath: Boolean?,
    @field:Schema(description = "コミット可能か")
    @field:NotNull val availableCommit: Boolean?,
    @field:Schema(description = "連続ガードありか")
    @field:NotNull val availableGuardSameTarget: Boolean?,
    @field:Schema(description = "アクションありか")
    @field:NotNull val availableAction: Boolean?,
    @field:Schema(description = "闇鍋編成か")
    @field:NotNull val randomOrganization: Boolean?,
    @field:Schema(description = "転生時に全役職を候補とするか")
    @field:NotNull val reincarnationSkillAll: Boolean?,

    @field:Schema(description = "秘話可能範囲 (CDef.AllowedSecretSay code)")
    @field:NotNull val allowedSecretSayCode: String?,

    @field:Schema(description = "オリジナル画像を使用するか。true の場合は multipart endpoint (`dummyCharaImage` part 必須) を使用する。")
    @field:NotNull val shouldOriginalImage: Boolean?,

    @field:Schema(description = "公式キャラチップ ID 一覧 (shouldOriginalImage=false のとき必須)")
    val characterSetId: List<Int>?,

    @field:Schema(description = "ダミーキャラ ID (shouldOriginalImage=false のとき必須)")
    val dummyCharaId: Int?,

    @field:Schema(description = "ダミーキャラ表示名 (1〜40文字)")
    @field:NotNull
    @field:Size(min = 1, max = 40)
    val dummyCharaName: String?,

    @field:Schema(description = "ダミーキャラ略称 (1文字)")
    @field:NotNull
    @field:Size(min = 1, max = 1)
    val dummyCharaShortName: String?,

    @field:Schema(description = "ダミーキャラ入村発言 (1〜400文字)")
    @field:NotNull
    @field:Size(min = 1, max = 400)
    val dummyJoinMessage: String?,

    @field:Schema(description = "ダミーキャラ1日目発言 (任意、最大400文字)")
    @field:Size(max = 400)
    val dummyDay1Message: String?,

    @field:Schema(description = "構成 (改行区切り、闇鍋でないときに参照)")
    val organization: String?,

    @field:Schema(description = "闇鍋編成詳細")
    @field:Valid
    val campAllocationList: List<VillageSettingsUpdateBody.CampAllocationBody>?,

    @field:Schema(description = "闇鍋編成: 人狼カウント配分")
    @field:Valid
    val wolfAllocation: VillageSettingsUpdateBody.WolfAllocationBody?,

    @field:Schema(description = "入村パスワード (3〜12文字、未設定なら null/空)")
    val joinPassword: String?,

    @field:Schema(description = "通常発言の役職別制限一覧 (全役職分)")
    @field:NotNull
    @field:Valid
    val sayRestrictList: List<VillageSettingsUpdateBody.SkillSayRestrictBody>?,

    @field:Schema(description = "役職発言制限一覧 (人狼の囁き等)")
    @field:NotNull
    @field:Valid
    val skillSayRestrictList: List<VillageSettingsUpdateBody.MessageTypeSayRestrictBody>?,

    @field:Schema(description = "RP 発言制限一覧 (アクション等)")
    @field:NotNull
    @field:Valid
    val rpSayRestrictList: List<VillageSettingsUpdateBody.MessageTypeSayRestrictBody>?,
) {
    /**
     * 旧 `NewVillageForm` に変換する。`NewVillageFormValidator` を共有するため。
     * 表示用フィールド (campName / skillName / messageTypeName) は空。
     */
    fun toForm(): NewVillageForm = NewVillageForm(
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
        possibleSkillRequest = possibleSkillRequest,
        availableSameWolfAttack = availableSameWolfAttack,
        openSkillInGrave = openSkillInGrave,
        visibleGraveSpectateMessage = visibleGraveSpectateMessage,
        shouldOriginalImage = shouldOriginalImage,
        characterSetId = characterSetId,
        dummyCharaId = dummyCharaId,
        dummyCharaImageFile = null,
        dummyCharaName = dummyCharaName,
        dummyCharaShortName = dummyCharaShortName,
        dummyJoinMessage = dummyJoinMessage,
        dummyDay1Message = dummyDay1Message,
        joinPassword = joinPassword,
        availableSpectate = availableSpectate,
        creatorIsProducer = creatorIsProducer,
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
            RandomOrganizationWolfForm(minNum = it.minNum, maxNum = it.maxNum)
        },
        allowedSecretSayCode = allowedSecretSayCode,
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
}
