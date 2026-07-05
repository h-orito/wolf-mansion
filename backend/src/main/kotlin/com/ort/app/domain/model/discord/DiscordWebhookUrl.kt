package com.ort.app.domain.model.discord

import java.net.URI
import java.net.URISyntaxException

/**
 * Discord Webhook URL の検証。保存した URL へはサーバから外向き POST を送るため、
 * Discord 以外のホストを許可すると内部サービスを標的にしたブラインド SSRF に悪用できる。
 * https かつ Discord のホスト (サブドメイン含む) に限定する。
 */
object DiscordWebhookUrl {
    private val ALLOWED_HOSTS = listOf("discord.com", "discordapp.com")

    fun isValid(url: String): Boolean {
        val uri =
            try {
                URI(url)
            } catch (e: URISyntaxException) {
                return false
            }
        val host = uri.host?.lowercase() ?: return false
        return uri.scheme.equals("https", ignoreCase = true) &&
            ALLOWED_HOSTS.any { host == it || host.endsWith(".$it") }
    }
}
