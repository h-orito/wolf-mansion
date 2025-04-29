package com.ort.app.domain.model.message

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

interface MessageRepository {

    fun findMessages(
        village: Village,
        myself: VillageParticipant?,
        query: MessageQuery
    ): Messages

    fun findMessage(
        village: Village,
        messageType: CDef.MessageType,
        messageNumber: Int
    ): Message?

    fun findLatestMessageDatetime(
        myself: VillageParticipant?,
        query: MessageQuery
    ): LocalDateTime?

    fun findParticipantDayMessageCount(
        village: Village,
        day: Int,
        participant: VillageParticipant
    ): Map<CDef.MessageType, Int>

    fun registerMessage(village: Village, message: Message): Message
}