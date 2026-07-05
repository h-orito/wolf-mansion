package com.ort.app.api.village.request

import com.ort.app.fw.exception.WolfMansionValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class VillageNotificationRequestTest {
    private fun request(url: String) = VillageNotificationRequest(webhookUrl = url)

    @Test
    fun `正規の Discord Webhook URL は通る`() {
        listOf(
            "https://discord.com/api/webhooks/123/abc",
            "https://discordapp.com/api/webhooks/123/abc",
            "https://ptb.discord.com/api/webhooks/123/abc",
            "https://canary.discord.com/api/webhooks/123/abc",
        ).forEach { url ->
            assertEquals(url, request(url).validatedWebhookUrl())
        }
    }

    @Test
    fun `Discord 以外のホストは 400 (ValidationException)`() {
        listOf(
            "https://example.com/api/webhooks/123/abc",
            "https://169.254.169.254/latest/meta-data/",
            "https://localhost:8089/wolf-mansion/",
        ).forEach { url ->
            assertThrows<WolfMansionValidationException> { request(url).validatedWebhookUrl() }
        }
    }

    @Test
    fun `https 以外のスキームは 400 (ValidationException)`() {
        listOf(
            "http://discord.com/api/webhooks/123/abc",
            "ftp://discord.com/api/webhooks/123/abc",
        ).forEach { url ->
            assertThrows<WolfMansionValidationException> { request(url).validatedWebhookUrl() }
        }
    }

    @Test
    fun `ホスト偽装を狙った URL は 400 (ValidationException)`() {
        listOf(
            // suffix が似ているだけの別ドメイン
            "https://evildiscord.com/api/webhooks/123/abc",
            "https://discord.com.evil.com/api/webhooks/123/abc",
            // userinfo 部に discord.com を置いた実ホスト evil.com
            "https://discord.com@evil.com/api/webhooks/123/abc",
        ).forEach { url ->
            assertThrows<WolfMansionValidationException> { request(url).validatedWebhookUrl() }
        }
    }

    @Test
    fun `URL として不正な文字列は 400 (ValidationException)`() {
        listOf(
            "not a url",
            "https://",
        ).forEach { url ->
            assertThrows<WolfMansionValidationException> { request(url).validatedWebhookUrl() }
        }
    }
}
