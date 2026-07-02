package com.ort.app.infrastructure.datasource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ort.app.domain.model.analyzer.AnalyzerMemo
import com.ort.app.domain.model.analyzer.AnalyzerMemoRepository
import com.ort.dbflute.exbhv.AnalyzerMemoBhv
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.AnalyzerMemo as DbAnalyzerMemo

@Repository
class AnalyzerMemoDataSource(
    private val analyzerMemoBhv: AnalyzerMemoBhv,
    private val objectMapper: ObjectMapper,
) : AnalyzerMemoRepository {
    override fun findByPlayerIdAndVillageId(
        playerId: Int,
        villageId: Int,
    ): AnalyzerMemo? {
        val optEntity =
            analyzerMemoBhv.selectEntity {
                it.query().setPlayerId_Equal(playerId)
                it.query().setVillageId_Equal(villageId)
            }
        if (!optEntity.isPresent) return null
        val entity = optEntity.get()
        // villageId はカラム値を正とする (JSON 内の値は保存時のスナップショット)
        return objectMapper.readValue<AnalyzerMemo>(entity.memoJson).copy(villageId = entity.villageId)
    }

    override fun save(
        playerId: Int,
        memo: AnalyzerMemo,
    ) {
        val memoJson = objectMapper.writeValueAsString(memo)
        val existing =
            analyzerMemoBhv.selectEntity {
                it.query().setPlayerId_Equal(playerId)
                it.query().setVillageId_Equal(memo.villageId)
            }
        if (existing.isPresent) {
            val entity = DbAnalyzerMemo()
            entity.analyzerMemoId = existing.get().analyzerMemoId
            entity.memoJson = memoJson
            analyzerMemoBhv.update(entity)
        } else {
            val entity = DbAnalyzerMemo()
            entity.playerId = playerId
            entity.villageId = memo.villageId
            entity.memoJson = memoJson
            analyzerMemoBhv.insert(entity)
        }
    }
}
