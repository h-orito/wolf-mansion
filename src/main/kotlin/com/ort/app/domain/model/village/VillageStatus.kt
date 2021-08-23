package com.ort.app.domain.model.village

import com.ort.dbflute.allcommon.CDef

data class VillageStatus(
    val code: String,
    val name: String
) {
    companion object {
        // 決着がついていないステータス
        val notSettledStatusList = listOf(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中)

        // 決着がついたステータス
        val settledStatusList = listOf(CDef.VillageStatus.エピローグ, CDef.VillageStatus.終了)

        // 終了していないステータス
        val notFinishedStatusList = listOf(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.エピローグ)
    }

    constructor(cdef: CDef.VillageStatus) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.VillageStatus =
        CDef.VillageStatus.codeOf(code) ?: throw IllegalStateException("unknown status: $code")

    fun isRecruiting(): Boolean = toCdef() == CDef.VillageStatus.募集中
    fun isProgress(): Boolean = toCdef() == CDef.VillageStatus.進行中
    fun isCanceled(): Boolean = toCdef() == CDef.VillageStatus.廃村
}