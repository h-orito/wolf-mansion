package com.ort.app.api.view.village

import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import java.time.LocalDateTime

data class VillageMemberContent(
    /** 状態 e.g. 襲撃 */
    var status: String,
    /** キャラリスト */
    val statusMemberList: List<VillageMemberDetailContent>
) {
    companion object {
        // 死亡日順
        private fun List<VillageParticipant>.sortedByDeadDayThenCharaId(): List<VillageParticipant> =
            sortedWith(compareBy<VillageParticipant> { !it.dead.isDead }
                .thenBy { it.dead.deadDay ?: 0 }
                .thenBy { it.room?.number ?: 0 }
                .thenBy { it.charaId })
    }

    constructor(name: String, participants: VillageParticipants) : this(
        status = name,
        statusMemberList = participants.list.sortedByDeadDayThenCharaId()
            .map { VillageMemberDetailContent(it) }
    )

    data class VillageMemberDetailContent(
        /** キャラ名 */
        val charaName: String,
        /** 死亡日時 */
        val deadDay: String?,
        /** 最終接続日時 */
        val lastAccessDatetime: LocalDateTime,
        /** 簡易メモ */
        val memo: String?
    ) {
        constructor(
            participant: VillageParticipant
        ) : this(
            charaName = participant.name(),
            deadDay = if (participant.dead.isDead) "${participant.dead.deadDay}d" else null,
            lastAccessDatetime = participant.lastAccessDatetime,
            memo = participant.memo
        )
    }
}
