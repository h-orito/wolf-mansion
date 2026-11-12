package com.ort.app.application.service

import com.ort.app.domain.model.randomkeyword.RandomContent
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywordRepository
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class RandomKeywordServiceTest {
    private val keywords =
        RandomKeywords(list = listOf(RandomKeyword(id = 1, keyword = "果物", contents = listOf(RandomContent(message = "りんご")))))

    @Test
    @DisplayName("発言に [[ が無ければランダムキーワードを読まない")
    fun skipsLoadWithoutMarker() {
        val repository = mock(RandomKeywordRepository::class.java)
        val result = RandomKeywordService(repository).findRandomKeywordsIfUsedIn("おはよう\n[who]]")
        assertTrue(result.list.isEmpty())
        verify(repository, never()).findRandomKeywords()
    }

    @Test
    @DisplayName("発言に [[ があればランダムキーワードを全件読む")
    fun loadsWithMarker() {
        val repository = mock(RandomKeywordRepository::class.java)
        given(repository.findRandomKeywords()).willReturn(keywords)
        val result = RandomKeywordService(repository).findRandomKeywordsIfUsedIn("今日の果物は[[果物]]")
        assertEquals(keywords, result)
        verify(repository).findRandomKeywords()
    }
}
