package com.ort.app.api.view.village

import com.ort.app.api.view.OptionContent
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef

data class VillageFormContent(
    /** 参戦、役職変更、退村 */
    val participate: VillageParticipateFormContent,
    /** コミット */
    val commit: VillageCommitFormContent,
    /** 発言 */
    val say: VillageSayFormContent,
    /** アクション */
    val action: VillageActionFormContent,
    /** 名前変更 */
    val changeName: VillageChangeNameFormContent,
    /** 簡易メモ */
    val memo: VillageMemoFormContent,
    /** 能力行使 */
    val ability: VillageAbilityFormContent,
    /** 投票 */
    val vote: VillageVoteFormContent
) {
    constructor(
        village: Village,
        myself: VillageParticipant?,
        situation: ParticipantSituation,
        day: Int,
        charachips: Charachips
    ) : this(
        participate = VillageParticipateFormContent(village, myself, situation),
        commit = VillageCommitFormContent(situation),
        say = VillageSayFormContent(myself, situation, charachips),
        action = VillageActionFormContent(village, myself, situation),
        changeName = VillageChangeNameFormContent(situation),
        memo = VillageMemoFormContent(situation),
        ability = VillageAbilityFormContent(village, myself, situation, day),
        vote = VillageVoteFormContent(situation)
    )

    data class VillageParticipateFormContent(
        /** 参戦フォームを表示するか */
        val isDispParticipateForm: Boolean,
        /** 希望役職変更フォームを表示するか */
        val isDispChangeRequestSkillForm: Boolean,
        /** 役職希望無効のメッセージを表示するか */
        val isDispChangeRequestNgMessage: Boolean,
        /** 退村フォームを表示するか */
        val isDispLeaveVillageForm: Boolean,
        /** 参戦フォームで選択するキャラクターリスト */
        val selectableCharaList: List<VillageCharaContent>,
        /** 参戦フォームで選択する希望役職リスト */
        val selectableSkillList: List<VillageSkillContent>
    ) {
        constructor(
            village: Village,
            myself: VillageParticipant?,
            situation: ParticipantSituation
        ) : this(
            isDispParticipateForm = situation.participate.isAvailableParticipate || situation.participate.isAvailableSpectate,
            isDispChangeRequestSkillForm = situation.skillRequest.isAvailableSkillRequest,
            isDispChangeRequestNgMessage = village.status.isPrologue() && myself != null &&
                    !myself.isSpectator &&
                    !village.setting.rule.isPossibleSkillRequest,
            isDispLeaveVillageForm = situation.participate.isAvailableLeave,
            selectableCharaList = situation.participate.selectableCharaList.map { VillageCharaContent(it) },
            selectableSkillList = situation.skillRequest.selectableSkillList.map { VillageSkillContent(it) }
        )

        data class VillageCharaContent(
            val id: Int,
            val name: String,
            val url: String,
            val width: Int,
            val height: Int
        ) {
            constructor(chara: Chara) : this(
                id = chara.id,
                name = chara.name,
                url = chara.defaultImage().url,
                width = chara.size.width,
                height = chara.size.height
            )
        }

        data class VillageSkillContent(
            val code: String,
            val name: String
        ) {
            constructor(skill: Skill) : this(code = skill.code, name = skill.name)
        }
    }

    data class VillageCommitFormContent(
        /** コミットフォームを表示するか */
        val isDispCommitForm: Boolean
    ) {
        constructor(situation: ParticipantSituation) : this(isDispCommitForm = situation.commit.isAvailableCommit)
    }

    data class VillageSayFormContent(
        /** 発言フォームを表示するか */
        var isDispSayForm: Boolean,
        /** 通常発言可能か */
        val isAvailableNormalSay: Boolean,
        /** 囁き発言可能か */
        val isAvailableWerewolfSay: Boolean,
        /** 共有発言可能か */
        val isAvailableMasonSay: Boolean,
        /** 恋人発言可能か */
        val isAvailableLoversSay: Boolean,
        /** 念話可能か */
        val isAvailableTelepathy: Boolean,
        /** 墓下発言可能か */
        val isAvailableGraveSay: Boolean,
        /** 見学発言可能か */
        val isAvailableSpectateSay: Boolean,
        /** 独り言可能か */
        val isAvailableMonologueSay: Boolean,
        /** 秘話可能か */
        val isAvailableSecretSay: Boolean,
        /** アクション可能か */
        val isAvailableAction: Boolean,
        /** 発言制限 */
        val restrict: SayRestrictContent,
        /** 選択可能な表情区分 */
        val faceTypeList: List<OptionContent>,
        /** 秘話相手 */
        val secretSayTargetList: List<SecretSayTarget>
    ) {
        constructor(myself: VillageParticipant?, situation: ParticipantSituation, charachips: Charachips) : this(
            isDispSayForm = situation.say.isAvailableSay,
            isAvailableNormalSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.通常発言 },
            isAvailableWerewolfSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.人狼の囁き },
            isAvailableMasonSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.共鳴発言 },
            isAvailableLoversSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.恋人発言 },
            isAvailableTelepathy = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.念話 },
            isAvailableGraveSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.死者の呻き },
            isAvailableSpectateSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.見学発言 },
            isAvailableMonologueSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.独り言 },
            isAvailableSecretSay = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.秘話 },
            isAvailableAction = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.アクション },
            restrict = SayRestrictContent(myself, situation),
            faceTypeList = situation.say.selectableFaceTypeList.map {
                OptionContent(
                    name = it.name,
                    value = it.code
                )
            },
            secretSayTargetList = situation.say.selectableMessageTypeList
                .firstOrNull { it.messageType.toCdef() == CDef.MessageType.秘話 }?.targetList
                ?.map { SecretSayTarget(it, charachips) } ?: emptyList()
        )

        data class SecretSayTarget(
            val name: String,
            val value: String,
            val url: String,
            val width: Int,
            val height: Int
        ) {
            constructor(participant: VillageParticipant, charachips: Charachips) : this(
                name = participant.name(),
                value = participant.charaId.toString(),
                url = charachips.chara(participant.charaId).defaultImage().url,
                width = charachips.chara(participant.charaId).size.width,
                height = charachips.chara(participant.charaId).size.height
            )
        }
    }

    data class VillageActionFormContent(
        /** アクションフォームを表示するか */
        val isDispActionForm: Boolean,
        /** target */
        var targetList: List<OptionContent>
    ) {
        constructor(
            village: Village,
            myself: VillageParticipant?,
            situation: ParticipantSituation
        ) : this(
            isDispActionForm = situation.say.selectableMessageTypeList.any { it.messageType.toCdef() == CDef.MessageType.アクション },
            targetList = village.allParticipants().sortedByRoomNumber().list.filterNot { it.id == myself?.id }
                .map { OptionContent(name = it.name(), value = it.name()) }
        )
    }

    data class VillageChangeNameFormContent(
        /** 名前変更フォームを表示するか */
        val isDispChangeNameForm: Boolean
    ) {
        constructor(situation: ParticipantSituation) : this(isDispChangeNameForm = situation.rp.isAvailableChangeName)
    }

    data class VillageMemoFormContent(
        /** 簡易メモフォームを表示するか */
        val isDispMemoForm: Boolean
    ) {
        constructor(situation: ParticipantSituation) : this(isDispMemoForm = situation.rp.isAvailableMemo)
    }

    data class VillageAbilityFormContent(
        /** 能力行使対象リスト */
        val abilityTargetList: List<OptionContent>,
        /** 襲撃担当者リスト */
        val attackerList: List<OptionContent>,
        /** 能力行使履歴 */
        val skillHistoryList: List<String>,
        /** 人狼のキャラ名（狂信者向け） */
        val werewolfCharaNameList: String,
        /** C国狂人のキャラ名（人狼向け） */
        val cMadmanCharaNameList: String,
        /** 妖狐のキャラ名（背徳者向け） */
        val foxCharaNameList: String,
        /** 恋人陣営のキャラ名（恋人陣営向け） */
        val loversCharaNameList: String,
        /** 対象選択の前に表示する文言 */
        val targetPrefixMessage: String?,
        /** 対象選択の後に表示する文言 */
        val targetSuffixMessage: String?,
        /** 対象選択して足音を残す役職か */
        val isTargetingAndFootstep: Boolean,
        /** 付与されているステータス */
        val statusList: List<VillagePlayerStatusContent>
    ) {
        constructor(
            village: Village,
            myself: VillageParticipant?,
            situation: ParticipantSituation,
            day: Int
        ) : this(
            abilityTargetList = mapAbilityTargetList(situation.ability),
            attackerList = situation.ability.attackerList.map { OptionContent(it) },
            skillHistoryList = situation.ability.skillHistoryList,
            werewolfCharaNameList = situation.ability.wolfList.joinToString("、") { it.name() },
            cMadmanCharaNameList = situation.ability.cMadmanList.joinToString("、") { it.name() },
            foxCharaNameList = situation.ability.foxList.joinToString("、") { it.name() },
            loversCharaNameList = mapLoversCharaNameList(village, situation.ability.loversList),
            targetPrefixMessage = situation.ability.targetPrefix,
            targetSuffixMessage = situation.ability.targetSuffix,
            isTargetingAndFootstep = situation.ability.isTargetingAndFootstep,
            statusList = mapStatusList(village, myself, day)
        )

        companion object {
            private fun mapAbilityTargetList(ability: ParticipantAbilitySituation): List<OptionContent> {
                return if (ability.targetFootstepList.isNotEmpty()) {
                    ability.targetFootstepList.map { OptionContent(it, it) }
                } else {
                    if (ability.isAvailableNoTarget) {
                        listOf(OptionContent(name = "なし", value = "")) + ability.targetList.map { OptionContent(it) }
                    } else ability.targetList.map { OptionContent(it) }
                }
            }

            private fun mapLoversCharaNameList(village: Village, loversList: List<VillageParticipant>): String {
                val list = mutableListOf<String>()
                loversList.forEach { lover ->
                    lover.status.loverIdList.forEach { targetId ->
                        val target = village.participants.member(targetId)
                        list.add("${lover.name()} → ${target.name()}")
                    }
                }
                if (list.isEmpty()) return ""
                return list.joinToString(
                    prefix = "この村で恋に落ちているのは\n",
                    separator = "\n",
                    postfix = "\nです。"
                )
            }

            private fun mapStatusList(
                village: Village,
                myself: VillageParticipant?,
                day: Int
            ): List<VillagePlayerStatusContent> {
                myself ?: return emptyList()
                if (!village.canUseAbility(day) || !myself.canUseAbility()) return emptyList()
                // 恋絆
                val loversStatusList = myself.status.loverIdList.map {
                    VillagePlayerStatusContent(
                        statusCode = CDef.VillagePlayerStatusType.後追い.code(),
                        message = "あなたは${village.participants.member(it).name()}に恋しています。"
                    )
                }
                // 狐憑き
                val foxPossessionedStatusList = if (myself.status.isFoxPossessioned()) {
                    listOf(
                        VillagePlayerStatusContent(
                            statusCode = CDef.VillagePlayerStatusType.狐憑き.code(),
                            message = "あなたは妖狐に与するものとなりました。"
                        )
                    )
                } else emptyList()
                // 狂気
                val insanedList = if (myself.status.isInsaned()) {
                    listOf(
                        VillagePlayerStatusContent(
                            statusCode = CDef.VillagePlayerStatusType.狂気.code(),
                            message = "あなたは狂気を宿しています。"
                        )
                    )
                } else emptyList()
                // 信念
                val persuadedList = if (myself.status.isPersuaded()) {
                    listOf(
                        VillagePlayerStatusContent(
                            statusCode = CDef.VillagePlayerStatusType.信念.code(),
                            message = "あなたは平和を望んでいます。"
                        )
                    )
                } else emptyList()
                // 保険
                val insuranceStatusList = myself.status.insuranceIdList.firstOrNull()?.let {
                    listOf(
                        VillagePlayerStatusContent(
                            statusCode = CDef.VillagePlayerStatusType.保険.code(),
                            message = "あなたは保険を勧められました。"
                        )
                    )
                } ?: emptyList()
                return loversStatusList +
                        foxPossessionedStatusList +
                        insanedList +
                        persuadedList +
                        insuranceStatusList
            }
        }

        data class VillagePlayerStatusContent(
            val statusCode: String,
            val message: String
        )
    }

    data class VillageVoteFormContent(
        /** 投票対象リスト */
        val voteTargetList: List<OptionContent>
    ) {
        constructor(situation: ParticipantSituation) : this(
            voteTargetList = situation.vote.targetList.map { OptionContent(it) }
        )
    }

    data class SayRestrictContent(
        var normalCount: Int? = null,
        var normalLeftCount: Int? = null,
        var normalLength: Int? = null,
        var whisperCount: Int? = null,
        var whisperLeftCount: Int? = null,
        var whisperLength: Int? = null,
        var masonCount: Int? = null,
        var masonLeftCount: Int? = null,
        var masonLength: Int? = null,
        var loversCount: Int? = null,
        var loversLeftCount: Int? = null,
        var loversLength: Int? = null,
        var telepathyCount: Int? = null,
        var telepathyLeftCount: Int? = null,
        var telepathyLength: Int? = null,
        var actionCount: Int? = null,
        var actionLeftCount: Int? = null,
        var actionLength: Int? = null
    ) {
        constructor(
            myself: VillageParticipant?,
            situation: ParticipantSituation
        ) : this() {
            if (myself?.isAdmin() == true) return
            situation.say.selectableMessageTypeList.filter { it.restrict.isRestricted }.forEach {
                val restrict = it.restrict
                when (it.messageType.toCdef()) {
                    CDef.MessageType.通常発言 -> {
                        normalCount = restrict.maxCount
                        normalLength = restrict.maxLength
                        normalLeftCount = restrict.remainingCount
                    }
                    CDef.MessageType.人狼の囁き -> {
                        whisperCount = restrict.maxCount
                        whisperLength = restrict.maxLength
                        whisperLeftCount = restrict.remainingCount
                    }
                    CDef.MessageType.共鳴発言 -> {
                        masonCount = restrict.maxCount
                        masonLength = restrict.maxLength
                        masonLeftCount = restrict.remainingCount
                    }
                    CDef.MessageType.恋人発言 -> {
                        loversCount = restrict.maxCount
                        loversLength = restrict.maxLength
                        loversLeftCount = restrict.remainingCount
                    }
                    CDef.MessageType.念話 -> {
                        telepathyCount = restrict.maxCount
                        telepathyCount = restrict.maxLength
                        telepathyLeftCount = restrict.remainingCount
                    }
                    CDef.MessageType.アクション -> {
                        actionCount = restrict.maxCount
                        actionLength = restrict.maxLength
                        actionLeftCount = restrict.remainingCount
                    }
                    else -> {
                    }
                }
            }
        }
    }
}