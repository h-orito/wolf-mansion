package com.ort.app.application.service

import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.message.MessageRepository
import com.ort.app.domain.model.message.Messages
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageService(
    private val messageDomainService: MessageDomainService,
    private val messageRepository: MessageRepository
) {

    fun findMeesages(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        query: MessageQuery
    ): Messages {
        messageDomainService.setViewableQuery(village, myself, player, query)
        return messageRepository.findMessages(village, myself, query)
    }

    fun findMessage(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        messageType: String,
        messageNumber: Int
    ): Message? {
        val cdef = requireNotNull(CDef.MessageType.codeOf(messageType))
        if (!messageDomainService.isViewable(village, myself, player, cdef, village.latestDay())) {
            return null
        }
        return messageRepository.findMessage(village, cdef, messageNumber)
    }

    fun findLatestMessageDatetime(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        query: MessageQuery
    ): LocalDateTime? {
        messageDomainService.setViewableQuery(village, myself, player, query)
        return messageRepository.findLatestMessageDatetime(myself, query)
    }

    fun findParticipantDayMessageCount(
        village: Village,
        day: Int,
        participant: VillageParticipant
    ): Map<CDef.MessageType, Int> = messageRepository.findParticipantDayMessageCount(village, day, participant)

    fun registerMessage(village: Village, message: Message): Message =
        messageRepository.registerMessage(village, message)

    fun updateDaychangeDifference(village: Village, current: Messages, changed: Messages) =
        changed.list.drop(current.list.size).forEach { registerMessage(village, it) }
}