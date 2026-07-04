package com.ort.app.api.request

import com.ort.app.api.request.setting.*
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.setting.SayRestriction
import com.ort.app.domain.model.village.setting.SecretSayRange
import com.ort.app.domain.model.village.setting.VillageOrganize
import com.ort.app.domain.model.village.setting.VillageRandomOrganize
import com.ort.app.domain.model.village.setting.VillageTags
import com.ort.app.domain.model.village.setting.toModel
import com.ort.dbflute.allcommon.CDef
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime

data class VillageSettingForm(
    /** 村表示名 */
    @field:NotNull
    @field:Length(min = 5, max = 40)
    var villageName: String? = null,
    /** 最低開始人数 */
    @field:NotNull
    @field:Min(8)
    var startPersonMinNum: Int? = null,
    /** 定員 */
    @field:NotNull
    @field:Max(999)
    var personMaxNum: Int? = null,
    /** 更新間隔時間 */
    @field:Min(0)
    @field:Max(72)
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
    /** 募集範囲 */
    var welcomeRange: String? = null,
    /** 年齢制限 */
    var ageLimit: String? = null,
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
    /** 転生時に全役職を候補とするか */
    @field:NotNull
    var reincarnationSkillAll: Boolean? = null,
    /** 闇鍋編成詳細 */
    @field:Valid
    var campAllocationList: List<RandomOrganizationCampForm>? = null,
    /** 闇鍋編成人狼配分 */
    @field:Valid
    var wolfAllocation: RandomOrganizationWolfForm? = null,
    /** ダミーキャラ1日目発言 */
    @field:Length(max = 400)
    var dummyDay1Message: String? = null,
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
    var rpSayRestrictList: List<MessageTypeSayRestrictForm>? = null,
) {
    constructor(village: Village) : this(
        villageName = village.name,
        startPersonMinNum = village.setting.personMin,
        personMaxNum = village.setting.personMax,
        dayChangeIntervalHours = village.setting.dayChangeIntervalSeconds / 3600,
        dayChangeIntervalMinutes = (village.setting.dayChangeIntervalSeconds % 3600) / 60,
        dayChangeIntervalSeconds = village.setting.dayChangeIntervalSeconds % 60,
        startYear =
            village.days.list
                .first()
                .dayChangeDatetime.year,
        startMonth =
            village.days.list
                .first()
                .dayChangeDatetime.monthValue,
        startDay =
            village.days.list
                .first()
                .dayChangeDatetime.dayOfMonth,
        startHour =
            village.days.list
                .first()
                .dayChangeDatetime.hour,
        startMinute =
            village.days.list
                .first()
                .dayChangeDatetime.minute,
        welcomeRange =
            village.setting.tags.list
                .find {
                    it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内
                }?.code,
        ageLimit =
            village.setting.tags.list
                .find {
                    it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18
                }?.code,
        openVote = village.setting.rule.isOpenVote,
        availableSameWolfAttack = village.setting.rule.isAvailableSameWolfAttack,
        openSkillInGrave = village.setting.rule.isOpenSkillInGrave,
        visibleGraveSpectateMessage = village.setting.rule.isVisibleGraveSpectateMessage,
        allowedSecretSayCode = village.setting.rule.secretSayRange.code,
        availableSpectate = village.setting.rule.isAvailableSpectate,
        availableSuddonlyDeath = village.setting.rule.isAvailableSuddenlyDeath,
        availableCommit = village.setting.rule.isAvailableCommit,
        availableGuardSameTarget = village.setting.rule.isAvailableGuardSameTarget,
        availableAction = village.setting.rule.isAvailableAction,
        organization = village.setting.organize.fixedOrganization,
        randomOrganization = village.setting.rule.isRandomOrganization,
        reincarnationSkillAll = village.setting.rule.isReincarnationSkillAll,
        campAllocationList =
            listOf(
                CDef.Camp.村人陣営,
                CDef.Camp.人狼陣営,
                CDef.Camp.狐陣営,
                CDef.Camp.恋人陣営,
                CDef.Camp.愉快犯陣営,
            ).mapNotNull { cdefCamp ->
                // 通常編成の村は陣営配分を持たないことがある。無い陣営は返さず、
                // frontend 側のデフォルト値補完に任せる
                val campAllocation =
                    village.setting.organize.randomOrganization.campAllocation
                        .firstOrNull { it.camp.code == cdefCamp.code() }
                        ?: return@mapNotNull null
                RandomOrganizationCampForm(
                    campCode = campAllocation.camp.code,
                    campName = campAllocation.camp.name,
                    minNum = campAllocation.min,
                    maxNum = campAllocation.max,
                    allocation = campAllocation.initAllocation,
                    reincarnationAllocation = campAllocation.reincarnationAllocation,
                    skillAllocation =
                        Skills
                            .all()
                            .filterNotSomeone()
                            .filterByCamp(cdefCamp)
                            .list
                            .mapNotNull { skill ->
                                village.setting.organize.randomOrganization.skillAllocation
                                    .firstOrNull { it.skill.code == skill.code }
                                    ?.let { s ->
                                        RandomOrganizationSkillForm(
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
            },
        wolfAllocation =
            village.setting.organize.randomOrganization.wolfAllocation?.let {
                RandomOrganizationWolfForm(
                    minNum = it.min,
                    maxNum = it.max,
                )
            } ?: RandomOrganizationWolfForm(),
        dummyDay1Message = village.setting.chara.dummyDay1Message,
        joinPassword = village.setting.joinPassword,
        sayRestrictList =
            Skills.all().filterNotSomeone().list.map { s ->
                val restrict =
                    village.setting.sayRestriction.normalSayRestriction
                        .find { it.skill.code == s.code }
                SkillSayRestrictForm(
                    skillCode = s.code,
                    skillName = s.name,
                    restrict = restrict != null,
                    count = restrict?.count ?: 20,
                    length = restrict?.length ?: 400,
                )
            },
        skillSayRestrictList =
            listOf(
                MessageType(CDef.MessageType.人狼の囁き),
                MessageType(CDef.MessageType.共鳴発言),
                MessageType(CDef.MessageType.恋人発言),
                MessageType(CDef.MessageType.念話),
            ).map { mt ->
                val restrict =
                    village.setting.sayRestriction.skillSayRestriction
                        .find { it.messageType.code == mt.code }
                MessageTypeSayRestrictForm(
                    messageTypeName = mt.name,
                    messageTypeCode = mt.code,
                    restrict = restrict != null,
                    count = restrict?.count ?: 20,
                    length = restrict?.length ?: 400,
                )
            },
        rpSayRestrictList =
            listOf(MessageType(CDef.MessageType.アクション)).map { mt ->
                val restrict =
                    village.setting.sayRestriction.skillSayRestriction
                        .find { it.messageType.code == mt.code }
                MessageTypeSayRestrictForm(
                    messageTypeName = mt.name,
                    messageTypeCode = mt.code,
                    restrict = restrict != null,
                    count = restrict?.count ?: 20,
                    length = restrict?.length ?: 400,
                )
            },
    )

    fun mergeTo(village: Village): Village {
        val startDatetime =
            LocalDateTime.of(
                startYear!!,
                startMonth!!,
                startDay!!,
                startHour!!,
                startMinute!!,
            )
        val welcome =
            if (welcomeRange.isNullOrBlank()) {
                emptyList()
            } else {
                listOf(CDef.VillageTagItem.codeOf(welcomeRange).toModel())
            }
        val age =
            if (ageLimit.isNullOrBlank()) {
                emptyList()
            } else {
                listOf(CDef.VillageTagItem.codeOf(ageLimit).toModel())
            }
        return village.copy(
            name = villageName!!,
            days =
                village.days.copy(
                    list =
                        village.days.list.map {
                            if (it.day == 0) {
                                it.copy(dayChangeDatetime = startDatetime)
                            } else {
                                it.copy()
                            }
                        },
                ),
            setting =
                village.setting.copy(
                    chara =
                        village.setting.chara.copy(
                            dummyDay1Message = dummyDay1Message,
                        ),
                    personMin = startPersonMinNum!!,
                    personMax = personMaxNum!!,
                    dayChangeIntervalSeconds =
                        dayChangeIntervalHours!! * 3600 + dayChangeIntervalMinutes!! * 60 + dayChangeIntervalSeconds!!,
                    startDatetime = startDatetime,
                    rule =
                        village.setting.rule.copy(
                            isOpenVote = openVote!!,
                            isAvailableSameWolfAttack = availableSameWolfAttack!!,
                            isOpenSkillInGrave = openSkillInGrave!!,
                            isVisibleGraveSpectateMessage = visibleGraveSpectateMessage!!,
                            isAvailableSpectate = availableSpectate!!,
                            isAvailableSuddenlyDeath = availableSuddonlyDeath!!,
                            isAvailableCommit = availableCommit!!,
                            isAvailableGuardSameTarget = availableGuardSameTarget!!,
                            isAvailableAction = availableAction!!,
                            isRandomOrganization = randomOrganization!!,
                            isReincarnationSkillAll = reincarnationSkillAll!!,
                            secretSayRange =
                                SecretSayRange(
                                    CDef.AllowedSecretSay.codeOf(allowedSecretSayCode!!),
                                ),
                        ),
                    organize =
                        VillageOrganize(
                            fixedOrganization = organization.orEmpty(),
                            randomOrganization =
                                VillageRandomOrganize(
                                    campAllocation =
                                        campAllocationList?.map {
                                            VillageRandomOrganize.CampAllocation(
                                                camp = Camp(CDef.Camp.codeOf(it.campCode)),
                                                min = it.minNum!!,
                                                max = it.maxNum,
                                                initAllocation = it.allocation!!,
                                                reincarnationAllocation = it.reincarnationAllocation!!,
                                            )
                                        } ?: emptyList(),
                                    skillAllocation =
                                        campAllocationList?.flatMap { it.skillAllocation!! }?.map {
                                            VillageRandomOrganize.SkillAllocation(
                                                skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                                                min = it.minNum!!,
                                                max = it.maxNum,
                                                initAllocation = it.allocation!!,
                                                reincarnationAllocation = it.reincarnationAllocation!!,
                                            )
                                        } ?: emptyList(),
                                    wolfAllocation =
                                        wolfAllocation?.let {
                                            VillageRandomOrganize.WolfAllocation(
                                                min = it.minNum!!,
                                                max = it.maxNum,
                                            )
                                        },
                                ),
                        ),
                    joinPassword = joinPassword,
                    sayRestriction =
                        SayRestriction(
                            normalSayRestriction =
                                sayRestrictList!!.filter { it.restrict!! }.map {
                                    SayRestriction.NormalSayRestriction(
                                        skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                                        messageType = MessageType(CDef.MessageType.通常発言),
                                        count = it.count!!,
                                        length = it.length!!,
                                    )
                                },
                            skillSayRestriction =
                                (skillSayRestrictList!! + rpSayRestrictList!!)
                                    .filter { it.restrict!! }
                                    .map {
                                        SayRestriction.SkillSayRestriction(
                                            messageType = MessageType(CDef.MessageType.codeOf(it.messageTypeCode)),
                                            count = it.count!!,
                                            length = it.length!!,
                                        )
                                    },
                        ),
                    tags = VillageTags(list = welcome + age),
                ),
        )
    }
}
