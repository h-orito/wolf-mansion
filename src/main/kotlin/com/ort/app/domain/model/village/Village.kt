package com.ort.app.domain.model.village

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.room.RoomSize
import java.time.LocalDateTime

data class Village(
    val id: Int,
    val name: String,
    val createPlayerName: String,
    val createDatetime: LocalDateTime,
    val status: VillageStatus,
    val roomSize: RoomSize?,
    val participants: VillageParticipants,
    val spectators: VillageParticipants,
    val days: VillageDays,
    val setting: VillageSetting,
    val epilogueDay: Int?,
    val winCamp: Camp?
) {
    fun allParticipants(): VillageParticipants {
        val list = (participants.list + spectators.list)
        return VillageParticipants(
            count = list.size,
            list = list
        )
    }
}