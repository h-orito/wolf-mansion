package com.ort.app.api.view.village

import com.ort.app.domain.model.camp.Camps
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageSetting
import com.ort.app.domain.model.village.setting.VillageRandomOrganize
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

data class VillageSettingsContent(
    /** 最低開始人数 */
    var startPersonMinNum: Int,
    /** 定員 */
    val personMaxNum: Int,
    /** 開始日時 */
    val startDatetime: LocalDateTime,
    /** 更新間隔 */
    val dayChangeInterval: String,
    /** 投票形式 */
    val voteType: String,
    /** 役職希望 */
    val skillRequestType: String,
    /** キャラクターグループID */
    val charaGroupId: Int,
    /** キャラセット名 */
    val charaGroupName: String,
    /** ダミーキャラ名 */
    val dummyCharaName: String,
    /** 入村パスワードを必要とするか */
    val isRequiredJoinPassword: Boolean,
    /** 役職希望可能か */
    val isSkillRequestAvailable: Boolean,
    /** 見学が可能か */
    val isAvailableSpectate: Boolean,
    /** 連続襲撃ありか */
    val isAvailableSameWolfAttack: Boolean,
    /** 連続護衛可能か */
    val isAvailableGuardSameTarget: Boolean,
    /** 転生時に全役職を候補とするか */
    val isReincarnationSkillAll: Boolean,
    /** 墓下役職公開ありか */
    val isOpenSkillInGrave: Boolean,
    /** 墓下見学発言を地上から見られるか */
    val isVisibleGraveSpectateMessage: Boolean,
    /** 秘話可能範囲 */
    val allowedSecretSayCode: String,
    /** 突然死ありか */
    val isAvailableSuddenlyDeath: Boolean,
    /** コミットありか */
    val isAvailableCommit: Boolean,
    /** アクションありか */
    val isAvailableAction: Boolean,
    /** 構成 */
    val organization: String,
    /** 闇鍋編成か */
    val isRandomOrganization: Boolean,
    /** 闇鍋陣営配分 */
    val campAllocationList: List<RandomCampOrganization>,
    /** 村設定の発言制限 */
    val sayRestrictList: List<SayRestriction>,
    /** 村設定の役職発言制限 */
    val skillSayRestrictList: List<SkillSayRestriction>,
    /** 村設定のRP発言制限 */
    val rpSayRestrictList: List<RpSayRestriction>,
    /** 村建てしたプレイヤー名 */
    val createPlayerName: String
) {
    constructor(village: Village, charachip: Charachip) : this(
        startPersonMinNum = village.setting.personMin,
        personMaxNum = village.setting.personMax,
        startDatetime = village.days.list.first().dayChangeDatetime,
        dayChangeInterval = mapDayChangeInterval(village.setting),
        voteType = if (village.setting.rule.isOpenVote) "記名投票" else "無記名投票",
        skillRequestType = if (village.setting.rule.isPossibleSkillRequest) "有効" else "無効",
        charaGroupId = charachip.id,
        charaGroupName = charachip.name,
        dummyCharaName = village.dummyParticipant().name(),
        isRequiredJoinPassword = !village.setting.joinPassword.isNullOrBlank(),
        isAvailableSpectate = village.setting.rule.isAvailableSpectate,
        isSkillRequestAvailable = village.setting.rule.isPossibleSkillRequest,
        isAvailableSameWolfAttack = village.setting.rule.isAvailableSameWolfAttack,
        isAvailableGuardSameTarget = village.setting.rule.isAvailableGuardSameTarget,
        isReincarnationSkillAll = village.setting.rule.isReincarnationSkillAll,
        isOpenSkillInGrave = village.setting.rule.isOpenSkillInGrave,
        isVisibleGraveSpectateMessage = village.setting.rule.isVisibleGraveSpectateMessage,
        allowedSecretSayCode = if (village.setting.rule.isAvailableSecretSay) "EVERYTHING" else "NOTHING",
        isAvailableSuddenlyDeath = village.setting.rule.isAvailableSuddenlyDeath,
        isAvailableCommit = village.setting.rule.isAvailableCommit,
        isAvailableAction = village.setting.rule.isAvailableAction,
        organization = mapOrganization(village),
        campAllocationList = mapCampAllocationList(village),
        isRandomOrganization = village.setting.rule.isRandomOrganization,
        sayRestrictList = mapSayRestrictList(village),
        skillSayRestrictList = mapSkillSayRestrictList(village),
        rpSayRestrictList = mapRpSayRestrictList(village),
        createPlayerName = village.createPlayerName
    )

    companion object {
        private fun mapDayChangeInterval(setting: VillageSetting): String {
            val interval = setting.dayChangeIntervalSeconds
            val hour = if (interval >= 3600) "${interval / 3600}時間" else ""
            val minute = if (interval >= 60) "${(interval % 3600) / 60}分" else ""
            val second = "${interval % 60}秒"
            return "$hour$minute$second"
        }

        private fun mapOrganization(village: Village): String {
            return if (village.status.isPrologue() || village.status.isCanceled()) {
                village.setting.organize.fixedOrganization.replace("\r\n", "\n").split("\n")
                    .joinToString("\n") { "${it.length.toString().padStart(2, '0')}人: $it" }
            } else if (village.setting.rule.isRandomOrganization) ""
            else {
                val count = village.participants.count
                val org = village.setting.organize.fixedOrganization.replace("\r\n", "\n").split("\n")
                    .first { it.length == count }
                "${count.toString().padStart(2, '0')}人: $org"
            }
        }

        private fun mapCampAllocationList(village: Village): List<RandomCampOrganization> {
            val campAllocationList = village.setting.organize.randomOrganization.campAllocation
            val skillAllocationList = village.setting.organize.randomOrganization.skillAllocation
            return Camps.all().list
                .filter { camp -> campAllocationList.any { it.camp.code == camp.code } }
                .map { camp ->
                    val campAllocation = campAllocationList.first { it.camp.code == camp.code }
                    RandomCampOrganization(campAllocation, skillAllocationList)
                }
        }

        private fun mapSayRestrictList(village: Village): List<SayRestriction> =
            Skills.all().filterNotSomeone().list.map { SayRestriction(it, village) }

        private fun mapSkillSayRestrictList(village: Village): List<SkillSayRestriction> =
            listOf(
                MessageType(CDef.MessageType.人狼の囁き),
                MessageType(CDef.MessageType.共鳴発言),
                MessageType(CDef.MessageType.恋人発言)
            ).map { SkillSayRestriction(it, village) }

        private fun mapRpSayRestrictList(village: Village): List<RpSayRestriction> =
            listOf(RpSayRestriction(MessageType(CDef.MessageType.アクション), village))
    }

    data class RandomCampOrganization(
        /** 陣営 */
        val campCode: String,
        /** 陣営名 */
        val campName: String,
        /** 最低人数 */
        val minNum: Int,
        /** 最大人数 */
        val maxNum: Int?,
        /** 配分 */
        val allocation: Int,
        /** 役職ごとの配分 */
        val skillAllocation: List<RandomSkillOrganization>
    ) {
        constructor(
            campAllocation: VillageRandomOrganize.CampAllocation,
            skillAllocationList: List<VillageRandomOrganize.SkillAllocation>
        ) : this(
            campCode = campAllocation.camp.code,
            campName = campAllocation.camp.name,
            minNum = campAllocation.min,
            maxNum = campAllocation.max,
            allocation = campAllocation.allocation,
            skillAllocation = Skills.all().filterNotSomeone()
                .filterByCamp(campAllocation.camp.toCdef()).list
                .mapNotNull { skill ->
                    skillAllocationList.firstOrNull { it.skill.code == skill.code }?.let { RandomSkillOrganization(it) }
                }
        )

        data class RandomSkillOrganization(
            /** 役職 */
            val skillCode: String,
            /** 役職名 */
            val skillName: String,
            /** 最低人数 */
            val minNum: Int,
            /** 最大人数 */
            val maxNum: Int?,
            /** 配分 */
            val allocation: Int
        ) {
            constructor(skillAllocation: VillageRandomOrganize.SkillAllocation) : this(
                skillCode = skillAllocation.skill.code,
                skillName = skillAllocation.skill.name,
                minNum = skillAllocation.min,
                maxNum = skillAllocation.max,
                allocation = skillAllocation.allocation
            )
        }
    }

    data class SayRestriction(
        /** 役職名 */
        val skillName: String,
        /** 役職 */
        val skillCode: String,
        /** 制限するか */
        var isRestrict: Boolean,
        /** 発言回数 */
        var count: Int?,
        /** 文字数 */
        var length: Int?
    ) {
        constructor(skill: Skill, village: Village) : this(
            skillName = skill.name,
            skillCode = skill.code,
            isRestrict = false,
            count = null,
            length = null
        ) {
            village.setting.sayRestriction.normalSayRestriction.find { it.skill.code == skill.code }?.let {
                isRestrict = true
                count = it.count
                length = it.length
            }
        }
    }

    data class SkillSayRestriction(
        /** 発言種別名 */
        val messageTypeName: String,
        /** 発言種別 */
        val messageTypeCode: String,
        /** 制限するか */
        var isRestrict: Boolean,
        /** 発言回数 */
        var count: Int?,
        /** 文字数 */
        var length: Int?
    ) {
        constructor(messageType: MessageType, village: Village) : this(
            messageTypeName = messageType.name,
            messageTypeCode = messageType.code,
            isRestrict = false,
            count = null,
            length = null
        ) {
            village.setting.sayRestriction.skillSayRestriction.find { it.messageType.code == messageType.code }
                ?.let {
                    isRestrict = true
                    count = it.count
                    length = it.length
                }
        }
    }

    data class RpSayRestriction(
        /** 発言種別名 */
        val messageTypeName: String,
        /** 発言種別 */
        val messageTypeCode: String,
        /** 制限するか */
        var isRestrict: Boolean,
        /** 発言回数 */
        var count: Int?,
        /** 文字数 */
        var length: Int?
    ) {
        constructor(messageType: MessageType, village: Village) : this(
            messageTypeName = messageType.name,
            messageTypeCode = messageType.code,
            isRestrict = false,
            count = null,
            length = null
        ) {
            village.setting.sayRestriction.skillSayRestriction.find { it.messageType.code == messageType.code }
                ?.let {
                    isRestrict = true
                    count = it.count
                    length = it.length
                }
        }
    }
}