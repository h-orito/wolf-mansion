package com.ort.app.infrastructure.discord

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DiscordRepositoryImplTest {

    @Autowired
    private lateinit var repository: DiscordRepositoryImpl

    @Test
    fun test_post() {
        repository.post(
            villageId = 1,
            day = 1,
            message = " ＠国主\nあれがこうです"
        )
    }

    @Test
    fun test_postToWebhook() {
        repository.postToWebhook(
            webhookUrl = "hoge",
            villageId = 1,
            message = "村が開始されました。"
        )
    }
}