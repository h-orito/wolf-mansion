package com.ort.app.domain.model.analyzer

/** 推理補助 (analyzer) のプレイヤー別メモ一式。プレイヤー本人だけが読み書きできる。 */
data class AnalyzerMemo(
    val villageId: Int,
    val wholeMemo: String,
    val participantMemos: List<AnalyzerParticipantMemo>,
    val dailyMemos: List<AnalyzerDailyMemo>,
    val dailyFootstepMemos: List<AnalyzerDailyFootstepMemo>,
) {
    companion object {
        fun empty(villageId: Int): AnalyzerMemo =
            AnalyzerMemo(
                villageId = villageId,
                wholeMemo = "",
                participantMemos = emptyList(),
                dailyMemos = emptyList(),
                dailyFootstepMemos = emptyList(),
            )
    }
}

data class AnalyzerParticipantMemo(
    val participantId: Int,
    val memo: String,
    /** 表示色 (6桁hex、#なし) */
    val color: String,
)

data class AnalyzerDailyMemo(
    val day: Int,
    val memo: String,
)

data class AnalyzerDailyFootstepMemo(
    val day: Int,
    val footsteps: List<AnalyzerFootstepMemo>,
)

data class AnalyzerFootstepMemo(
    /** 足音の部屋番号表記 (例: "12,17,18") */
    val footstep: String,
    /** 表示色 (6桁hex、#なし) */
    val color: String,
    /** 部屋グリッドに足音ラインを表示するか */
    val show: Boolean,
    val memo: String,
)
