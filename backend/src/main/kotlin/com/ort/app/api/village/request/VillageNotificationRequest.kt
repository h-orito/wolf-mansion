package com.ort.app.api.village.request

import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.net.URI
import java.net.URISyntaxException

/** Discord 通知設定の保存。保存時に webhook へテスト通知を送る。 */
data class VillageNotificationRequest(
    @field:NotBlank
    val webhookUrl: String? = null,
    /** 村が開始したら通知 */
    val villageStart: Boolean? = null,
    /** 日付更新で通知 */
    val villageDaychange: Boolean? = null,
    /** エピローグを迎えたら通知 */
    val villageEpilogue: Boolean? = null,
    /** 新着秘話で通知 */
    val secretSay: Boolean? = null,
    /** 新着役職窓発言で通知 */
    val abilitySay: Boolean? = null,
    /** 自分宛アンカーで通知 */
    val anchorSay: Boolean? = null,
    /** キーワード通知 (スペース区切り) */
    @field:Size(max = 30)
    val keyword: String? = null,
) {
    companion object {
        private val ALLOWED_HOSTS = listOf("discord.com", "discordapp.com")
        private const val INVALID_MESSAGE = "Discord の Webhook URL (https://discord.com/api/webhooks/...) を指定してください"
    }

    /**
     * 検証済みの webhookUrl を返す。保存した URL へはサーバから外向き POST を送るため、
     * Discord 以外のホストを許可すると内部サービスを標的にしたブラインド SSRF に悪用できる。
     * https かつ Discord のホストに限定する。
     */
    fun validatedWebhookUrl(): String {
        val url = webhookUrl!!
        val uri =
            try {
                URI(url)
            } catch (e: URISyntaxException) {
                throw WolfMansionValidationException(listOf(FieldErrorItem("webhookUrl", INVALID_MESSAGE)))
            }
        val host = uri.host?.lowercase()
        val isAllowed =
            uri.scheme.equals("https", ignoreCase = true) &&
                host != null &&
                ALLOWED_HOSTS.any { host == it || host.endsWith(".$it") }
        if (!isAllowed) {
            throw WolfMansionValidationException(listOf(FieldErrorItem("webhookUrl", INVALID_MESSAGE)))
        }
        return url
    }
}
