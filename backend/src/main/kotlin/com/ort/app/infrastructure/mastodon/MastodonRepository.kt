package com.ort.app.infrastructure.mastodon

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.ort.app.domain.model.toot.TootRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class MastodonRepository : TootRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        private const val VILLAGE_URL = "https://wolfort.net/wolf-mansion/village/"
    }

    @Value("\${app.debug}")
    private lateinit var debug: String

    @Value("\${mastodon.oauth.host:}")
    private lateinit var host: String

    @Value("\${mastodon.oauth.access-token:}")
    private lateinit var accessToken: String

    override fun toot(msg: String, villageId: Int) {
        toot("[WOLF MANSION] $msg\r\n$VILLAGE_URL$villageId")
    }

    data class Request(
        val status: String,
        val visibility: String = "unlisted" // ローカルタイムラインには流さない
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Response(
        val id: String
    )

    override fun toot(msg: String) {
        if (debug.toBoolean()) return

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(accessToken)
        val request = Request(
            status = "[自動通知]$msg"
        )
        val entity = HttpEntity<Request>(request, headers)
        val restTemplate = RestTemplate()
        try {
            restTemplate.exchange(
                "https://$host/api/v1/statuses",
                HttpMethod.POST,
                entity,
                Response::class.java,
            )
        } catch (e: Exception) {
            // 失敗してもスルー
            logger.warn("fail toot", e)
        }
    }
}