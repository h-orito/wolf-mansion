package com.ort.app.infrastructure.datasource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ort.app.domain.model.analyzer.AnalyzerMemo
import com.ort.app.domain.model.analyzer.AnalyzerMemoRepository
import com.ort.dbflute.exbhv.AnalyzerMemoBhv
import org.dbflute.exception.EntityAlreadyExistsException
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
        val existingId = selectId(playerId, memo.villageId)
        if (existingId != null) {
            update(existingId, memoJson)
            return
        }
        val entity = DbAnalyzerMemo()
        entity.playerId = playerId
        entity.villageId = memo.villageId
        entity.memoJson = memoJson
        try {
            analyzerMemoBhv.insert(entity)
        } catch (e: EntityAlreadyExistsException) {
            // 初回保存の並走で UNIQUE 制約に負けた場合は後勝ちの update に切り替える
            val id = selectId(playerId, memo.villageId) ?: throw e
            update(id, memoJson)
        }
    }

    private fun selectId(
        playerId: Int,
        villageId: Int,
    ): Int? {
        val optEntity =
            analyzerMemoBhv.selectEntity {
                it.query().setPlayerId_Equal(playerId)
                it.query().setVillageId_Equal(villageId)
            }
        return if (optEntity.isPresent) optEntity.get().analyzerMemoId else null
    }

    private fun update(
        analyzerMemoId: Int,
        memoJson: String,
    ) {
        // PK 指定の partial update。セットした列 (memo_json + 共通更新列) のみ更新される
        val entity = DbAnalyzerMemo()
        entity.analyzerMemoId = analyzerMemoId
        entity.memoJson = memoJson
        analyzerMemoBhv.update(entity)
    }
}
