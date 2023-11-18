package com.ort.app.api.request

import com.ort.app.api.request.setting.MessageTypeSayRestrictForm
import com.ort.app.api.request.setting.RandomOrganizationCampForm
import com.ort.app.api.request.setting.RandomOrganizationWolfForm
import com.ort.app.api.request.setting.SkillSayRestrictForm
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.*
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.setting.*
import com.ort.dbflute.allcommon.CDef
import org.hibernate.validator.constraints.Length
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class NewVillageForm(
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

    /** 役職希望有効か */
    @field:NotNull
    var possibleSkillRequest: Boolean? = null,

    /** 連続襲撃ありか */
    @field:NotNull
    var availableSameWolfAttack: Boolean? = null,

    /** 墓下役職公開ありか */
    @field:NotNull
    var openSkillInGrave: Boolean? = null,

    /** 墓下見学発言を地上から見られるか */
    @field:NotNull
    var visibleGraveSpectateMessage: Boolean? = null,

    /** 自分で画像をアップロードするか */
    @field:NotNull
    var shouldOriginalImage: Boolean? = null,

    /** キャラセットID */
    var characterSetId: List<Int>? = null,

    /** ダミーキャラID */
    var dummyCharaId: Int? = null,

    /** ダミーキャラ画像 */
    var dummyCharaImageFile: MultipartFile? = null,

    /** ダミーキャラ名 */
    @field:Length(min = 1, max = 40)
    var dummyCharaName: String? = null,

    /** ダミーキャラ略称 */
    @field:Length(min = 1, max = 1)
    var dummyCharaShortName: String? = null,

    /** ダミーキャラ入村発言 */
    @field:NotNull
    @field:Length(min = 1, max = 400)
    var dummyJoinMessage: String? = null,

    /** 入村パスワード */
    var joinPassword: String? = null,

    /** 観戦を可能にするか */
    @field:NotNull
    var availableSpectate: Boolean? = null,

    /** 村建てがプロデューサー機能を持つか */
    @field:NotNull
    var creatorIsProducer: Boolean? = null,

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

    /** 秘話可能範囲 */
    @field:NotNull
    var allowedSecretSayCode: String? = null,

    /** 発言制限 */
    @field:NotNull
    var sayRestrictList: List<SkillSayRestrictForm>? = null,

    /** 役職発言制限 */
    @field:NotNull
    var skillSayRestrictList: List<MessageTypeSayRestrictForm>? = null,

    /** RP発言制限 */
    @field:NotNull
    var rpSayRestrictList: List<MessageTypeSayRestrictForm>? = null
) {
    fun initialize() {
        if (startPersonMinNum == null) startPersonMinNum = 8
        if (personMaxNum == null) personMaxNum = 20
        if (openVote == null) openVote = true
        if (possibleSkillRequest == null) possibleSkillRequest = true
        if (availableSpectate == null) availableSpectate = false
        if (creatorIsProducer == null) creatorIsProducer = false
        if (availableSameWolfAttack == null) availableSameWolfAttack = true
        if (openSkillInGrave == null) openSkillInGrave = false
        if (visibleGraveSpectateMessage == null) visibleGraveSpectateMessage = false
        if (availableSuddonlyDeath == null) availableSuddonlyDeath = false
        if (availableCommit == null) availableCommit = false
        if (availableAction == null) availableAction = false
        if (availableGuardSameTarget == null) availableGuardSameTarget = true
        if (dayChangeIntervalHours == null) dayChangeIntervalHours = 24
        if (startYear == null) {
            LocalDate.now().plusDays(7L).let {
                startYear = it.year
                startMonth = it.monthValue
                startDay = it.dayOfMonth
                startHour = 0
                startMinute = 0
            }
        }
        if (shouldOriginalImage == null) shouldOriginalImage = false
        if (dummyCharaName == null) dummyCharaName = "楽天家 ゲルト"
        if (dummyCharaShortName == null) dummyCharaShortName = "楽"
        if (characterSetId == null) characterSetId = listOf(1)
        if (randomOrganization == null) randomOrganization = false
        if (reincarnationSkillAll == null) reincarnationSkillAll = false
        if (campAllocationList == null) campAllocationList = initializeCampAllocationList()
        if (wolfAllocation == null) wolfAllocation = RandomOrganizationWolfForm()
        if (organization == null) organization = VillageOrganize.defaultFixedOrganization
        if (allowedSecretSayCode == null) allowedSecretSayCode = CDef.AllowedSecretSay.なし.code()
        if (sayRestrictList == null) sayRestrictList = initializeSayRestrictList()
        if (skillSayRestrictList == null) skillSayRestrictList = initializeSkillSayRestrictList()
        if (rpSayRestrictList == null) rpSayRestrictList = initializeRpSayRestrictList()
    }

    private fun initializeCampAllocationList(): List<RandomOrganizationCampForm> {
        return listOf(
            CDef.Camp.村人陣営,
            CDef.Camp.人狼陣営,
            CDef.Camp.狐陣営,
            CDef.Camp.恋人陣営,
            CDef.Camp.愉快犯陣営
        ).map {
            RandomOrganizationCampForm(it)
        }
    }

    private fun initializeSayRestrictList(): List<SkillSayRestrictForm> {
        return Skills.all().filterNotSomeone().list.map {
            SkillSayRestrictForm(it)
        }
    }

    private fun initializeSkillSayRestrictList(): List<MessageTypeSayRestrictForm> {
        return listOf(
            CDef.MessageType.人狼の囁き,
            CDef.MessageType.共鳴発言,
            CDef.MessageType.恋人発言,
            CDef.MessageType.念話
        ).map {
            MessageTypeSayRestrictForm(it)
        }
    }

    private fun initializeRpSayRestrictList(): List<MessageTypeSayRestrictForm> {
        return listOf(CDef.MessageType.アクション).map {
            MessageTypeSayRestrictForm(it)
        }
    }

    fun override(village: Village) {
        startPersonMinNum = village.setting.personMin
        personMaxNum = village.setting.personMax
        dayChangeIntervalHours = village.setting.dayChangeIntervalSeconds / 3600
        dayChangeIntervalMinutes = (village.setting.dayChangeIntervalSeconds % 3600) / 60
        dayChangeIntervalSeconds = village.setting.dayChangeIntervalSeconds % 60
        shouldOriginalImage = village.setting.chara.isOriginalCharachip
        if (!village.setting.chara.isOriginalCharachip) {
            characterSetId = village.setting.chara.charachipIds
            dummyCharaId = village.setting.chara.dummyCharaId
        }
        openVote = village.setting.rule.isOpenVote
        availableSameWolfAttack = village.setting.rule.isAvailableSameWolfAttack
        openSkillInGrave = village.setting.rule.isOpenSkillInGrave
        visibleGraveSpectateMessage = village.setting.rule.isVisibleGraveSpectateMessage
        allowedSecretSayCode = village.setting.rule.secretSayRange.code
        availableSpectate = village.setting.rule.isAvailableSpectate
        creatorIsProducer = village.setting.rule.isCreatorIsProducer
        availableSuddonlyDeath = village.setting.rule.isAvailableSuddenlyDeath
        availableCommit = village.setting.rule.isAvailableCommit
        availableGuardSameTarget = village.setting.rule.isAvailableGuardSameTarget
        availableAction = village.setting.rule.isAvailableAction
        organization = village.setting.organize.fixedOrganization
        randomOrganization = village.setting.rule.isRandomOrganization
        reincarnationSkillAll = village.setting.rule.isReincarnationSkillAll
        val dbCampAllocationList = village.setting.organize.randomOrganization.campAllocation
        val dbSkillAllocationList = village.setting.organize.randomOrganization.skillAllocation
        campAllocationList = campAllocationList!!.map { campAllocation ->
            val dbCampAllocation = dbCampAllocationList.find { it.camp.code == campAllocation.campCode }
            campAllocation.copy(
                minNum = dbCampAllocation?.min ?: 0,
                maxNum = dbCampAllocation?.max ?: 0,
                allocation = dbCampAllocation?.allocation ?: 0,
                skillAllocation = campAllocation.skillAllocation!!.map { skillAllocation ->
                    val dbSkillAllocation = dbSkillAllocationList.find { it.skill.code == skillAllocation.skillCode }
                    skillAllocation.copy(
                        minNum = dbSkillAllocation?.min ?: 0,
                        maxNum = dbSkillAllocation?.max ?: 0,
                        allocation = dbSkillAllocation?.allocation ?: 0
                    )
                }
            )
        }
        wolfAllocation = village.setting.organize.randomOrganization.wolfAllocation?.let {
            RandomOrganizationWolfForm(
                minNum = it.min,
                maxNum = it.max
            )
        } ?: RandomOrganizationWolfForm()
        sayRestrictList = sayRestrictList!!.map { formRestrict ->
            val restrict =
                village.setting.sayRestriction.normalSayRestriction.find { it.skill.code == formRestrict.skillCode }
            formRestrict.copy(
                restrict = restrict != null,
                count = restrict?.count ?: 20,
                length = restrict?.length ?: 400
            )
        }
        skillSayRestrictList = skillSayRestrictList!!.map { formRestrict ->
            val restrict =
                village.setting.sayRestriction.skillSayRestriction.find { it.messageType.code == formRestrict.messageTypeCode }
            formRestrict.copy(
                restrict = restrict != null,
                count = restrict?.count ?: 20,
                length = restrict?.length ?: 400
            )
        }
        rpSayRestrictList = rpSayRestrictList!!.map { formRestrict ->
            val restrict =
                village.setting.sayRestriction.skillSayRestriction.find { it.messageType.code == formRestrict.messageTypeCode }
            formRestrict.copy(
                restrict = restrict != null,
                count = restrict?.count ?: 20,
                length = restrict?.length ?: 400
            )
        }
        village.setting.tags.list.find {
            it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内
        }?.let { welcomeRange = it.code }
        village.setting.tags.list.find {
            it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18
        }?.let { ageLimit = it.code }
    }

    fun toVillage(player: Player): Village {
        val startDatetime = LocalDateTime.of(startYear!!, startMonth!!, startDay!!, startHour!!, startMinute!!)
        val welcome = if (welcomeRange.isNullOrBlank()) emptyList() else listOf(
            CDef.VillageTagItem.codeOf(welcomeRange).toModel()
        )
        val age = if (ageLimit.isNullOrBlank()) emptyList() else listOf(CDef.VillageTagItem.codeOf(ageLimit).toModel())
        return Village(
            id = 0,
            name = villageName!!,
            createPlayerName = player.name,
            createDatetime = LocalDateTime.now(),
            status = VillageStatus(CDef.VillageStatus.募集中),
            roomSize = null,
            participants = VillageParticipants(count = 0, list = emptyList()),
            spectators = VillageParticipants(count = 0, list = emptyList()),
            days = VillageDays(
                list = listOf(VillageDay(day = 0, dayChangeDatetime = startDatetime))
            ),
            setting = VillageSetting(
                chara = VillageCharaSetting(
                    isOriginalCharachip = shouldOriginalImage!!,
                    dummyCharaId = dummyCharaId!!,
                    charachipIds = characterSetId!!,
                ),
                personMin = startPersonMinNum!!,
                personMax = personMaxNum!!,
                dayChangeIntervalSeconds = dayChangeIntervalHours!! * 3600 + dayChangeIntervalMinutes!! * 60 + dayChangeIntervalSeconds!!,
                startDatetime = startDatetime,
                rule = VillageRule(
                    isOpenVote = openVote!!,
                    isAvailableSameWolfAttack = availableSameWolfAttack!!,
                    isOpenSkillInGrave = openSkillInGrave!!,
                    isVisibleGraveSpectateMessage = visibleGraveSpectateMessage!!,
                    isAvailableSpectate = availableSpectate!!,
                    isCreatorIsProducer = creatorIsProducer!!,
                    isAvailableSuddenlyDeath = availableSuddonlyDeath!!,
                    isAvailableCommit = availableCommit!!,
                    isAvailableGuardSameTarget = availableGuardSameTarget!!,
                    isAvailableAction = availableAction!!,
                    isRandomOrganization = randomOrganization!!,
                    isPossibleSkillRequest = possibleSkillRequest!!,
                    isReincarnationSkillAll = reincarnationSkillAll!!,
                    secretSayRange = SecretSayRange(CDef.AllowedSecretSay.codeOf(allowedSecretSayCode!!))
                ),
                organize = VillageOrganize(
                    fixedOrganization = organization.orEmpty(),
                    randomOrganization = VillageRandomOrganize(
                        campAllocation = campAllocationList?.map {
                            VillageRandomOrganize.CampAllocation(
                                camp = Camp(CDef.Camp.codeOf(it.campCode)),
                                min = it.minNum!!,
                                max = it.maxNum,
                                allocation = it.allocation!!
                            )
                        } ?: emptyList(),
                        skillAllocation = campAllocationList?.flatMap { it.skillAllocation!! }?.map {
                            VillageRandomOrganize.SkillAllocation(
                                skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                                min = it.minNum!!,
                                max = it.maxNum,
                                allocation = it.allocation!!
                            )
                        } ?: emptyList(),
                        wolfAllocation = if (campAllocationList == null) null else wolfAllocation?.let {
                            VillageRandomOrganize.WolfAllocation(
                                min = it.minNum!!,
                                max = it.maxNum
                            )
                        }
                    )
                ),
                joinPassword = joinPassword,
                sayRestriction = SayRestriction(
                    normalSayRestriction = sayRestrictList!!.filter { it.restrict == true }.map {
                        SayRestriction.NormalSayRestriction(
                            skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                            messageType = MessageType(CDef.MessageType.通常発言),
                            count = it.count!!,
                            length = it.length!!
                        )
                    },
                    skillSayRestriction = (skillSayRestrictList!! + rpSayRestrictList!!).filter { it.restrict == true }
                        .map {
                            SayRestriction.SkillSayRestriction(
                                messageType = MessageType(CDef.MessageType.codeOf(it.messageTypeCode)),
                                count = it.count!!,
                                length = it.length!!
                            )
                        }
                ),
                tags = VillageTags(list = welcome + age)
            ),
            epilogueDay = null,
            winCamp = null
        )
    }
}