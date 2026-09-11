package com.ort.app.domain.model.discord

interface DiscordRepository {
    fun post(
        villageId: Int,
        day: Int,
        message: String,
    )

    fun postToWebhook(
        webhookUrl: String,
        villageId: Int,
        message: String,
        shouldContainVillageUrl: Boolean = true,
    )

    /**
     * 管理者向け通知。master webhook (`discord.webhook-url`) へ master mention 付きで送る。
     * 村に紐づかない運用アラート (攻撃検知等) 用で、村 URL は埋め込まない。
     * webhook 未設定なら no-op。ベストエフォート (失敗してもログのみ)。
     */
    fun postToMaster(message: String)
}
