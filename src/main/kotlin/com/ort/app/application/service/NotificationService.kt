package com.ort.app.application.service

import com.ort.app.domain.model.discord.DiscordRepository
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.slack.SlackRepository
import com.ort.app.domain.model.village.Village
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val discordRepository: DiscordRepository,
    private val slackRepository: SlackRepository
) {

    fun postIfNeeded(villageId: Int, message: Message) {
        if (!message.shouldNotify()) return
        discordRepository.post(villageId, message.time.day, message.content.text)
        slackRepository.post(villageId, message.time.day, message.content.text)
    }

    fun postTextIfNeeded(village: Village, text: String) {
        discordRepository.post(village.id, village.latestDay(), text)
        slackRepository.post(village.id, village.latestDay(), text)
    }
}