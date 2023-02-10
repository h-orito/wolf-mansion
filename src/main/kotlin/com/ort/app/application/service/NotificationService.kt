package com.ort.app.application.service

import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.slack.SlackRepository
import com.ort.app.domain.model.toot.TootRepository
import com.ort.app.domain.model.tweet.TweetRepository
import com.ort.app.domain.model.village.Village
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class NotificationService(
    private val discordRepository: DiscordRepository,
    private val slackRepository: SlackRepository,
    private val tweetRepository: TweetRepository,
    private val tootRepository: TootRepository
) {

    private val format = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm")

    fun notifyToDeveloperIfNeeded(villageId: Int, message: Message) {
        if (!message.shouldNotify()) return
        discordRepository.post(villageId, message.time.day, message.content.text)
//        slackRepository.post(villageId, message.time.day, message.content.text)
    }

    fun notifyToDeveloperTextIfNeeded(village: Village, text: String) {
        discordRepository.post(village.id, village.latestDay(), text)
//        slackRepository.post(village.id, village.latestDay(), text)
    }

    fun notifyParticipantEnoughToCustomerIfNeeded(village: Village) {
        if (village.participants.count != village.setting.personMin) return
        // 身内村は通知しない
        if (!village.setting.joinPassword.isNullOrBlank()) return

        val startDateTime = village.setting.startDatetime.format(format)
        val msg = "人数が揃いました。次回更新時に村が開始されます。\r\n村名：${village.name}\r\n開始予定：${startDateTime}"
        tweetRepository.tweet(msg, village.id)
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
        tweetRepository.tweet(msg, village.id)
        tootRepository.toot(msg, village.id)
    }

    fun notifyDaychangeToCustomer(villageId: Int, list: List<String>) =
        list.forEach {
            tweetRepository.tweet(it, villageId)
            tootRepository.toot(it, villageId)
        }
}