package com.ort.app.domain.model.analyzer

interface AnalyzerMemoRepository {
    fun findByPlayerIdAndVillageId(
        playerId: Int,
        villageId: Int,
    ): AnalyzerMemo?

    fun save(
        playerId: Int,
        memo: AnalyzerMemo,
    )
}
