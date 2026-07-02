package com.ort.app.api.analyzer.request

import com.ort.app.domain.model.analyzer.AnalyzerDailyFootstepMemo
import com.ort.app.domain.model.analyzer.AnalyzerDailyMemo
import com.ort.app.domain.model.analyzer.AnalyzerFootstepMemo
import com.ort.app.domain.model.analyzer.AnalyzerMemo
import com.ort.app.domain.model.analyzer.AnalyzerParticipantMemo
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

private const val COLOR_PATTERN = "^[0-9a-fA-F]{6}$"

/** 推理補助メモの保存リクエスト。メモ一式をまるごと置き換える。 */
data class AnalyzerMemoUpdateRequest(
    @field:NotNull
    @field:Size(max = 10000)
    val wholeMemo: String? = null,
    @field:NotNull
    @field:Size(max = 200)
    @field:Valid
    val participantMemos: List<AnalyzerParticipantMemoRequest>? = null,
    @field:NotNull
    @field:Size(max = 100)
    @field:Valid
    val dailyMemos: List<AnalyzerDailyMemoRequest>? = null,
    @field:NotNull
    @field:Size(max = 100)
    @field:Valid
    val dailyFootstepMemos: List<AnalyzerDailyFootstepMemoRequest>? = null,
) {
    fun toModel(villageId: Int): AnalyzerMemo =
        AnalyzerMemo(
            villageId = villageId,
            wholeMemo = wholeMemo!!,
            participantMemos = participantMemos!!.map { it.toModel() },
            dailyMemos = dailyMemos!!.map { it.toModel() },
            dailyFootstepMemos = dailyFootstepMemos!!.map { it.toModel() },
        )
}

data class AnalyzerParticipantMemoRequest(
    @field:NotNull
    val participantId: Int? = null,
    @field:NotNull
    @field:Size(max = 1000)
    val memo: String? = null,
    @field:NotNull
    @field:Pattern(regexp = COLOR_PATTERN)
    val color: String? = null,
) {
    fun toModel(): AnalyzerParticipantMemo =
        AnalyzerParticipantMemo(
            participantId = participantId!!,
            memo = memo!!,
            color = color!!,
        )
}

data class AnalyzerDailyMemoRequest(
    @field:NotNull
    val day: Int? = null,
    @field:NotNull
    @field:Size(max = 10000)
    val memo: String? = null,
) {
    fun toModel(): AnalyzerDailyMemo =
        AnalyzerDailyMemo(
            day = day!!,
            memo = memo!!,
        )
}

data class AnalyzerDailyFootstepMemoRequest(
    @field:NotNull
    val day: Int? = null,
    @field:NotNull
    @field:Size(max = 100)
    @field:Valid
    val footsteps: List<AnalyzerFootstepMemoRequest>? = null,
) {
    fun toModel(): AnalyzerDailyFootstepMemo =
        AnalyzerDailyFootstepMemo(
            day = day!!,
            footsteps = footsteps!!.map { it.toModel() },
        )
}

data class AnalyzerFootstepMemoRequest(
    @field:NotNull
    @field:Size(max = 100)
    val footstep: String? = null,
    @field:NotNull
    @field:Pattern(regexp = COLOR_PATTERN)
    val color: String? = null,
    @field:NotNull
    val show: Boolean? = null,
    @field:NotNull
    @field:Size(max = 1000)
    val memo: String? = null,
) {
    fun toModel(): AnalyzerFootstepMemo =
        AnalyzerFootstepMemo(
            footstep = footstep!!,
            color = color!!,
            show = show!!,
            memo = memo!!,
        )
}
