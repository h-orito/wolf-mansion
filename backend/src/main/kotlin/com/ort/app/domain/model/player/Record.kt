package com.ort.app.domain.model.player

import com.ort.app.domain.model.village.Villages

data class Record(
    val participateCount: Int,
    val winCount: Int,
    val winRate: Float
) {
    constructor(
        player: Player,
        villages: Villages
    ) : this(
        participateCount = villages.list.size,
        winCount = sumWinCount(villages, player),
        winRate = if (villages.list.isEmpty()) 0F else sumWinCount(
            villages,
            player
        ).toFloat() / villages.list.size.toFloat()
    )

    companion object {

        private fun sumWinCount(villages: Villages, player: Player): Int {
            return villages.list.count { village ->
                village.participants.list.first { participant ->
                    participant.playerId == player.id && !participant.isGone
                }.isWin!!
            }
        }
    }
}