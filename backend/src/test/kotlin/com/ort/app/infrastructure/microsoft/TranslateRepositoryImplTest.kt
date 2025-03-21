package com.ort.app.infrastructure.microsoft

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class TranslateRepositoryImplTest {

    @Autowired
    private lateinit var repository: TranslateRepositoryImpl

    @Test
    fun test_reTranslate() {
        val str = "普通に考えたら投票先は20,21……？\n転生先を吊るのは、そこそこリスキーかな、とか。\n村利の役職かどうかもわからないし……"
        println(repository.reTranslate(str))
    }
}