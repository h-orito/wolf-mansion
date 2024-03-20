package com.ort.app.application.service

import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.toot.TootRepository
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.MessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class NotificationService(
    private val discordRepository: DiscordRepository,
    private val tootRepository: TootRepository,
    private val messageDomainService: MessageDomainService
) {

    private val format = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm")

    fun notifyToDeveloperIfNeeded(villageId: Int, message: Message) {
        if (!message.shouldNotify()) return
        discordRepository.post(villageId, message.time.day, message.content.text)
    }

    fun notifyToDeveloperTextIfNeeded(village: Village, text: String) {
        discordRepository.post(village.id, village.latestDay(), text)
    }

    fun notifyParticipantEnoughToCustomerIfNeeded(village: Village) {
        if (village.participants.count != village.setting.personMin) return
        // 身内村は通知しない
        if (!village.setting.joinPassword.isNullOrBlank()) return

        val startDateTime = village.setting.startDatetime.format(format)
        val msg = "人数が揃いました。次回更新時に村が開始されます。\r\n村名：${village.name}\r\n開始予定：${startDateTime}"
        tootRepository.toot(msg, village.id)
    }

    fun notifyCreateVillageToCustomerIfNeeded(village: Village) {
        if (!village.setting.joinPassword.isNullOrEmpty()) return
        val startDatetime = village.setting.startDatetime.format(format)
        val msg = """
                新しい村が作成されました。
                村名：${village.name}
                開始予定：$startDatetime
            """.trimIndent()
        tootRepository.toot(msg, village.id)
    }

    fun notifyDaychange(villageId: Int, list: List<String>) =
        list.forEach {
            tootRepository.toot(it, villageId) // admin mastodon
        }

    fun notifyVillageStartToCustomerIfNeeded(village: Village) {
        village.allParticipants().list.filter {
            it.notification?.village?.start ?: false
        }.forEach {
            discordRepository.postToWebhook(
                webhookUrl = it.notification!!.discordWebhookUrl,
                villageId = village.id,
                message = "村が開始されました。"
            )
        }
    }

    fun notifyVillageEpilogueToCustomerIfNeeded(village: Village) {
        village.allParticipants().list.filter {
            it.notification?.village?.epilogue ?: false
        }.forEach {
            discordRepository.postToWebhook(
                webhookUrl = it.notification!!.discordWebhookUrl,
                villageId = village.id,
                message = "村がエピローグを迎えました。"
            )
        }
    }

    fun notifyVillageDaychangeToCustomerIfNeeded(village: Village) {
        village.allParticipants().list.filter {
            it.notification?.village?.dayChange ?: false
        }.forEach {
            discordRepository.postToWebhook(
                webhookUrl = it.notification!!.discordWebhookUrl,
                villageId = village.id,
                message = "村の日付が${village.latestDay()}日目に更新されました。"
            )
        }
    }

    fun notifyTest(url: String, villageId: Int) {
        discordRepository.postToWebhook(
            webhookUrl = url,
            villageId = villageId,
            message = "テスト通知です。"
        )
    }

    fun notifyReceiveMessageToCustomerIfNeeded(
        village: Village,
        players: Players,
        message: Message
    ) {
        // 秘話→アンカー→キーワード→役職窓の順
        val alreadyNotifiedParticipantIds = mutableListOf<Int>()
        notifyReceiveSecretSayToCustomerIfNeeded(village, message)?.let {
            alreadyNotifiedParticipantIds.add(it)
        }
        val idsByAnchor =
            notifyReceiveAnchorToCustomerIfNeeded(village, players, message, alreadyNotifiedParticipantIds)
        alreadyNotifiedParticipantIds.addAll(idsByAnchor)
        val idsByKeyword =
            notifyReceiveKeywordToCustomerIfNeeded(village, players, message, alreadyNotifiedParticipantIds)
        alreadyNotifiedParticipantIds.addAll(idsByKeyword)
        notifyReceiveAbilitySayToCustomerIfNeeded(village, players, message, alreadyNotifiedParticipantIds)
    }

    private fun notifyReceiveSecretSayToCustomerIfNeeded(
        village: Village,
        message: Message
    ): Int? {
        if (message.content.type.toCdef() != CDef.MessageType.秘話) return null
        val toParticipant = village.allParticipants().list.find { it.id == message.toParticipantId } ?: return null
        val fromParticipant = village.allParticipants().list.find { it.id == message.fromParticipantId } ?: return null
        return toParticipant.notification?.let {
            if (!it.message.secretSay) return null
            discordRepository.postToWebhook(
                webhookUrl = it.discordWebhookUrl,
                villageId = village.id,
                message = "${fromParticipant.name()}から秘話が届きました。",
                shouldContainVillageUrl = false
            )
            return toParticipant.id
        }
    }

    private fun notifyReceiveAnchorToCustomerIfNeeded(
        village: Village,
        players: Players,
        message: Message,
        alreadyNotifiedParticipantIds: List<Int>
    ): List<Int> {
        val notifiedParticipantIds = mutableListOf<Int>()
        val fromParticipant = village.allParticipants().list.find { it.id == message.fromParticipantId }

        village.allParticipants().list
            .asSequence()
            .filterNot { it.id == message.fromParticipantId } // 自分の発言でない
            .filterNot { alreadyNotifiedParticipantIds.contains(it.id) }
            .filter {
                val player = players.player(it.playerId)
                messageDomainService.isViewable(village, it, player, message.content.type.toCdef(), village.latestDay())
            }.filter {
                it.notification?.message?.anchor ?: false
            }.filter {
                message.sendToParticipantIds.contains(it.id)
            }
            .toList().forEach {
                val messageTypeName = message.content.type.name
                val text = if (fromParticipant == null) {
                    "${messageTypeName}であなたの発言がアンカー指定されました。"
                } else "${fromParticipant.name()}の${messageTypeName}であなたの発言がアンカー指定されました。"
                discordRepository.postToWebhook(
                    webhookUrl = it.notification!!.discordWebhookUrl,
                    villageId = village.id,
                    message = text,
                    shouldContainVillageUrl = false
                )
                notifiedParticipantIds.add(it.id)
            }
        return notifiedParticipantIds
    }

    private fun notifyReceiveKeywordToCustomerIfNeeded(
        village: Village,
        players: Players,
        message: Message,
        alreadyNotifiedParticipantIds: List<Int>
    ): List<Int> {
        val notifiedParticipantIds = mutableListOf<Int>()
        val fromParticipant = village.allParticipants().list.find { it.id == message.fromParticipantId }

        village.allParticipants().list
            .asSequence()
            .filter {
                val keywords = it.notification?.message?.keywords
                val player = players.player(it.playerId)

                it.id != message.fromParticipantId &&  // 自分の発言でない
                        !alreadyNotifiedParticipantIds.contains(it.id) &&  // 通知済みでない
                        // 発言を閲覧できる
                        messageDomainService.isViewable(
                            village = village,
                            myself = it,
                            player = player,
                            messageType = message.content.type.toCdef(),
                            day = village.latestDay()
                        )
                        // キーワードが含まれている
                        && !keywords.isNullOrEmpty()
                        && keywords.any { keyword -> message.content.text.contains(keyword) }
            }
            .toList().forEach {
                val messageTypeName = message.content.type.name
                val text = if (fromParticipant == null) {
                    "${messageTypeName}に指定キーワードが含まれています。"
                } else "${fromParticipant.name()}の${messageTypeName}に指定キーワードが含まれています。"
                discordRepository.postToWebhook(
                    webhookUrl = it.notification!!.discordWebhookUrl,
                    villageId = village.id,
                    message = text,
                    shouldContainVillageUrl = false
                )
                notifiedParticipantIds.add(it.id)
            }
        return notifiedParticipantIds
    }

    private fun notifyReceiveAbilitySayToCustomerIfNeeded(
        village: Village,
        players: Players,
        message: Message,
        alreadyNotifiedParticipantIds: List<Int>
    ) {
        if (!message.content.type.isOwlViewableType()) return
        val fromParticipant = village.participants.list.find { it.id == message.fromParticipantId } ?: return
        village.allParticipants().list
            .filterNot { it.id == message.fromParticipantId } // 自分の発言でない
            .filterNot { alreadyNotifiedParticipantIds.contains(it.id) } // 既にアンカーで通知していたら通知しない
            .filter {
                val player = players.player(it.playerId)
                messageDomainService.isViewable(village, it, player, message.content.type.toCdef(), village.latestDay())
            }.filter {
                it.notification?.message?.abilitySay ?: false
            }.forEach {
                discordRepository.postToWebhook(
                    webhookUrl = it.notification!!.discordWebhookUrl,
                    villageId = village.id,
                    message = "${fromParticipant.name()}から${message.content.type.name}が届きました。",
                    shouldContainVillageUrl = false
                )
            }
    }
}