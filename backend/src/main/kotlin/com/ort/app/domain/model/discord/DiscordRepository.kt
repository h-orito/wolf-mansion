package com.ort.app.domain.model.discord

interface DiscordRepository {

    fun post(villageId: Int, day: Int, message: String)

    fun postToWebhook(
        webhookUrl: String,
        villageId: Int,
        message: String,
        shouldContainVillageUrl: Boolean = true
    )
}