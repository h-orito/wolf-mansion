package com.ort.app.application.service

import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywordRepository
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RandomKeywordService(
    val randomKeywordRepository: RandomKeywordRepository,
) {
    fun findRandomKeywords(text: String? = null): RandomKeywords {
        val keywords = randomKeywordRepository.findRandomKeywords()
        return if (text.isNullOrBlank()) keywords else keywords.filterBy(text)
    }

    fun findRandomKeyword(id: Int): RandomKeyword? = randomKeywordRepository.findRandomKeyword(id)

    fun findRandomKeyword(keyword: String): RandomKeyword? = randomKeywordRepository.findRandomKeyword(keyword)

    // 書き込みは複数テーブル (RANDOM_KEYWORD + RANDOM_CONTENT) にまたがるため、
    // 途中で失敗した際に部分挿入が残らないようトランザクション境界をここに置く。
    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun registerRandomKeyword(keyword: RandomKeyword): RandomKeyword {
        findRandomKeyword(keyword.keyword)?.let {
            throw WolfMansionBusinessException("すでに同じキーワードで登録されています")
        }
        return randomKeywordRepository.register(keyword)
    }

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun deleteRandomKeyword(keyword: String) = randomKeywordRepository.delete(keyword)

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun updateRandomKeyword(keyword: RandomKeyword) = randomKeywordRepository.update(keyword)
}
