package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.vote.Vote
import com.ort.app.domain.model.vote.Votes

// ツール用
data class WholeVillageSituationsContent(
    val village: VillageView,
    val participantIdToChara: Map<Int, Chara>,
    val charaIdToParticipantId: Map<Int, Int>,
    val days: List<VillageDaySituation>
) {
    constructor(
        village: Village,
        // 表示用に加工された状態
        footsteps: Footsteps,
        // 黒箱などが反映された状態
        votes: Votes,
        charachips: Charachips
    ) : this(
        village = VillageView(
            village,
            village.participants.list.associate { it.id to charachips.chara(it.charaId) }
        ),
        participantIdToChara = village.participants.list.associate { it.id to charachips.chara(it.charaId) },
        charaIdToParticipantId = village.participants.list.associate { it.charaId to it.id },
        days = village.days.list.map {
            VillageDaySituation(village, it.day, footsteps, votes)
        }
    )

    data class VillageDaySituation(
        val day: Int,
        val rooms: List<VillageDayRoom>,
        val footsteps: List<String>,
        // 前日に投票して当日に処刑された分の投票
        val votes: List<Vote>
    ) {
        constructor(
            village: Village,
            day: Int,
            footsteps: Footsteps,
            votes: Votes
        ) : this(
            day = day,
            rooms = convertToVillageDayRoom(village, day),
            footsteps = footsteps.filterByDay(day).list.map { it.roomNumbers }.sorted(),
            votes = votes.filterByDay(day).list
        )

        companion object {
            private fun convertToVillageDayRoom(
                village: Village,
                day: Int,
            ): List<VillageDayRoom> {
                if (village.status.isPrologue()) return emptyList()
                val width = village.roomSize!!.width
                val height = village.roomSize.height
                return (1..width * height).map { roomNumber ->
                    val participant = village.participants.findByRoomNumber(roomNumber, day)
                    VillageDayRoom(
                        roomNumber = roomNumber,
                        x = (roomNumber - 1) % width,
                        y = (roomNumber - 1) / width,
                        participantId = participant?.id,
                        isDead = participant?.isDeadWhen(day),
                        deadReason = participant?.dead?.deadReasonWhen(day)?.let { DeadReasonView.of(it) }
                    )
                }
            }
        }

        data class VillageDayRoom(
            // 1始まり
            val roomNumber: Int,
            // 0始まり
            val x: Int,
            val y: Int,
            val participantId: Int?,
            val isDead: Boolean?,
            val deadReason: DeadReasonView?
        )
    }
}