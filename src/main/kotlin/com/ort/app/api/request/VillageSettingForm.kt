package com.ort.app.api.request

import com.ort.app.api.request.setting.MessageTypeSayRestrictForm
import com.ort.app.api.request.setting.RandomOrganizationCampForm
import com.ort.app.api.request.setting.RandomOrganizationSkillForm
import com.ort.app.api.request.setting.SkillSayRestrictForm
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class VillageSettingForm(
    /** 最低開始人数 */
    @field:NotNull
    @field:Min(8)
    var startPersonMinNum: Int? = null,

    /** 定員 */
    @field:NotNull
    @field:Max(99)
    var personMaxNum: Int? = null,

    /** 更新間隔時間 */
    @field:Min(0)
    @field:Max(48)
    var dayChangeIntervalHours: Int? = null,

    /** 更新間隔分 */
    @field:Min(0)
    @field:Max(59)
    var dayChangeIntervalMinutes: Int? = null,

    /** 更新間隔秒 */
    @field:Min(0)
    @field:Max(59)
    var dayChangeIntervalSeconds: Int? = null,

    /** 開始年 */
    @field:Min(0)
    var startYear: Int? = null,

    /** 開始月 */
    @field:Min(1)
    @field:Max(12)
    var startMonth: Int? = null,

    /** 開始日 */
    @field:Min(1)
    @field:Max(31)
    var startDay: Int? = null,

    /** 開始時間 */
    @field:Min(0)
    @field:Max(23)
    var startHour: Int? = null,

    /** 開始分 */
    @field:Min(0)
    @field:Max(59)
    var startMinute: Int? = null,

    /** 記名投票か */
    @field:NotNull
    var openVote: Boolean? = null,

    /** 連続襲撃ありか */
    @field:NotNull
    var availableSameWolfAttack: Boolean? = null,

    /** 墓下役職公開ありか */
    @field:NotNull
    var openSkillInGrave: Boolean? = null,

    /** 墓下見学発言を地上から見られるか */
    @field:NotNull
    var visibleGraveSpectateMessage: Boolean? = null,

    /** 秘話可能範囲 */
    @field:NotNull
    var allowedSecretSayCode: String? = null,

    /** 観戦を可能にする */
    @field:NotNull
    var availableSpectate: Boolean? = null,

    /** 突然死ありか */
    @field:NotNull
    var availableSuddonlyDeath: Boolean? = null,

    /** コミット可能か */
    @field:NotNull
    var availableCommit: Boolean? = null,

    /** 連続ガードありか */
    @field:NotNull
    var availableGuardSameTarget: Boolean? = null,

    /** アクションありか */
    @field:NotNull
    var availableAction: Boolean? = null,

    /** 構成 */
    var organization: String? = null,

    /** 闇鍋か */
    @field:NotNull
    var randomOrganization: Boolean? = null,

    /** 闇鍋編成詳細 */
    @field:Valid
    var campAllocationList: List<RandomOrganizationCampForm>? = null,

    /** 入村パスワード */
    var joinPassword: String? = null,

    /** 発言制限 */
    @field:NotNull
    @Valid
    var sayRestrictList: List<SkillSayRestrictForm>? = null,

    /** 役職発言制限 */
    @field:NotNull
    @Valid
    var skillSayRestrictList: List<MessageTypeSayRestrictForm>? = null,

    /** RP発言制限 */
    @field:NotNull
    @Valid
    var rpSayRestrictList: List<MessageTypeSayRestrictForm>? = null
) {
    constructor(village: Village) : this(
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
        openVote = village.setting.rule.isOpenVote,
        availableSameWolfAttack = village.setting.rule.isAvailableSameWolfAttack,
        openSkillInGrave = village.setting.rule.isOpenSkillInGrave,
        visibleGraveSpectateMessage = village.setting.rule.isVisibleGraveSpectateMessage,
        allowedSecretSayCode = if (village.setting.rule.isAvailableSecretSay) "EVERYTHING" else "NOTHING",
        availableSpectate = village.setting.rule.isAvailableSpectate,
        availableSuddonlyDeath = village.setting.rule.isAvailableSuddenlyDeath,
        availableCommit = village.setting.rule.isAvailableCommit,
        availableGuardSameTarget = village.setting.rule.isAvailableGuardSameTarget,
        availableAction = village.setting.rule.isAvailableAction,
        organization = village.setting.organize.fixedOrganization,
        randomOrganization = village.setting.rule.isRandomOrganization,
        campAllocationList = village.setting.organize.randomOrganization.campAllocation.map {
            RandomOrganizationCampForm(
                campCode = it.camp.code,
                campName = it.camp.name,
                minNum = it.min,
                maxNum = it.max,
                allocation = it.allocation,
                skillAllocation = village.setting.organize.randomOrganization.skillAllocation.filter { s ->
                    s.skill.camp().code == it.camp.code
                }.map { s ->
                    RandomOrganizationSkillForm(
                        skillCode = s.skill.code,
                        skillName = s.skill.name,
                        minNum = s.min,
                        maxNum = s.max,
                        allocation = s.allocation
                    )
                }
            )
        },
        joinPassword = village.setting.joinPassword,
        sayRestrictList = Skills.all().filterNotSomeone().list.map { s ->
            val restrict = village.setting.sayRestriction.normalSayRestriction.find { it.skill.code == s.code }
            SkillSayRestrictForm(
                skillCode = s.code,
                skillName = s.name,
                restrict = restrict != null,
                count = restrict?.count ?: 20,
                length = restrict?.length ?: 400
            )
        },
        skillSayRestrictList = listOf(
            MessageType(CDef.MessageType.人狼の囁き),
            MessageType(CDef.MessageType.共鳴発言),
            MessageType(CDef.MessageType.恋人発言)
        ).map { mt ->
            val restrict = village.setting.sayRestriction.skillSayRestriction.find { it.messageType.code == mt.code }
            MessageTypeSayRestrictForm(
                messageTypeName = mt.name,
                messageTypeCode = mt.code,
                restrict = restrict != null,
                count = restrict?.count ?: 20,
                length = restrict?.length ?: 400
            )
        },
        rpSayRestrictList = listOf(MessageType(CDef.MessageType.アクション)).map { mt ->
            val restrict = village.setting.sayRestriction.skillSayRestriction.find { it.messageType.code == mt.code }
            MessageTypeSayRestrictForm(
                messageTypeName = mt.name,
                messageTypeCode = mt.code,
                restrict = restrict != null,
                count = restrict?.count ?: 20,
                length = restrict?.length ?: 400
            )
        }
    )
}