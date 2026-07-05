package com.ort.app.api.village.request

import com.ort.app.domain.model.discord.DiscordWebhookUrl
import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

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
        private const val INVALID_MESSAGE = "Discord の Webhook URL (https://discord.com/api/webhooks/...) を指定してください"
    }

    /** 検証済みの webhookUrl を返す。検証理由は [DiscordWebhookUrl] を参照。 */
    fun validatedWebhookUrl(): String {
        val url = webhookUrl!!.trim()
        if (!DiscordWebhookUrl.isValid(url)) {
            throw WolfMansionValidationException(listOf(FieldErrorItem("webhookUrl", INVALID_MESSAGE)))
        }
        return url
    }
}
