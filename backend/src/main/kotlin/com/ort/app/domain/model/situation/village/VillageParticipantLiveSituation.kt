package com.ort.app.domain.model.situation.village

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class VillageParticipantLiveSituation(var list: List<VillageParticipantLive>) {

    constructor(village: Village): this(list = emptyList()) {
        list = listOf(
            // 生存
            VillageParticipantLive(
                status = "生存",
                list = village.participants
                    .filterAlive()
                    .filterNotSpectator()
                    .list.sortedByDeadDayThenCharaId()
            ),
            // 処刑
            VillageParticipantLive(
                status = "処刑死",
                list = village.participants
                    .filterExecuted()
                    .filterNotSpectator()
                    .list.sortedByDeadDayThenCharaId()
            ),
            // 無惨
            VillageParticipantLive(
                status = "無惨",
                list = village.participants
                    .filterMiserable()
                    .filterNotSpectator()
                    .list.sortedByDeadDayThenCharaId()
            ),
            // 後追
            VillageParticipantLive(
                status = "後追",
                list = village.participants
                    .filterSuicide()
                    .filterNotSpectator()
                    .list.sortedByDeadDayThenCharaId()
            ),
            // 突然死
            VillageParticipantLive(
                status = "突然",
                list = village.participants
                    .filterSuddenly()
                    .filterNotSpectator()
                    .list.sortedByDeadDayThenCharaId()
            ),
            // 見学
            VillageParticipantLive(
                status = "見学",
                list = village.spectators
                    .list.sortedByDeadDayThenCharaId()
            )
        )
    }

    // 死亡日順
    private fun List<VillageParticipant>.sortedByDeadDayThenCharaId(): List<VillageParticipant> =
        sortedWith(compareBy<VillageParticipant> { !it.dead.isDead }
            .thenBy { it.dead.deadDay ?: 0 }
            .thenBy { it.room?.number ?: 0 }
            .thenBy { it.charaId })
}

data class VillageParticipantLive(
    val status: String,
    val list: List<VillageParticipant>
)
