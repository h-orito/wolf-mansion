package com.ort.app.application.coordinator

import com.ort.app.application.service.CharaService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.SlackService
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageTime
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.SayDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageCoordinator(
    // application service
    private val messageService: MessageService,
    private val charaService: CharaService,
    private val villageService: VillageApplicationService,
    private val randomKeywordService: RandomKeywordService,
    private val slackService: SlackService,
    // domain service
    private val messageDomainService: MessageDomainService,
    private val sayDomainService: SayDomainService
) {
    fun registerMessage(villageId: Int, message: Message) {
        val village =
            villageService.findVillage(villageId) ?: throw IllegalStateException("village not found. id: $villageId")
        val replacedMessage = messageDomainService.replaceRandomMessageIfNeeded(
            message = message,
            participants = village.allParticipants(),
            randomKeywords = randomKeywordService.findRandomKeywords()
        )
        messageService.registerMessage(village, replacedMessage)
        slackService.postIfNeeded(village.id, replacedMessage)
    }

    fun confirmToSay(
        village: Village,
        myself: VillageParticipant?,
        messageText: String,
        messageType: String,
        faceType: String?,
        isConvertDisable: Boolean?,
        targetCharaId: Int?
    ): Message {
        myself ?: throw WolfMansionBusinessException("myself not found.")
        val messageContent = MessageContent.invoke(messageType, messageText, faceType, isConvertDisable)
        assertSay(village, myself, messageContent)
        val toParticipant = targetCharaId?.let { village.allParticipants().chara(it) }
        return Message(
            fromParticipantId = myself.id,
            fromCharacterName = myself.name(),
            toParticipantId = toParticipant?.id,
            toCharacterName = toParticipant?.name(),
            time = MessageTime(day = village.latestDay(), datetime = LocalDateTime.now()),
            content = messageContent
        )
    }

    fun say(
        village: Village,
        myself: VillageParticipant?,
        message: String,
        messageType: String,
        faceType: String?,
        convertDisable: Boolean?,
        targetCharaId: Int?
    ) {
        myself ?: throw WolfMansionBusinessException("myself not found.")
        val messageContent = MessageContent.invoke(messageType, message, faceType, convertDisable)
        assertSay(village, myself, messageContent)
        val toParticipant = targetCharaId?.let { village.allParticipants().chara(it) }
        registerMessage(
            village.id,
            messageDomainService.createSayMessage(
                village = village,
                myself = myself,
                target = toParticipant,
                messageContent = messageContent
            )
        )
    }

    private fun assertSay(
        village: Village,
        myself: VillageParticipant,
        messageContent: MessageContent
    ) {
        val chara = charaService.findChara(myself.charaId) ?: throw WolfMansionBusinessException("chara not found.")
        val latestDayMessageCountMap =
            messageService.findParticipantDayMessageCount(village, village.latestDay(), myself)
        sayDomainService.assertSay(village, myself, chara, latestDayMessageCountMap, messageContent)
    }
}