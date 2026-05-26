package com.ort.app.api.response.village

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村設定 (read-only)")
data class VillageSettingsView(
    @field:Schema(description = "最小人数")
    val personMin: Int,
    @field:Schema(description = "最大人数")
    val personMax: Int,
    @field:Schema(description = "プロローグの開始予定日時")
    val startDatetime: LocalDateTime,
    @field:Schema(description = "日付更新の間隔 (秒)")
    val dayChangeIntervalSeconds: Int,
    @field:Schema(description = "入村パスワードの有無")
    val joinPasswordRequired: Boolean,
    @field:Schema(description = "オリジナルキャラチップの村か")
    val isOriginalCharachip: Boolean,
    @field:Schema(description = "村が参照しているキャラチップ ID 一覧 (selectableCharas エンドポイント呼出に使用)")
    val charachipIds: List<Int>,
    @field:Schema(description = "希望役職指定が可能な村か")
    val isSkillRequestAvailable: Boolean,
    @field:Schema(description = "見学参加が可能な村か")
    val isSpectateAvailable: Boolean,
    @field:Schema(description = "募集範囲タグ名 (誰歓/身内)。未設定なら null。")
    val welcomeRangeName: String?,
    @field:Schema(description = "年齢制限タグ名 (R15/R18)。未設定なら null。")
    val ageLimitName: String?,
    @field:Schema(description = "投票形式 (公開投票か無記名投票か)")
    val voteTypeName: String,
    @field:Schema(description = "プロデューサー機能の有無")
    val creatorIsProducer: Boolean,
    @field:Schema(description = "同一人狼連続襲撃の可否")
    val availableSameWolfAttack: Boolean,
    @field:Schema(description = "狩人による連続護衛の可否")
    val availableGuardSameTarget: Boolean,
    @field:Schema(description = "突然死の有無")
    val availableSuddenlyDeath: Boolean,
    @field:Schema(description = "コミット機能の有無")
    val availableCommit: Boolean,
    @field:Schema(description = "アクション発言の可否")
    val availableAction: Boolean,
    @field:Schema(description = "墓下見学役職を公開するか")
    val openSkillInGrave: Boolean,
    @field:Schema(description = "墓下見学と地上の会話可否")
    val visibleGraveSpectateMessage: Boolean,
    @field:Schema(description = "秘話可能範囲 (NOTHING / ONLY_CREATOR / EVERYONE)")
    val allowedSecretSayCode: String,
    @field:Schema(description = "秘話可能範囲の表示名")
    val allowedSecretSayName: String,
    @field:Schema(description = "転生時の役職候補が全役職か (false なら編成に含まれるもののみ)")
    val reincarnationSkillAll: Boolean,
    @field:Schema(description = "ランダム編成 (闇鍋) か")
    val isRandomOrganization: Boolean,
    @field:Schema(description = "役職構成 (固定編成のテキスト表現)。ランダム編成なら空文字。")
    val organization: String,
    @field:Schema(description = "ダミーキャラ ID")
    val dummyCharaId: Int,
    @field:Schema(description = "ダミーキャラ名 (= 1日目発言主)")
    val dummyCharaName: String,
    @field:Schema(description = "村のキャラチップ一覧 (`isOriginalCharachip=false` のときのみ外部リンク用に id を使う)")
    val charachips: List<CharachipSummaryView>,
    @field:Schema(description = "通常発言の役職別制限 (制限がかかっている役職のみ。空ならすべて無制限)")
    val sayRestrictList: List<SkillSayRestrictView>,
    @field:Schema(description = "役職発言 (人狼の囁き/共鳴/恋人/念話) の発言種別別制限 (制限がかかっているもののみ)")
    val skillSayRestrictList: List<MessageTypeSayRestrictView>,
    @field:Schema(description = "RP 発言 (アクション) の発言種別別制限")
    val rpSayRestrictList: List<MessageTypeSayRestrictView>,
) {
    constructor(village: Village, charachips: Charachips) : this(
        personMin = village.setting.personMin,
        personMax = village.setting.personMax,
        startDatetime = village.setting.startDatetime,
        dayChangeIntervalSeconds = village.setting.dayChangeIntervalSeconds,
        joinPasswordRequired = !village.setting.joinPassword.isNullOrEmpty(),
        isOriginalCharachip = village.setting.chara.isOriginalCharachip,
        charachipIds = village.setting.chara.charachipIds,
        isSkillRequestAvailable = village.setting.rule.isPossibleSkillRequest,
        isSpectateAvailable = village.setting.rule.isAvailableSpectate,
        welcomeRangeName = village.setting.tags.list.find {
            it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内
        }?.name,
        ageLimitName = village.setting.tags.list.find {
            it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18
        }?.name,
        voteTypeName = if (village.setting.rule.isOpenVote) "記名投票" else "無記名投票",
        creatorIsProducer = village.setting.rule.isCreatorIsProducer,
        availableSameWolfAttack = village.setting.rule.isAvailableSameWolfAttack,
        availableGuardSameTarget = village.setting.rule.isAvailableGuardSameTarget,
        availableSuddenlyDeath = village.setting.rule.isAvailableSuddenlyDeath,
        availableCommit = village.setting.rule.isAvailableCommit,
        availableAction = village.setting.rule.isAvailableAction,
        openSkillInGrave = village.setting.rule.isOpenSkillInGrave,
        visibleGraveSpectateMessage = village.setting.rule.isVisibleGraveSpectateMessage,
        allowedSecretSayCode = village.setting.rule.secretSayRange.code,
        allowedSecretSayName = village.setting.rule.secretSayRange.name,
        reincarnationSkillAll = village.setting.rule.isReincarnationSkillAll,
        isRandomOrganization = village.setting.rule.isRandomOrganization,
        organization = village.setting.organize.fixedOrganization,
        dummyCharaId = village.setting.chara.dummyCharaId,
        dummyCharaName = charachips.charas().list.firstOrNull { it.id == village.setting.chara.dummyCharaId }?.name
            ?: "?",
        charachips = charachips.list.map { CharachipSummaryView(it.id, it.name) },
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

    @Schema(description = "キャラチップの id + 表示名 (`isOriginalCharachip=false` のときのみクライアントは外部リンクを作る)")
    data class CharachipSummaryView(val id: Int, val name: String)

    @Schema(description = "通常発言の役職別制限。制限がかかっている役職のみリストに含む")
    data class SkillSayRestrictView(
        val skillCode: String,
        val skillName: String,
        val count: Int,
        val length: Int,
    )

    @Schema(description = "発言種別別の発言制限。制限がかかっている発言種別のみリストに含む")
    data class MessageTypeSayRestrictView(
        val messageTypeCode: String,
        val messageTypeName: String,
        val count: Int,
        val length: Int,
    )

    companion object {
        private fun mapSayRestrictList(village: Village): List<SkillSayRestrictView> {
            // 旧 modal-village-info.html は「制限がかかっている役職のみ表示」だったため
            // ここでも `normalSayRestriction` に登録されているものだけを返す。
            return village.setting.sayRestriction.normalSayRestriction.mapNotNull { restrict ->
                val skill = Skills.all().filterNotSomeone().list.find { it.code == restrict.skill.code }
                    ?: return@mapNotNull null
                SkillSayRestrictView(
                    skillCode = skill.code,
                    skillName = skill.name,
                    count = restrict.count,
                    length = restrict.length,
                )
            }
        }

        private fun mapSkillRestrictList(
            village: Village,
            types: List<CDef.MessageType>,
        ): List<MessageTypeSayRestrictView> {
            return types.mapNotNull { mt ->
                val restrict = village.setting.sayRestriction.skillSayRestriction
                    .find { it.messageType.code == mt.code() }
                    ?: return@mapNotNull null
                MessageTypeSayRestrictView(
                    messageTypeCode = mt.code(),
                    messageTypeName = mt.alias(),
                    count = restrict.count,
                    length = restrict.length,
                )
            }
        }
    }
}
