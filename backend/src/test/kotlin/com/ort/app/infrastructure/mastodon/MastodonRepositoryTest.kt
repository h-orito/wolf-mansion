package com.ort.app.infrastructure.mastodon

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MastodonRepositoryTest {

    @Autowired
    private lateinit var repository: MastodonRepository

    @Test
    fun test_toot() {
        val str = "新しい村が作成されました。"
        println(repository.toot(str, 1))
    }
}