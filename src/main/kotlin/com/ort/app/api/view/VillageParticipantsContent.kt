package com.ort.app.api.view

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantStatus

data class VillageParticipantsContent(
    val list: List<VillageParticipantContent>
) {
    constructor(
        village: Village,
        players: Players
    ) : this(
        list = village.allParticipants().sortedByRoomNumber().list.map {
            VillageParticipantContent(
                participant = it,
                player = players.player(it.playerId)
            )
        }
    )

    data class VillageParticipantContent(
        val name: String,
        val playerName: String,
        val isSpectator: Boolean,
        val deadStatus: String,
        val skillName: String,
        val winStatus: String
    ) {
        constructor(
            participant: VillageParticipant,
            player: Player
        ) : this(
            name = participant.name(),
            playerName = player.name,
            isSpectator = participant.isSpectator,
            deadStatus = mappingToDeadStatus(participant),
            skillName = mappingToSkillName(participant),
            winStatus = mappingToWinStatus(participant)
        )

        companion object {
            private fun mappingToSkillName(participant: VillageParticipant): String {
                if (participant.isSpectator) return "見学参加"
                val skill = participant.skill!!.name
                val statuses = mappingToStatuses(participant.status)
                return if (statuses.isEmpty()) skill
                else "$skill（${statuses.joinToString(separator = "、")}）"
            }

            private fun mappingToDeadStatus(participant: VillageParticipant): String {
                if (participant.isSpectator) return ""
                if (participant.isAlive()) return "生存"
                return participant.dead.let {
                    val reason = it.reason!!.name.let { reason -> if (reason.endsWith("死")) reason else "${reason}死" }
                    "${it.deadDay}d${reason}"
                }
            }

            private fun mappingToStatuses(status: VillageParticipantStatus): List<String> {
                val list = mutableListOf<String>()
                if (status.hasLover()) list.add("恋絆")
                if (status.isFoxPossessioned()) list.add("狐憑き")
                return list
            }

            private fun mappingToWinStatus(participant: VillageParticipant): String {
                return when {
                    participant.isSpectator -> ""
                    participant.isWin!! -> return "勝利"
                    else -> "敗北"
                }
            }
        }
    }
}