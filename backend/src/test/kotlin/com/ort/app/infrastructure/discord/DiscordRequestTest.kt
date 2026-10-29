package com.ort.app.infrastructure.discord

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

class DiscordRequestTest {
    // RestTemplate の JSON 変換と同じ設定
    private val objectMapper = Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()

    private fun toJson(request: DiscordRepositoryImpl.Request) = objectMapper.readTree(objectMapper.writeValueAsString(request))

    @Test
    @DisplayName("村の発言の通知: 開発者へのメンションだけを許可し、本文のメンション・書式は無効にする")
    fun postRequest() {
        val json =
            toJson(DiscordRepositoryImpl.createPostRequest("123", 10, "@everyone <@&456> [偽](https://evil.example) **太字**"))
        assertEquals(
            "<@!123>\n<https://wolfort.net/wolf-mansion/village/10>\n" +
                "\\@everyone \\<\\@&456\\> \\[偽\\]\\(https\\:/" + "/evil.example\\) \\*\\*太字\\*\\*",
            json["content"].asText(),
        )
        assertEquals(0, json["allowed_mentions"]["parse"].size())
        assertEquals(listOf("123"), json["allowed_mentions"]["users"].map { it.asText() })
        assertFalse(json.has("allowedMentions"))
    }

    @Test
    @DisplayName("管理者への通知: ログイン名などのメンション・書式は無効にする")
    fun postToMasterRequest() {
        val json = toJson(DiscordRepositoryImpl.createPostToMasterRequest("123", "アカウント: @everyone"))
        assertEquals("<@!123>\nアカウント\\: \\@everyone", json["content"].asText())
        assertEquals(0, json["allowed_mentions"]["parse"].size())
        assertEquals(listOf("123"), json["allowed_mentions"]["users"].map { it.asText() })

        val noMaster = toJson(DiscordRepositoryImpl.createPostToMasterRequest("", "@here"))
        assertEquals("\\@here", noMaster["content"].asText())
        assertEquals(0, noMaster["allowed_mentions"]["parse"].size())
        assertFalse(noMaster["allowed_mentions"].has("users"))
    }

    @Test
    @DisplayName("村の通知 webhook: メンションを一切解釈させない")
    fun webhookRequest() {
        val json = toJson(DiscordRepositoryImpl.Request(content = "@everyone", username = "通知"))
        assertEquals(0, json["allowed_mentions"]["parse"].size())
    }
}
