package com.ort.app.infrastructure.discord

import com.ort.app.domain.model.discord.DiscordRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class DiscordRepositoryImpl : DiscordRepository {

    companion object {
        private val logger = LoggerFactory.getLogger(DiscordRepositoryImpl::class.java)
    }

    @Value("\${discord.webhook-url:}")
    private lateinit var webhookUrl: String

    @Value("\${discord.master-userid:}")
    private lateinit var masterUserId: String

    override fun post(villageId: Int, day: Int, message: String) {
        if (webhookUrl.isEmpty()) return
        try {
            val restTemplate = RestTemplate()
            val request = Request(
                content = "<@!$masterUserId>\n<https://wolfort.net/wolf-mansion/village/$villageId>\n$message"
            )
            val formHeaders = HttpHeaders()
            formHeaders.contentType = MediaType.APPLICATION_JSON
            val formEntity = HttpEntity(request, formHeaders)
            restTemplate.exchange(webhookUrl, HttpMethod.POST, formEntity, String::class.java)
        } catch (e: Exception) {
            logger.error("discord投稿に失敗しました", e)
        }
    }

    override fun postToWebhook(
        webhookUrl: String,
        villageId: Int,
        message: String,
        shouldContainVillageUrl: Boolean
    ) {
        try {
            val restTemplate = RestTemplate()
            val content =
                if (shouldContainVillageUrl) "<https://wolfort.net/wolf-mansion/village/$villageId>\n$message"
                else message
            val request = Request(
                content = content,
                username = "WOLF MANSION ${villageId.toString().padStart(4, '0')}村通知"
            )
            val formHeaders = HttpHeaders()
            formHeaders.contentType = MediaType.APPLICATION_JSON
            val formEntity = HttpEntity(request, formHeaders)
            restTemplate.exchange(webhookUrl, HttpMethod.POST, formEntity, String::class.java)
        } catch (e: Exception) {
            logger.error("discord投稿に失敗しました", e)
        }
    }

    data class Request(
        val content: String,
        val username: String? = null,
    ) : java.io.Serializable
}