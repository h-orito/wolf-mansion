package com.ort.app.api.response.village

import com.ort.app.api.response.chara.CharachipView
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.setting.VillageOrganize
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

/**
 * 新規村作成画面 (GET /api/v1/new-village/form-defaults) のレスポンス。
 *
 * - `canCreate`: 認証ユーザが村建てできる状態か (= 進行中の自分の村が無いか)
 * - `defaults`: 初期 form 値 (`NewVillageForm.initialize()` と同等)
 * - `options`: 候補値 (公式キャラチップ / 役職一覧 / 陣営一覧 / タグ / 秘話可能範囲 / 発言種別)
 *
 * フロントエンドはこの 1 リクエストで作成フォームを初期描画できる。
 *
 * `canCreate=false` のときも options は埋めて返す (UI の説明表示用)。
 * 未認証ユーザは backend 側で 400 ("ログインが必要です") にする。
 */
@Schema(description = "新規村作成フォーム初期値 (creator)")
data class NewVillageFormView(
    @field:Schema(description = "認証ユーザが現時点で村建て可能か")
    val canCreate: Boolean,
    @field:Schema(description = "認証ユーザ名 (デバッグ補助 / canCreate の根拠表示用)")
    val userName: String,
    @field:Schema(description = "フォーム初期値")
    val defaults: NewVillageDefaults,
    @field:Schema(description = "候補値")
    val options: NewVillageOptions,
) {

    @Schema(description = "新規村作成 form の初期値")
    data class NewVillageDefaults(
        val villageName: String,
        val startPersonMinNum: Int,
        val personMaxNum: Int,
        val dayChangeIntervalHours: Int,
        val dayChangeIntervalMinutes: Int,
        val dayChangeIntervalSeconds: Int,
        val startYear: Int,
        val startMonth: Int,
        val startDay: Int,
        val startHour: Int,
        val startMinute: Int,
        val welcomeRange: String?,
        val ageLimit: String?,
        val openVote: Boolean,
        val possibleSkillRequest: Boolean,
        val availableSameWolfAttack: Boolean,
        val openSkillInGrave: Boolean,
        val visibleGraveSpectateMessage: Boolean,
        val availableSpectate: Boolean,
        val creatorIsProducer: Boolean,
        val availableSuddenlyDeath: Boolean,
        val availableCommit: Boolean,
        val availableGuardSameTarget: Boolean,
        val availableAction: Boolean,
        val randomOrganization: Boolean,
        val reincarnationSkillAll: Boolean,
        val allowedSecretSayCode: String,
        val shouldOriginalImage: Boolean,
        val characterSetId: List<Int>,
        val dummyCharaName: String,
        val dummyCharaShortName: String,
        val dummyJoinMessage: String,
        val dummyDay1Message: String?,
        val organization: String,
        val campAllocationList: List<VillageSettingsFormView.CampAllocation>,
        val wolfAllocation: VillageSettingsFormView.WolfAllocation,
        val joinPassword: String?,
        val sayRestrictList: List<VillageSettingsFormView.SkillSayRestrict>,
        val skillSayRestrictList: List<VillageSettingsFormView.MessageTypeSayRestrict>,
        val rpSayRestrictList: List<VillageSettingsFormView.MessageTypeSayRestrict>,
    ) {
        companion object {
            /**
             * 旧 `NewVillageForm.initialize()` と同等のデフォルト値を返す。
             * 開始日時は 7日後 0:00 (現地時刻)。
             */
            fun create(): NewVillageDefaults {
                val startDate = LocalDate.now().plusDays(7L)
                return NewVillageDefaults(
                    villageName = "",
                    startPersonMinNum = 8,
                    personMaxNum = 20,
                    dayChangeIntervalHours = 24,
                    dayChangeIntervalMinutes = 0,
                    dayChangeIntervalSeconds = 0,
                    startYear = startDate.year,
                    startMonth = startDate.monthValue,
                    startDay = startDate.dayOfMonth,
                    startHour = 0,
                    startMinute = 0,
                    welcomeRange = null,
                    ageLimit = null,
                    openVote = true,
                    possibleSkillRequest = true,
                    availableSameWolfAttack = true,
                    openSkillInGrave = false,
                    visibleGraveSpectateMessage = false,
                    availableSpectate = false,
                    creatorIsProducer = false,
                    availableSuddenlyDeath = false,
                    availableCommit = false,
                    availableGuardSameTarget = true,
                    availableAction = false,
                    randomOrganization = false,
                    reincarnationSkillAll = false,
                    allowedSecretSayCode = CDef.AllowedSecretSay.なし.code(),
                    shouldOriginalImage = false,
                    characterSetId = listOf(1),
                    dummyCharaName = "楽天家 ゲルト",
                    dummyCharaShortName = "楽",
                    dummyJoinMessage = "",
                    dummyDay1Message = null,
                    organization = VillageOrganize.defaultFixedOrganization,
                    campAllocationList = defaultCampAllocations(),
                    wolfAllocation = VillageSettingsFormView.WolfAllocation(minNum = 1, maxNum = null),
                    joinPassword = null,
                    sayRestrictList = defaultSayRestrictList(),
                    skillSayRestrictList = defaultMessageTypeRestrictList(
                        listOf(
                            CDef.MessageType.人狼の囁き,
                            CDef.MessageType.共鳴発言,
                            CDef.MessageType.恋人発言,
                            CDef.MessageType.念話,
                        ),
                    ),
                    rpSayRestrictList = defaultMessageTypeRestrictList(listOf(CDef.MessageType.アクション)),
                )
            }

            private fun defaultCampAllocations(): List<VillageSettingsFormView.CampAllocation> {
                return listOf(
                    CDef.Camp.村人陣営,
                    CDef.Camp.人狼陣営,
                    CDef.Camp.狐陣営,
                    CDef.Camp.恋人陣営,
                    CDef.Camp.愉快犯陣営,
                ).map { cdefCamp ->
                    val skills = Skills.all().filterNotSomeone().filterByCamp(cdefCamp).list
                    VillageSettingsFormView.CampAllocation(
                        campCode = cdefCamp.code(),
                        campName = cdefCamp.alias(),
                        minNum = 0,
                        maxNum = null,
                        allocation = 50,
                        reincarnationAllocation = 50,
                        skillAllocation = skills.map { s ->
                            VillageSettingsFormView.SkillAllocation(
                                skillCode = s.code,
                                skillName = s.name,
                                minNum = if (s.toCdef() == CDef.Skill.村人) 1 else 0,
                                maxNum = if (s.isRequestable()) null else 0,
                                allocation = 50,
                                reincarnationAllocation = if (s.isRevivable()) 50 else 0,
                            )
                        },
                    )
                }
            }

            private fun defaultSayRestrictList(): List<VillageSettingsFormView.SkillSayRestrict> {
                return Skills.all().filterNotSomeone().list.map { s ->
                    VillageSettingsFormView.SkillSayRestrict(
                        skillCode = s.code,
                        skillName = s.name,
                        restrict = false,
                        count = 20,
                        length = 400,
                    )
                }
            }

            private fun defaultMessageTypeRestrictList(
                types: List<CDef.MessageType>,
            ): List<VillageSettingsFormView.MessageTypeSayRestrict> {
                return types.map { mt ->
                    VillageSettingsFormView.MessageTypeSayRestrict(
                        messageTypeCode = mt.code(),
                        messageTypeName = mt.alias(),
                        restrict = false,
                        count = 20,
                        length = 400,
                    )
                }
            }
        }
    }

    @Schema(description = "編集 UI で出す候補値一覧 + キャラチップ")
    data class NewVillageOptions(
        @field:Schema(description = "公式キャラチップ一覧 (オリジナル選択時は使わない)")
        val charachips: List<CharachipView>,
        @field:Schema(description = "募集範囲候補")
        val welcomeRanges: List<VillageSettingsFormView.CodeName>,
        @field:Schema(description = "年齢制限候補")
        val ageLimits: List<VillageSettingsFormView.CodeName>,
        @field:Schema(description = "秘話可能範囲候補")
        val allowedSecretSays: List<VillageSettingsFormView.CodeName>,
        @field:Schema(description = "陣営一覧 (闇鍋編成 UI 用)")
        val camps: List<VillageSettingsFormView.CodeName>,
        @field:Schema(description = "発言種別: 役職発言制限の対象一覧")
        val skillMessageTypes: List<VillageSettingsFormView.CodeName>,
        @field:Schema(description = "発言種別: RP 発言制限の対象一覧")
        val rpMessageTypes: List<VillageSettingsFormView.CodeName>,
        @field:Schema(description = "全役職一覧 (おまかせ除く)。通常発言制限の対象。")
        val skills: List<VillageSettingsFormView.CodeName>,
    ) {
        constructor(charachips: Charachips) : this(
            charachips = charachips.list.map { CharachipView(it) },
            welcomeRanges = listOf(
                VillageSettingsFormView.CodeName(CDef.VillageTagItem.誰歓.code(), CDef.VillageTagItem.誰歓.alias()),
                VillageSettingsFormView.CodeName(CDef.VillageTagItem.身内.code(), CDef.VillageTagItem.身内.alias()),
            ),
            ageLimits = listOf(
                VillageSettingsFormView.CodeName(CDef.VillageTagItem.R15.code(), CDef.VillageTagItem.R15.alias()),
                VillageSettingsFormView.CodeName(CDef.VillageTagItem.R18.code(), CDef.VillageTagItem.R18.alias()),
            ),
            allowedSecretSays = CDef.AllowedSecretSay.listAll().map {
                VillageSettingsFormView.CodeName(it.code(), it.alias())
            },
            camps = listOf(
                CDef.Camp.村人陣営,
                CDef.Camp.人狼陣営,
                CDef.Camp.狐陣営,
                CDef.Camp.恋人陣営,
                CDef.Camp.愉快犯陣営,
            ).map { VillageSettingsFormView.CodeName(it.code(), it.alias()) },
            skillMessageTypes = listOf(
                CDef.MessageType.人狼の囁き,
                CDef.MessageType.共鳴発言,
                CDef.MessageType.恋人発言,
                CDef.MessageType.念話,
            ).map { VillageSettingsFormView.CodeName(it.code(), it.alias()) },
            rpMessageTypes = listOf(CDef.MessageType.アクション).map {
                VillageSettingsFormView.CodeName(it.code(), it.alias())
            },
            skills = Skills.all().filterNotSomeone().list.map {
                VillageSettingsFormView.CodeName(it.code, it.name)
            },
        )
    }
}
