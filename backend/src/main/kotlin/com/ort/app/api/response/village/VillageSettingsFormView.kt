package com.ort.app.api.response.village

import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 村設定編集フォーム (GET /api/v1/villages/{id}/settings/form) のレスポンス。
 *
 * - `current`: 現在の設定値 (PUT の body 形に合わせた現在値)
 * - `options`: 候補値 (役職一覧 / 陣営一覧 / 募集範囲 / 年齢制限 / 秘話可能範囲)
 *
 * フロントエンドはこの 1 リクエストで編集フォームを完全に描画できる。
 *
 * `isOriginalCharachip` と `isOriginalCharachipPasswordRequired` はオリジナルキャラチップ村で
 * パスワード必須となる UI 表示に使用する。
 */
@Schema(description = "村設定編集フォーム (creator 専用)")
data class VillageSettingsFormView(
    @field:Schema(description = "村 ID")
    val villageId: Int,
    @field:Schema(description = "現在の設定値 (PUT の body と同じ形)")
    val current: CurrentSettings,
    @field:Schema(description = "候補値")
    val options: Options,
    @field:Schema(description = "オリジナルキャラチップ村か (true なら入村パスワード必須)")
    val isOriginalCharachip: Boolean,
) {
    constructor(village: Village) : this(
        villageId = village.id,
        current = CurrentSettings(village),
        options = Options(village),
        isOriginalCharachip = village.setting.chara.isOriginalCharachip,
    )

    @Schema(description = "現在の設定値 (PUT body と同じフィールド構造)")
    data class CurrentSettings(
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
        @field:Schema(description = "募集範囲タグ (ANYONE_WELCOME/RELATIVES_ONLY)。未設定なら null。")
        val welcomeRange: String?,
        @field:Schema(description = "年齢制限タグ (R15/R18)。未設定なら null。")
        val ageLimit: String?,
        val openVote: Boolean,
        val availableSameWolfAttack: Boolean,
        val openSkillInGrave: Boolean,
        val visibleGraveSpectateMessage: Boolean,
        val allowedSecretSayCode: String,
        val availableSpectate: Boolean,
        val availableSuddenlyDeath: Boolean,
        val availableCommit: Boolean,
        val availableGuardSameTarget: Boolean,
        val availableAction: Boolean,
        val organization: String,
        val randomOrganization: Boolean,
        val reincarnationSkillAll: Boolean,
        val campAllocationList: List<CampAllocation>,
        val wolfAllocation: WolfAllocation,
        val dummyDay1Message: String?,
        @field:Schema(description = "入村パスワードが設定済みか。値そのものはレスポンスに含めない (creator のみ" +
                "とはいえ JSON 経路に平文を載せないため)。クリア / 変更は PUT body の joinPassword で行う。")
        val joinPasswordSet: Boolean,
        val sayRestrictList: List<SkillSayRestrict>,
        val skillSayRestrictList: List<MessageTypeSayRestrict>,
        val rpSayRestrictList: List<MessageTypeSayRestrict>,
    ) {
        constructor(village: Village) : this(
            villageName = village.name,
            startPersonMinNum = village.setting.personMin,
            personMaxNum = village.setting.personMax,
            dayChangeIntervalHours = village.setting.dayChangeIntervalSeconds / 3600,
            dayChangeIntervalMinutes = (village.setting.dayChangeIntervalSeconds % 3600) / 60,
            dayChangeIntervalSeconds = village.setting.dayChangeIntervalSeconds % 60,
            startYear = village.days.list.first().dayChangeDatetime.year,
            startMonth = village.days.list.first().dayChangeDatetime.monthValue,
            startDay = village.days.list.first().dayChangeDatetime.dayOfMonth,
            startHour = village.days.list.first().dayChangeDatetime.hour,
            startMinute = village.days.list.first().dayChangeDatetime.minute,
            welcomeRange = village.setting.tags.list.find {
                it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内
            }?.code,
            ageLimit = village.setting.tags.list.find {
                it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18
            }?.code,
            openVote = village.setting.rule.isOpenVote,
            availableSameWolfAttack = village.setting.rule.isAvailableSameWolfAttack,
            openSkillInGrave = village.setting.rule.isOpenSkillInGrave,
            visibleGraveSpectateMessage = village.setting.rule.isVisibleGraveSpectateMessage,
            allowedSecretSayCode = village.setting.rule.secretSayRange.code,
            availableSpectate = village.setting.rule.isAvailableSpectate,
            availableSuddenlyDeath = village.setting.rule.isAvailableSuddenlyDeath,
            availableCommit = village.setting.rule.isAvailableCommit,
            availableGuardSameTarget = village.setting.rule.isAvailableGuardSameTarget,
            availableAction = village.setting.rule.isAvailableAction,
            organization = village.setting.organize.fixedOrganization,
            randomOrganization = village.setting.rule.isRandomOrganization,
            reincarnationSkillAll = village.setting.rule.isReincarnationSkillAll,
            campAllocationList = mapCampAllocation(village),
            wolfAllocation = village.setting.organize.randomOrganization.wolfAllocation
                ?.let { WolfAllocation(minNum = it.min, maxNum = it.max) }
                ?: WolfAllocation(minNum = 1, maxNum = null),
            dummyDay1Message = village.setting.chara.dummyDay1Message,
            joinPasswordSet = !village.setting.joinPassword.isNullOrBlank(),
            sayRestrictList = mapSayRestrictList(village),
            skillSayRestrictList = mapSkillRestrictList(
                village,
                listOf(
                    CDef.MessageType.人狼の囁き,
                    CDef.MessageType.共鳴発言,
                    CDef.MessageType.恋人発言,
                    CDef.MessageType.念話,
                ),
            ),
            rpSayRestrictList = mapSkillRestrictList(
                village,
                listOf(CDef.MessageType.アクション),
            ),
        )
    }

    @Schema(description = "編集 UI で出す候補値一覧")
    data class Options(
        @field:Schema(description = "募集範囲候補 (code, name)")
        val welcomeRanges: List<CodeName>,
        @field:Schema(description = "年齢制限候補")
        val ageLimits: List<CodeName>,
        @field:Schema(description = "秘話可能範囲候補")
        val allowedSecretSays: List<CodeName>,
        @field:Schema(description = "陣営一覧 (闇鍋編成の表示用)")
        val camps: List<CodeName>,
        @field:Schema(description = "発言種別: 役職発言制限の対象一覧 (人狼の囁き/共鳴/恋人/念話)")
        val skillMessageTypes: List<CodeName>,
        @field:Schema(description = "発言種別: RP 発言制限の対象一覧 (アクション)")
        val rpMessageTypes: List<CodeName>,
        @field:Schema(description = "全役職一覧 (おまかせ除く)。通常発言制限の対象。")
        val skills: List<CodeName>,
    ) {
        constructor(village: Village) : this(
            welcomeRanges = listOf(
                CodeName(CDef.VillageTagItem.誰歓.code(), CDef.VillageTagItem.誰歓.alias()),
                CodeName(CDef.VillageTagItem.身内.code(), CDef.VillageTagItem.身内.alias()),
            ),
            ageLimits = listOf(
                CodeName(CDef.VillageTagItem.R15.code(), CDef.VillageTagItem.R15.alias()),
                CodeName(CDef.VillageTagItem.R18.code(), CDef.VillageTagItem.R18.alias()),
            ),
            allowedSecretSays = CDef.AllowedSecretSay.listAll().map { CodeName(it.code(), it.alias()) },
            camps = listOf(
                CDef.Camp.村人陣営,
                CDef.Camp.人狼陣営,
                CDef.Camp.狐陣営,
                CDef.Camp.恋人陣営,
                CDef.Camp.愉快犯陣営,
            ).map { CodeName(it.code(), it.alias()) },
            skillMessageTypes = listOf(
                CDef.MessageType.人狼の囁き,
                CDef.MessageType.共鳴発言,
                CDef.MessageType.恋人発言,
                CDef.MessageType.念話,
            ).map { CodeName(it.code(), it.alias()) },
            rpMessageTypes = listOf(CDef.MessageType.アクション).map { CodeName(it.code(), it.alias()) },
            skills = Skills.all().filterNotSomeone().list.map { CodeName(it.code, it.name) },
        )
    }

    @Schema(description = "code / name ペア")
    data class CodeName(val code: String, val name: String)

    data class CampAllocation(
        val campCode: String,
        val campName: String,
        val minNum: Int,
        val maxNum: Int?,
        val allocation: Int,
        val reincarnationAllocation: Int,
        val skillAllocation: List<SkillAllocation>,
    )

    data class SkillAllocation(
        val skillCode: String,
        val skillName: String,
        val minNum: Int,
        val maxNum: Int?,
        val allocation: Int,
        val reincarnationAllocation: Int,
    )

    data class WolfAllocation(
        val minNum: Int,
        val maxNum: Int?,
    )

    data class SkillSayRestrict(
        val skillCode: String,
        val skillName: String,
        val restrict: Boolean,
        val count: Int,
        val length: Int,
    )

    data class MessageTypeSayRestrict(
        val messageTypeCode: String,
        val messageTypeName: String,
        val restrict: Boolean,
        val count: Int,
        val length: Int,
    )

    companion object {
        private fun mapCampAllocation(village: Village): List<CampAllocation> {
            return listOf(
                CDef.Camp.村人陣営,
                CDef.Camp.人狼陣営,
                CDef.Camp.狐陣営,
                CDef.Camp.恋人陣営,
                CDef.Camp.愉快犯陣営,
            ).mapNotNull { cdefCamp ->
                // 旧データ等で当該陣営の randomOrganization 配分が欠落している場合は
                // 該当陣営を結果から落とす (実害がない範囲で 500 を回避)。
                val campAllocation = village.setting.organize.randomOrganization.campAllocation
                    .firstOrNull { it.camp.code == cdefCamp.code() }
                    ?: return@mapNotNull null
                val skillByCamp = Skills.all().filterNotSomeone().filterByCamp(cdefCamp).list
                CampAllocation(
                    campCode = campAllocation.camp.code,
                    campName = campAllocation.camp.name,
                    minNum = campAllocation.min,
                    maxNum = campAllocation.max,
                    allocation = campAllocation.initAllocation,
                    reincarnationAllocation = campAllocation.reincarnationAllocation,
                    skillAllocation = skillByCamp.mapNotNull { skill ->
                        village.setting.organize.randomOrganization.skillAllocation
                            .firstOrNull { it.skill.code == skill.code }?.let { s ->
                                SkillAllocation(
                                    skillCode = s.skill.code,
                                    skillName = s.skill.name,
                                    minNum = s.min,
                                    maxNum = s.max,
                                    allocation = s.initAllocation,
                                    reincarnationAllocation = s.reincarnationAllocation,
                                )
                            }
                    },
                )
            }
        }

        private fun mapSayRestrictList(village: Village): List<SkillSayRestrict> {
            return Skills.all().filterNotSomeone().list.map { s ->
                val restrict = village.setting.sayRestriction.normalSayRestriction
                    .find { it.skill.code == s.code }
                SkillSayRestrict(
                    skillCode = s.code,
                    skillName = s.name,
                    restrict = restrict != null,
                    count = restrict?.count ?: 20,
                    length = restrict?.length ?: 400,
                )
            }
        }

        private fun mapSkillRestrictList(
            village: Village,
            types: List<CDef.MessageType>,
        ): List<MessageTypeSayRestrict> {
            return types.map { mt ->
                val restrict = village.setting.sayRestriction.skillSayRestriction
                    .find { it.messageType.code == mt.code() }
                MessageTypeSayRestrict(
                    messageTypeCode = mt.code(),
                    messageTypeName = mt.alias(),
                    restrict = restrict != null,
                    count = restrict?.count ?: 20,
                    length = restrict?.length ?: 400,
                )
            }
        }
    }
}
