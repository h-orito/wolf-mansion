package com.ort.app.api.response.myself

import com.ort.app.domain.model.situation.participant.ParticipantCommitSituation
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "自分のコミット状態")
data class MyselfCommitView(
    @field:Schema(description = "コミット可能か (村設定で有効 + 進行中 + 生存中 + 非ダミー)")
    val isAvailable: Boolean,
    @field:Schema(description = "現時点でコミット中か")
    val isCommitting: Boolean,
) {
    constructor(situation: ParticipantCommitSituation) : this(
        isAvailable = situation.isAvailableCommit,
        isCommitting = situation.isCommitting,
    )
}
