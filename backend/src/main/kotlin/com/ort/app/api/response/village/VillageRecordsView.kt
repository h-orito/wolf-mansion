package com.ort.app.api.response.village

import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.dead.Dead
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * 終了済 (エピローグ / 終了 / 廃村) 村の戦績一覧。
 *
 * 旧 Thymeleaf `/village-record/list` (`VillageRecordSummaryListContent`) の置き換え。
 * 旧 API は snake_case + 文字列日付だったが、REST 化に合わせて他の REST API と統一し
 * camelCase + `LocalDateTime` (Spring の Jackson で ISO 文字列にシリアライズ) で返す。
 *
 * 表記揺れリスクを抑えるため、外部連携 (Discord ボット等) が旧 snake_case を期待していたら
 * 旧 Thymeleaf endpoint は当面残置する方針 (CLAUDE.md に従い Thymeleaf 撤去まで併存可)。
 */
@Schema(description = "終了村の戦績一覧")
data class VillageRecordsView(
    @field:Schema(description = "村レコード (新しい順)")
    val list: List<VillageRecordSummary>,
) {
    constructor(villages: Villages, players: Players) : this(
        list = villages.list.map { VillageRecordSummary(it, players) },
    )

    @Schema(description = "村 1 件の戦績")
    data class VillageRecordSummary(
        @field:Schema(description = "村 ID") val id: Int,
        @field:Schema(description = "村名") val name: String,
        @field:Schema(description = "ステータス名 (例: 終了, 廃村)") val status: String,
        @field:Schema(description = "編成 (役職略称の連結)") val organization: String,
        @field:Schema(description = "更新間隔 (秒)") val intervalSeconds: Int,
        @field:Schema(description = "1 日目開始日時 (廃村なら null)") val startDatetime: LocalDateTime?,
        @field:Schema(description = "プロローグ開始日時") val prologueDatetime: LocalDateTime,
        @field:Schema(description = "エピローグ開始日時 (廃村なら null)") val epilogueDatetime: LocalDateTime?,
        @field:Schema(description = "エピローグが何日目か") val epilogueDay: Int?,
        @field:Schema(description = "勝利陣営名") val winCampName: String?,
        @field:Schema(description = "参加者一覧 (見学含む)") val participants: List<VillageRecordParticipant>,
    ) {
        constructor(village: Village, players: Players) : this(
            id = village.id,
            name = village.name,
            status = village.status.name,
            organization = convertOrganization(village),
            intervalSeconds = village.setting.dayChangeIntervalSeconds,
            startDatetime = if (village.status.isCanceled()) null else village.setting.startDatetime,
            prologueDatetime = village.createDatetime,
            epilogueDatetime = if (village.status.isCanceled()) null else convertEpilogueDatetime(village),
            epilogueDay = village.epilogueDay,
            winCampName = village.winCamp?.name,
            participants = village.allParticipants().list.map { VillageRecordParticipant(it, players) },
        )

        @Schema(description = "村参加者 1 件の戦績情報")
        data class VillageRecordParticipant(
            @field:Schema(description = "プレイヤーのユーザ名") val userName: String,
            @field:Schema(description = "キャラ名") val characterName: String,
            @field:Schema(description = "役職名 (1日目時点、見学なら null)") val skillName: String?,
            @field:Schema(description = "見学か") val isSpectator: Boolean,
            @field:Schema(description = "勝利したか (廃村などで null あり)") val isWin: Boolean?,
            @field:Schema(description = "死亡したか") val isDead: Boolean,
            @field:Schema(description = "死亡日") val deadDay: Int?,
            @field:Schema(description = "死因 (\"〜死\" 表記)") val deadReason: String?,
            @field:Schema(description = "勝敗判定陣営名") val campName: String?,
        ) {
            constructor(participant: VillageParticipant, players: Players) : this(
                // `playerService.findPlayers(villageIdList)` は isGone=false の村プレイヤーのみ取得するため、
                // 途中退村プレイヤー (= isGone=true) は players に含まれない可能性がある。
                // 該当プレイヤーが見つからない場合は表示用フォールバック "(退会)" を返す。
                userName = players.list.firstOrNull { it.id == participant.playerId }?.name ?: "(退会)",
                characterName = participant.charaName.name,
                skillName = participant.skillWhen(1)?.name,
                isSpectator = participant.isSpectator,
                isWin = participant.isWin,
                isDead = participant.dead.isDead,
                deadDay = participant.dead.deadDay,
                deadReason = extractDeadReason(participant.dead),
                campName = participant.camp?.name,
            )

            companion object {
                private fun extractDeadReason(dead: Dead): String? {
                    // `dead.isDead == true` のとき `dead.reason` が常に非 null であることは
                    // ドメインモデル上の不変だが、コンパイラはスマートキャストできないので
                    // `?.let` で明示的に扱う。
                    return dead.reason?.let {
                        val name = it.name
                        if (name.endsWith("死")) name else "${name}死"
                    }
                }
            }
        }

        companion object {
            private fun convertOrganization(village: Village): String = when {
                village.status.isCanceled() -> "廃村"
                village.setting.rule.isRandomOrganization -> village.participants.list
                    // 廃村でないと skill は基本非 null だが、データ不整合時の NPE を避けるため
                    // `mapNotNull` で safe にフィルタする (廃村は↑で別分岐)
                    .mapNotNull { it.skill }
                    .sortedBy { it.toCdef().order().toInt() }
                    .joinToString(separator = "") { it.shortName }
                else -> village.setting.organize.fixedOrganization
                    .replace("\r\n", "\n").split("\n")
                    .firstOrNull { it.length == village.participants.count }
                    ?: ""
            }

            /**
             * エピローグ突入日時 = `(epilogueDay - 1)` の `dayChangeDatetime` (=その日の終了時刻
             * = 次の日 = エピローグ初日の開始時刻)。
             * 例えば epilogueDay=4 (4日目がエピローグ) なら day=3 の dayChangeDatetime。
             * epilogueDay=1 (= 1日目がエピローグ、極端なケース) なら day=0 (プロローグ) の
             * dayChangeDatetime が返り、これはプロローグ終了 = day 1 開始時刻なので意味的に正しい。
             */
            private fun convertEpilogueDatetime(village: Village): LocalDateTime? {
                val epilogueDay = village.epilogueDay ?: return null
                return village.days.list.firstOrNull { it.day == epilogueDay - 1 }?.dayChangeDatetime
            }
        }
    }
}
