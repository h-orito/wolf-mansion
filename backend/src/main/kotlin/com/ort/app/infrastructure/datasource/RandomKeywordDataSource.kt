package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.randomkeyword.RandomContent
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywordRepository
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.dbflute.bsbhv.BsRandomKeywordBhv
import com.ort.dbflute.cbean.RandomKeywordCB
import com.ort.dbflute.exbhv.RandomContentBhv
import com.ort.dbflute.exentity.RandomKeyword as DbRandomKeyword
import com.ort.dbflute.exentity.RandomContent as DbRandomContent
import org.springframework.stereotype.Repository

@Repository
class RandomKeywordDataSource(
    private val randomKeywordBhv: BsRandomKeywordBhv,
    private val randomContentBhv: RandomContentBhv
) : RandomKeywordRepository {

    override fun findRandomKeywords(): RandomKeywords {
        val randomKeywordList = randomKeywordBhv.selectList {
            it.query().addOrderBy_RandomKeywordId_Asc()
        }
        randomKeywordBhv.loadRandomContent(randomKeywordList) {
            it.query().addOrderBy_RandomContentId_Asc()
        }
        return mapKeywords(randomKeywordList)
    }

    override fun findRandomKeyword(id: Int): RandomKeyword? {
        val optRandomKeyword = randomKeywordBhv.selectEntity {
            it.query().setRandomKeywordId_Equal(id)
            it.query().addOrderBy_RandomKeywordId_Asc()
        }
        if (!optRandomKeyword.isPresent) return null
        val randomKeyword = optRandomKeyword.get()
        randomKeywordBhv.loadRandomContent(randomKeyword) {
            it.query().addOrderBy_RandomContentId_Asc()
        }
        return mapRandomKeyword(randomKeyword)
    }

    override fun findRandomKeyword(keyword: String): RandomKeyword? {
        val optRandomKeyword = randomKeywordBhv.selectEntity {
            it.query().setKeyword_Equal(keyword)
            it.query().addOrderBy_RandomKeywordId_Asc()
        }
        if (!optRandomKeyword.isPresent) return null
        val randomKeyword = optRandomKeyword.get()
        randomKeywordBhv.loadRandomContent(randomKeyword) {
            it.query().addOrderBy_RandomContentId_Asc()
        }
        return mapRandomKeyword(randomKeyword)
    }

    override fun register(keyword: RandomKeyword): RandomKeyword {
        val dbRandomKeyword = DbRandomKeyword()
        dbRandomKeyword.keyword = keyword.keyword
        randomKeywordBhv.insert(dbRandomKeyword)
        val id = dbRandomKeyword.randomKeywordId
        keyword.contents.forEach {
            val dbRandomContent = DbRandomContent()
            dbRandomContent.randomKeywordId = id
            dbRandomContent.randomMessage = it.message
            randomContentBhv.insert(dbRandomContent)
        }
        return findRandomKeyword(keyword = keyword.keyword)!!
    }

    override fun delete(keyword: String) {
        randomContentBhv.queryDelete{
            it.query().queryRandomKeyword().setKeyword_Equal(keyword)
        }
        randomKeywordBhv.queryDelete {
           it.query().setKeyword_Equal(keyword)
        }
    }

    override fun update(keyword: RandomKeyword) {
        val exists = findRandomKeyword(keyword = keyword.keyword) ?: return
        randomContentBhv.queryDelete{
            it.query().queryRandomKeyword().setKeyword_Equal(keyword.keyword)
        }
        keyword.contents.forEach {
            val dbRandomContent = DbRandomContent()
            dbRandomContent.randomKeywordId = exists.id
            dbRandomContent.randomMessage = it.message
            randomContentBhv.insert(dbRandomContent)
        }
    }

    private fun mapKeywords(randomKeywordList: List<DbRandomKeyword>): RandomKeywords {
        return RandomKeywords(list = randomKeywordList.map { mapRandomKeyword(it) })
    }

    private fun mapRandomKeyword(randomKeyword: DbRandomKeyword): RandomKeyword {
        return RandomKeyword(
            id = randomKeyword.randomKeywordId,
            keyword = randomKeyword.keyword,
            contents = randomKeyword.randomContentList.map { RandomContent(message = it.randomMessage) }
        )
    }
}