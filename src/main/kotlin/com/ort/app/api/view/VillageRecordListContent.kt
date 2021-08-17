package com.ort.app.api.view

import com.fasterxml.jackson.annotation.JsonProperty
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.dead.Dead
import java.time.format.DateTimeFormatter

data class VillageRecordListContent(
    val list: List<VillageRecord>
) {
    constructor(villages: Villages, players: Players) : this(
        list = villages.list.map { VillageRecord(it, players) }
    )

    data class VillageRecord(
        /** 村ID  */
        val id: Int,
        /** 村名  */
        val name: String,
        /** ステータス  */
        val status: String,
        /** 編成  */
        val organization: String,
        /** 更新間隔（秒）  */
        @JsonProperty("interval_seconds")
        val intervalSeconds: Int,
        /** 開始日時  */
        @JsonProperty("start_datetime")
        val startDatetime: String?,
        /** プロローグ開始日時  */
        @JsonProperty("prologue_datetime")
        val prologueDatetime: String,
        /** エピローグ日時  */
        @JsonProperty("epilogue_datetime")
        val epilogueDatetime: String?,
        /** エピローグが何日目か  */
        @JsonProperty("epilogue_day")
        val epilogueDay: Int?,
        /** URL  */
        val url: String?,
        /** 勝利陣営名  */
        @JsonProperty("win_camp_name")
        val winCampName: String?,
        /** 参加者リスト  */
        @JsonProperty("participant_list")
        val participantList: List<VillageParticipantRecord>
    ) {
        constructor(village: Village, players: Players) : this(
            id = village.id,
            name = village.name,
            status = village.status.name,
            organization = convertToOrganization(village),
            intervalSeconds = village.setting.dayChangeIntervalSeconds,
            startDatetime = if (village.status.isCanceled()) null
            else village.setting.startDatetime.format(formatter),
            prologueDatetime = village.createDatetime.format(formatter),
            epilogueDatetime = if (village.status.isCanceled()) null
            else convertToEpilogueDatetime(village),
            epilogueDay = village.epilogueDay,
            url = "https://wolfort.net/wolf-mansion/village/${village.id}",
            winCampName = village.winCamp?.name,
            participantList = village.allParticipants().list.map {
                VillageParticipantRecord(it, players)
            }
        )

        companion object {
            private val formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm")

            private fun convertToOrganization(village: Village): String {
                return when {
                    village.status.isCanceled() -> return "廃村"
                    village.setting.rule.isRandomOrganization -> {
                        village.participants.list
                            .map { it.skill!! }
                            .sortedBy { it.toCdef().order().toInt() }
                            .joinToString(separator = "") { it.shortName }
                    }
                    else -> {
                        village.setting.organize.fixedOrganization
                            .replace("\r\n", "\n")
                            .split("\n")
                            .first { it.length == village.participants.count }
                    }
                }
            }

            private fun convertToEpilogueDatetime(village: Village): String? {
                return village.days.list
                    .firstOrNull { it.day == village.epilogueDay!! - 1 }
                    ?.dayChangeDatetime?.format(formatter)
            }
        }

        data class VillageParticipantRecord(
            /** ユーザID  */
            @JsonProperty("user_id")
            val userId: String,
            /** キャラ名  */
            @JsonProperty("character_name")
            val characterName: String,
            /** 役職名  */
            @JsonProperty("skill_name")
            val skillName: String?,
            /** 見学か  */
            @JsonProperty("spectator")
            val isSpectator: Boolean,
            /** 勝利したか  */
            @JsonProperty("win")
            val isWin: Boolean?,
            /** 死亡したか  */
            @JsonProperty("dead")
            val isDead: Boolean,
            /** 死亡日  */
            @JsonProperty("dead_day")
            val deadDay: Int?,
            /** 死亡理由  */
            @JsonProperty("dead_reason")
            val deadReason: String?,
            /** 勝敗判定陣営  */
            @JsonProperty("camp_name")
            val campName: String?
        ) {
            constructor(
                participant: VillageParticipant,
                players: Players
            ) : this(
                userId = players.list.first { it.id == participant.playerId }.name,
                characterName = participant.charaName.name,
                skillName = participant.skill?.name,
                isSpectator = participant.isSpectator,
                isWin = participant.isWin,
                isDead = participant.dead.isDead,
                deadDay = participant.dead.deadDay,
                deadReason = extractDeadReason(participant.dead),
                campName = participant.camp?.name
            )

            companion object {
                private fun extractDeadReason(dead: Dead): String? {
                    return if (!dead.isDead) null
                    else {
                        val reason = dead.reason!!.name
                        return if (reason.endsWith("死")) {
                            reason
                        } else {
                            reason + "死"
                        }
                    }
                }
            }
        }

    }
}