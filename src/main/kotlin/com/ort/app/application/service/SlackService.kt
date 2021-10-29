package com.ort.app.application.service

import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.slack.SlackRepository
import org.springframework.stereotype.Service

@Service
class SlackService(
    private val slackRepository: SlackRepository
) {

    fun postIfNeeded(villageId: Int, message: Message) {
        if (!message.shouldPostSlack()) return
        slackRepository.post(villageId, message.time.day, message.content.text)
    }
}