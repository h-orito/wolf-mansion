package com.ort.app.application.service

import com.ort.app.domain.model.analyzer.AnalyzerMemo
import com.ort.app.domain.model.analyzer.AnalyzerMemoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnalyzerMemoService(
    private val analyzerMemoRepository: AnalyzerMemoRepository,
) {
    /** 未保存の場合は空のメモを返す。 */
    fun findAnalyzerMemo(
        playerId: Int,
        villageId: Int,
    ): AnalyzerMemo =
        analyzerMemoRepository.findByPlayerIdAndVillageId(playerId, villageId)
            ?: AnalyzerMemo.empty(villageId)

    @Transactional
    fun saveAnalyzerMemo(
        playerId: Int,
        memo: AnalyzerMemo,
    ) = analyzerMemoRepository.save(playerId, memo)
}
