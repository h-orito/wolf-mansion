package com.ort.app.application.service

import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywordRepository
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class RandomKeywordService(
    val randomKeywordRepository: RandomKeywordRepository
) {

    fun findRandomKeywords(): RandomKeywords = randomKeywordRepository.findRandomKeywords()

    fun findRandomKeyword(id: Int): RandomKeyword? = randomKeywordRepository.findRandomKeyword(id)

    fun findRandomKeyword(keyword: String): RandomKeyword? = randomKeywordRepository.findRandomKeyword(keyword)

    fun registerRandomKeyword(keyword: RandomKeyword): RandomKeyword {
        findRandomKeyword(keyword.keyword)?.let {
            throw WolfMansionBusinessException("すでに同じキーワードで登録されています")
        }
        return randomKeywordRepository.register(keyword)
    }

    fun deleteRandomKeyword(keyword: String) = randomKeywordRepository.delete(keyword)

    fun updateRandomKeyword(keyword: RandomKeyword) = randomKeywordRepository.update(keyword)
}