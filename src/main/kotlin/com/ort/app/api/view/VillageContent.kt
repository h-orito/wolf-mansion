package com.ort.app.api.view

import com.ort.app.api.view.village.*
import com.ort.app.api.view.village.VillageSettingsContent
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.situation.VillageSituation
import com.ort.app.domain.model.situation.village.VillageMemberVotes
import com.ort.app.domain.model.situation.village.VillageWholeDetail
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

data class VillageContent(
    /** 村ID */
    val villageId: Int,
    /** 村表示用番号 */
    val villageNumber: String,
    /** 村名 */
    val villageName: String,
    /** 村ステータスコード */
    val villageStatusCode: String,
    /** 村設定 */
    val settings: VillageSettingsContent,
    /** 何日目か */
    val day: Int,
    /** 日付リスト */
    val dayList: List<Int>,
    /** エピローグ日時 */
    val epilogueDay: Int?,
    /** 参加者一覧 */
    val memberList: List<VillageMemberContent>,
    /** キャラクター一覧 */
    val characterList: List<OptionContent>,
    /** 発言抽出用キャラクター一覧 */
    val participantList: List<VillageFilterParticipantContent>,
    /** 参加者部屋割り当て */
    val roomAssignedRowList: List<VillageRoomAssignedRow>?,
    /** 部屋の横サイズ */
    val roomWidth: Int?,
    /** 参加している場合の各種フォーム表示 */
    val form: VillageFormContent,
    /** 参加している場合の自分自身の情報 */
    val myself: VillageParticipateContent?,
    /** 村設定変更可能か */
    val isAvailableSettingsUpdate: Boolean,
    /** 投票一覧 */
    val vote: VillageVoteContent?,
    /** 足音一覧 */
    val villageFootstepList: List<VillageFootstepContent>,
    /** 次回更新日時 */
    val dayChangeDatetime: LocalDateTime?,
    /** ネタバレ防止切り替えを表示するか */
    val isDispUnspoiler: Boolean,
    /** ランダムキーワード（カンマ区切り） */
    val randomKeywords: String,
    /** 全体状況リスト */
    val situationList: List<VillageSituationContent>,
    /** ネタバレ表示（エピと墓下公開用） */
    val isDispSpoilerContent: Boolean,
    /** 村建てした人か */
    val isCreatePlayer: Boolean
) {
    constructor(
        village: Village,
        day: Int,
        myself: VillageParticipant?,
        player: Player?,
        charachips: Charachips,
        keywords: RandomKeywords,
        villageSituation: VillageSituation,
        participantSituation: ParticipantSituation,
        isDispSpoilerContent: Boolean
    ) : this(
        villageId = village.id,
        villageNumber = village.id.toString().padStart(4, '0'),
        villageName = village.name,
        villageStatusCode = village.status.code,
        settings = VillageSettingsContent(village, charachips),
        day = day,
        dayList = village.days.list.map { it.day },
        epilogueDay = village.epilogueDay,
        memberList = mapMemberList(village.allParticipants()),
        characterList = village.allParticipants().sortedByRoomNumber().list.map { OptionContent(it) },
        participantList = village.allParticipants().sortedByRoomNumber().list.map {
            VillageFilterParticipantContent(village, it, charachips)
        },
        roomAssignedRowList = mapRoomAssignRowList(village, day, charachips, myself, player),
        roomWidth = village.roomSize?.width,
        form = VillageFormContent(village, myself, participantSituation, day, charachips),
        myself = myself?.let { VillageParticipateContent(it, charachips, participantSituation) },
        isAvailableSettingsUpdate = participantSituation.creator.isAvailableModifySetting,
        vote = if (day > 2) VillageVoteContent(village, villageSituation) else null,
        villageFootstepList = villageSituation.footstep.list.map { VillageFootstepContent(it.day, it.footstep) },
        dayChangeDatetime = mapDayChangeDatetime(village),
        isDispUnspoiler = village.status.isSettled(),
        randomKeywords = keywords.list.joinToString(separator = ",") { it.keyword },
        situationList = villageSituation.whole.list.map { VillageSituationContent(it) },
        isDispSpoilerContent = isDispSpoilerContent,
        isCreatePlayer = participantSituation.creator.isCreator
    )

    companion object {
        private fun mapMemberList(
            participants: VillageParticipants
        ): List<VillageMemberContent> {
            // 生存
            return listOf(
                // 生存
                VillageMemberContent(
                    name = "生存",
                    participants = participants.filterAlive().filterNotSpectator()
                ),
                // 処刑
                VillageMemberContent(
                    name = "処刑死",
                    participants = participants.filterExecuted().filterNotSpectator()
                ),
                // 無惨
                VillageMemberContent(
                    name = "無惨",
                    participants = participants.filterMiserable().filterNotSpectator()
                ),
                // 後追
                VillageMemberContent(
                    name = "後追",
                    participants = participants.filterSuicide().filterNotSpectator()
                ),
                // 突然死
                VillageMemberContent(
                    name = "突然",
                    participants = participants.filterSuddenly().filterNotSpectator()
                ),
                // 見学
                VillageMemberContent(
                    name = "見学",
                    participants = participants.filterSpectator()
                )
            )
        }

        private fun mapRoomAssignRowList(
            village: Village,
            day: Int,
            charachips: Charachips,
            myself: VillageParticipant?,
            player: Player?
        ): List<VillageRoomAssignedRow>? {
            village.roomSize ?: return null
            return List(village.roomSize.height) { columnIndex ->
                VillageRoomAssignedRow(village, day, columnIndex, charachips, myself, player)
            }
        }

        private fun mapDayChangeDatetime(village: Village): LocalDateTime? {
            return if (village.status.isFinished()) null
            else village.days.latestDay().dayChangeDatetime
        }
    }

    data class VillageParticipateContent(
        val id: Int,
        /** 参戦しているキャラの名前 */
        val charaName: String,
        /** 参戦しているキャラの画像 */
        val charaImageUrl: String,
        /** 参戦しているキャラの画像の横幅 */
        val charaImageWidth: Int,
        /** 参戦しているキャラの画像の縦幅 */
        val charaImageHeight: Int,
        /** 役職 */
        val skill: VillageParticipateSkillContent?,
        /** 死んでいるか */
        val isDead: Boolean,
        /** 勝敗判定陣営名 */
        val camp: String?
    ) {
        constructor(
            myself: VillageParticipant,
            charachips: Charachips,
            situation: ParticipantSituation
        ) : this(
            id = myself.id,
            charaName = myself.name(),
            charaImageUrl = charachips.chara(myself.charaId).defaultImage().url,
            charaImageWidth = charachips.chara(myself.charaId).size.width,
            charaImageHeight = charachips.chara(myself.charaId).size.height,
            skill = myself.skill?.let { VillageParticipateSkillContent(it, situation) },
            isDead = myself.dead.isDead,
            camp = myself.camp?.name
        )

        data class VillageParticipateSkillContent(
            /** 役職コード */
            var code: String,
            /** 襲撃能力を持っているか */
            val hasAttackAbility: Boolean,
            /** 占い能力を持っているか */
            val hasDivineAbility: Boolean,
            /** 護衛能力を持っているか */
            val hasGuardAbility: Boolean,
            /** 徘徊能力を持っているか */
            val hasDisturbAbility: Boolean,
            /** 足音を残す能力を持っているか */
            val hasFootstepAbility: Boolean,
            /** 同棲能力を持っているか */
            val hasCohabitAbility: Boolean,
            /** 捜査能力を持っているか */
            val hasInvestigateAbility: Boolean
        ) {
            constructor(
                skill: Skill,
                situation: ParticipantSituation
            ) : this(
                code = skill.code,
                hasAttackAbility = skill.toCdef().isHasAttackAbility,
                hasDivineAbility = skill.toCdef().isHasDivineAbility,
                hasGuardAbility = skill.toCdef() == CDef.Skill.狩人 || skill.toCdef() == CDef.Skill.風来狩人,
                hasDisturbAbility = skill.hasDisturbAbility(),
                hasFootstepAbility = skill.hasDisturbAbility() || situation.ability.isTargetingAndFootstep,
                hasCohabitAbility = skill.toCdef() == CDef.Skill.同棲者,
                hasInvestigateAbility = skill.hasInvestigateAbility()
            )
        }
    }

    data class VillageVoteContent(
        /** 参加者ごとの投票リスト */
        val voteList: List<VillageMemberVoteContent>,
        /** 投票した回数の最大 */
        var maxVoteCount: Int = 0
    ) {
        constructor(
            village: Village,
            villageSituation: VillageSituation
        ) : this(
            voteList = villageSituation.vote.list.map {
                VillageMemberVoteContent(village, it)
            }
        ) {
            maxVoteCount = voteList.maxByOrNull { it.voteTargetList.size }?.voteTargetList?.size ?: 0
        }

        data class VillageMemberVoteContent(
            /** キャラ省略名 */
            val charaName: String,
            /** 投票リスト */
            val voteTargetList: List<String>
        ) {
            constructor(
                village: Village,
                villageMemberVotes: VillageMemberVotes
            ) : this(
                charaName = villageMemberVotes.participant.shortName(),
                voteTargetList = (2 until village.latestDay()).map { day ->
                    villageMemberVotes.voteList.find { it.day == day }?.let { vote ->
                        village.participants.chara(vote.targetCharaId).shortName()
                    } ?: ""
                }
            )
        }
    }

    data class VillageFootstepContent(
        /** 何日めか */
        val day: Int,
        /** 足音 */
        val footstep: String
    )

    data class VillageSituationContent(
        val day: Int,
        val attackedChara: String,
        val executedChara: String,
        val suddonlyDeathChara: String,
        val suicideChara: String,
        val revivalChara: String,
        val ability: String
    ) {
        constructor(
            situation: VillageWholeDetail
        ) : this(
            day = situation.day,
            attackedChara = situation.miserable.mapCharas(situation.day),
            executedChara = situation.executed.mapCharas(situation.day),
            suddonlyDeathChara = situation.suddenlyDeath.mapCharas(situation.day),
            suicideChara = situation.suicide.mapCharas(situation.day),
            revivalChara = situation.revival.mapCharas(situation.day),
            ability = situation.ability.joinToString(separator = "\n")
        )

        companion object {
            private fun VillageParticipants.mapCharas(day: Int): String {
                return if (list.isEmpty()) "なし"
                else list.shuffled().joinToString(separator = "、") { it.shortNameWhen(day - 1) }
            }
        }
    }
}