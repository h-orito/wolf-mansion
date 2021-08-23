package com.ort.app.domain.model.randomkeyword

interface RandomKeywordRepository {

    fun findRandomKeywords(): RandomKeywords

    fun findRandomKeyword(id: Int): RandomKeyword?

    fun findRandomKeyword(keyword: String): RandomKeyword?

    fun register(keyword: RandomKeyword): RandomKeyword

    fun delete(keyword: String)

    fun update(keyword: RandomKeyword)
}