package com.ort.app.infrastructure.slack

import com.ort.app.domain.model.slack.SlackRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class SlackRepositoryImpl : SlackRepository {

    companion object {
        private val logger = LoggerFactory.getLogger(SlackRepositoryImpl::class.java)
    }

    @Value("\${slack.webhook-url:}")
    private lateinit var slackWebhookUrl: String


    override fun post(villageId: Int, day: Int, message: String) {
        if (slackWebhookUrl.isEmpty()) return
        try {
            val restTemplate = RestTemplate()
            val request = "{\"text\": \"<@U8Z40QDUM> vid: $villageId, message: \n$message\"}"
            val formHeaders = HttpHeaders()
            formHeaders.contentType = MediaType.APPLICATION_JSON
            val formEntity = HttpEntity(request, formHeaders)
            restTemplate.exchange(slackWebhookUrl, HttpMethod.POST, formEntity, String::class.java)
        } catch (e: Exception) {
            logger.error("slack投稿に失敗しました", e)
        }
    }
}