package com.ort.app.domain.model.discord

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DiscordWebhookUrlTest {
    @Test
    fun `正規の Discord Webhook URL は valid`() {
        listOf(
            "https://discord.com/api/webhooks/123/abc",
            "https://discordapp.com/api/webhooks/123/abc",
            "https://ptb.discord.com/api/webhooks/123/abc",
            "https://canary.discord.com/api/webhooks/123/abc",
        ).forEach { url ->
            assertTrue(DiscordWebhookUrl.isValid(url), url)
        }
    }

    @Test
    fun `Discord 以外のホストは invalid`() {
        listOf(
            "https://example.com/api/webhooks/123/abc",
            "https://169.254.169.254/latest/meta-data/",
            "https://localhost:8089/wolf-mansion/",
        ).forEach { url ->
            assertFalse(DiscordWebhookUrl.isValid(url), url)
        }
    }

    @Test
    fun `https 以外のスキームは invalid`() {
        listOf(
            "http://discord.com/api/webhooks/123/abc",
            "ftp://discord.com/api/webhooks/123/abc",
        ).forEach { url ->
            assertFalse(DiscordWebhookUrl.isValid(url), url)
        }
    }

    @Test
    fun `ホスト偽装を狙った URL は invalid`() {
        listOf(
            // suffix が似ているだけの別ドメイン
            "https://evildiscord.com/api/webhooks/123/abc",
            "https://discord.com.evil.com/api/webhooks/123/abc",
            // userinfo 部に discord.com を置いた実ホスト evil.com
            "https://discord.com@evil.com/api/webhooks/123/abc",
        ).forEach { url ->
            assertFalse(DiscordWebhookUrl.isValid(url), url)
        }
    }

    @Test
    fun `URL として不正な文字列は invalid`() {
        listOf(
            "not a url",
            "https://",
        ).forEach { url ->
            assertFalse(DiscordWebhookUrl.isValid(url), url)
        }
    }
}
