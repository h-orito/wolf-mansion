package com.ort.app.api.view

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.ability.toModel
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.Messages
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import java.time.format.DateTimeFormatter

data class VillageMessageListContent(
    /** メッセージリスト */
    var messageList: List<VillageMessageContent>,

    /** 村状態メッセージ */
    val villageStatusMessage: String?,

    /** コミット状態メッセージ */
    val commitStatusMessage: String?,

    /** 突然死候補メッセージ */
    val suddenlyDeathMessage: String?,

    /** 最新日付 */
    val latestDay: Int,

    /** 総ページ数 */
    val allPageCount: Int,

    /** 前のページがあるか */
    val isExistPrePage: Boolean,

    /** 次のページがあるか */
    val isExistNextPage: Boolean,

    /** 現在のページ番号 */
    val currentPageNum: Int?,

    /** 最新を表示か */
    val isDispLatest: Boolean,

    /** 表示するページ番号リスト */
    val pageNumList: List<Int>,

    /** 最新発言の文字列型日時uuuuMMddHHMiss */
    val latestMessageDatetime: String?
) {
    constructor(
        messages: Messages,
        village: Village,
        myself: VillageParticipant?,
        charas: Charas,
        players: Players,
        votes: Votes,
        commits: Commits,
        abilities: Abilities,
        day: Int
    ) : this(
        messageList = messages.list.map { message ->
            VillageMessageContent(
                village = village,
                message = message,
                fromParticipant = message.fromParticipantId?.let { village.allParticipants().member(it) },
                player = message.fromParticipantId?.let {
                    players.player(
                        village.allParticipants().member(it).playerId
                    )
                },
                charas = charas,
                hasBigEar = village.status.isProgress() && myself?.hasBigEar() ?: false,
                isRainbow = isRainbow(message, abilities, village)
            )
        },
        villageStatusMessage = mapVillageStatusMessage(village, myself, day),
        commitStatusMessage = mapCommitStatusMessage(village, day, commits),
        suddenlyDeathMessage = mapSuddenlyDeathMessage(village, votes, day),
        latestDay = village.latestDay(),
        allPageCount = messages.allPageCount,
        isExistPrePage = if (messages.isLatest) false else messages.isExistPrePage,
        isExistNextPage = if (messages.isLatest) false else messages.isExistNextPage,
        currentPageNum = if (messages.isLatest) null else messages.currentPageNum,
        isDispLatest = messages.isLatest,
        pageNumList = mapPageNumList(messages),
        latestMessageDatetime = messages.list.lastOrNull()?.time?.datetime?.format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss"))
            ?: "0"
    )

    companion object {
        private fun mapVillageStatusMessage(village: Village, myself: VillageParticipant?, day: Int): String? {
            return if (day != village.latestDay()) null
            else village.statusMessage(myself != null)
        }

        private fun mapSuddenlyDeathMessage(village: Village, votes: Votes, day: Int): String? {
            if (!isDispSuddenlyDeathWarnMessage(village, day)) return null

            val noVoteParticipantList = village.participants
                .filterNotDummy(village.dummyParticipant())
                .filterNotGone()
                .filterAlive()
                .list.filter { votes.filterByCharaId(it.charaId).list.isEmpty() }
            if (noVoteParticipantList.isEmpty()) return null
            return noVoteParticipantList.joinToString(
                separator = "、",
                prefix = "本日まだ投票していない者は、",
                postfix = "\n\n※未投票で更新時刻を迎えると突然死します。"
            ) { it.name() }
        }

        fun isDispSuddenlyDeathWarnMessage(village: Village, day: Int): Boolean {
            return day == village.latestDay()
                    && village.setting.rule.isAvailableSuddenlyDeath
                    && village.status.isProgress()
                    && 2 <= day
        }

        private fun mapCommitStatusMessage(village: Village, day: Int, commits: Commits): String? {
            if (!isDispCommitMessage(village, day)) return null
            val max = village.participants
                .filterNotGone()
                .filterAlive()
                .filterNotDummy(village.dummyParticipant())
                .list.size
            val current = commits.list.size
            return "生存者全員がコミットすると日付が更新されます。\n\n現在 $current/${max}人 がコミットしています。"
        }

        fun isDispCommitMessage(village: Village, day: Int): Boolean {
            return village.setting.rule.isAvailableCommit
                    && village.status.isProgress()
                    && day == village.latestDay()
        }

        private fun mapPageNumList(messages: Messages): List<Int> {
            val allPageCount: Int = messages.allPageCount
            if (messages.isLatest) {
                val start = if (allPageCount < 5) 1 else allPageCount - 4
                return (start..allPageCount).toList()
            }
            val currentPageNumber: Int = messages.currentPageNum!!
            var startPage = currentPageNumber - 2
            var endPage = currentPageNumber + 2
            if (startPage < 1) {
                startPage = 1
                endPage = 5
            }
            if (endPage > allPageCount) {
                endPage = allPageCount
                startPage = allPageCount - 4
                if (startPage < 1) {
                    startPage = 1
                }
            }
            return (startPage..endPage).toList()
        }

        private fun isRainbow(message: Message, abilities: Abilities, village: Village): Boolean {
            message.fromParticipantId ?: return false
            // 対象は発言系メッセージのみ
            if (!message.content.type.isSayType()) return false
            // 虹塗りされている
            val charaId = village.allParticipants().member(message.fromParticipantId).charaId
            return abilities.filterByDay(message.time.day - 1)
                .filterByType(CDef.AbilityType.虹塗り.toModel()).list.any { it.targetCharaId == charaId }
        }
    }
}