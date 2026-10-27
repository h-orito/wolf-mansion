package com.ort.app.application.coordinator

import com.ort.app.application.service.*
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageTime
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.SayDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageCoordinator(
    // application service
    private val messageService: MessageService,
    private val charaService: CharaService,
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val abilityService: AbilityService,
    private val randomKeywordService: RandomKeywordService,
    private val accessInfoCoordinator: AccessInfoCoordinator,
    private val notificationService: NotificationService,
    // domain service
    private val messageDomainService: MessageDomainService,
    private val sayDomainService: SayDomainService,
) {
    fun registerMessage(
        villageId: Int,
        message: Message,
    ): Message {
        val village =
            villageService.findVillage(villageId) ?: throw IllegalStateException("village not found. id: $villageId")
        val replacedMessage =
            messageDomainService.replaceRandomMessageIfNeeded(
                message = message,
                participants = village.allParticipants(),
                randomKeywords = randomKeywordService.findRandomKeywords(),
            )
        val registered = messageService.registerMessage(village, replacedMessage)
        notificationService.notifyToDeveloperIfNeeded(village.id, replacedMessage)
        return registered
    }

    fun confirmToSay(
        village: Village,
        myself: VillageParticipant?,
        messageText: String,
        messageType: String,
        faceType: String?,
        isConvertDisable: Boolean?,
        targetCharaId: Int?,
    ): Message {
        myself ?: throw WolfMansionBusinessException("myself not found.")
        val messageContent = MessageContent.invoke(messageType, messageText, faceType, isConvertDisable)
        val player = playerService.findPlayer(myself.playerId)
        assertSay(village, myself, player, messageContent)
        val toParticipant = findSecretSayTarget(messageContent, village, targetCharaId)
        assertSecretSayTarget(village, myself, player, messageContent, toParticipant)
        return Message(
            fromParticipantId = myself.id,
            fromCharacterName = myself.name(),
            toParticipantId = toParticipant?.id,
            toCharacterName = toParticipant?.name(),
            time = MessageTime(day = village.latestDay(), datetime = LocalDateTime.now()),
            content = messageContent,
        )
    }

    fun say(
        village: Village,
        myself: VillageParticipant?,
        message: String,
        messageType: String,
        faceType: String?,
        convertDisable: Boolean?,
        targetCharaId: Int?,
        ipAddress: String,
    ) {
        // assert
        myself ?: throw WolfMansionBusinessException("myself not found.")
        val messageContent = MessageContent.invoke(messageType, message, faceType, convertDisable)
        val player = playerService.findPlayer(myself.playerId)
        assertSay(village, myself, player, messageContent)
        val target = findSecretSayTarget(messageContent, village, targetCharaId)
        assertSecretSayTarget(village, myself, player, messageContent, target)
        // register message
        val messages =
            messageDomainService
                .createSayMessages(
                    village = village,
                    myself = myself,
                    target = target,
                    messageContent = messageContent,
                    abilities = abilityService.findAbilities(village.id),
                ).list
                .map { registerMessage(village.id, it) }
        // register access info
        accessInfoCoordinator.registerAccessInfo(village, myself, ipAddress)
        // notification
        val players = playerService.findPlayers(village.id)
        notificationService.notifyReceiveMessageToCustomerIfNeeded(village, players, messages.last())
    }

    private fun findSecretSayTarget(
        messageContent: MessageContent,
        village: Village,
        targetCharaId: Int?,
    ): VillageParticipant? {
        if (messageContent.type.toCdef() != CDef.MessageType.秘話) return null
        targetCharaId ?: return null
        return village.allParticipants().list.firstOrNull { it.charaId == targetCharaId }
            ?: throw WolfMansionBusinessException("秘話を送れない相手です")
    }

    private fun assertSecretSayTarget(
        village: Village,
        myself: VillageParticipant,
        player: Player,
        messageContent: MessageContent,
        target: VillageParticipant?,
    ) {
        if (messageContent.type.toCdef() != CDef.MessageType.秘話) return
        val creator =
            playerService.findPlayer(village.createPlayerName)
                ?: throw IllegalStateException("creator not found.")
        sayDomainService.assertSecretSayTarget(village, creator.id, myself, player, messageContent, target)
    }

    private fun assertSay(
        village: Village,
        myself: VillageParticipant,
        player: Player,
        messageContent: MessageContent,
    ) {
        val chara =
            charaService.findChara(myself.charaId, village.setting.chara.isOriginalCharachip)
                ?: throw WolfMansionBusinessException("chara not found.")
        val latestDayMessageCountMap =
            messageService.findParticipantDayMessageCount(village, village.latestDay(), myself)
        sayDomainService.assertSay(village, myself, player, chara, latestDayMessageCountMap, messageContent)
    }
}
