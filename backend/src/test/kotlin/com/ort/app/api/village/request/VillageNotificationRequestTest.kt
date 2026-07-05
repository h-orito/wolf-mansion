package com.ort.app.api.village.request

import com.ort.app.fw.exception.WolfMansionValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class VillageNotificationRequestTest {
    private fun request(url: String) = VillageNotificationRequest(webhookUrl = url)

    @Test
    fun `正規の Discord Webhook URL は通る`() {
        val url = "https://discord.com/api/webhooks/123/abc"
        assertEquals(url, request(url).validatedWebhookUrl())
    }

    @Test
    fun `前後の空白は取り除いて検証・保存する`() {
        assertEquals(
            "https://discord.com/api/webhooks/123/abc",
            request(" https://discord.com/api/webhooks/123/abc ").validatedWebhookUrl(),
        )
    }

    @Test
    fun `Discord の Webhook URL でなければ 400 (ValidationException)`() {
        listOf(
            "https://169.254.169.254/latest/meta-data/",
            "http://discord.com/api/webhooks/123/abc",
            "https://discord.com@evil.com/api/webhooks/123/abc",
            "not a url",
        ).forEach { url ->
            val e = assertThrows<WolfMansionValidationException> { request(url).validatedWebhookUrl() }
            assertEquals("webhookUrl", e.fieldErrors.single().field)
        }
    }
}
