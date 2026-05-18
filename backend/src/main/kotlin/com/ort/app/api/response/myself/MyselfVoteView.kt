package com.ort.app.api.response.myself

import com.ort.app.domain.model.situation.participant.ParticipantVoteSituation
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "自分の投票状態")
data class MyselfVoteView(
    @field:Schema(description = "投票可能か (進行中 + 2 日目以降 + 生存中)")
    val canVote: Boolean,
    @field:Schema(description = "投票対象候補のキャラ ID 一覧 (生存者)")
    val targetCharaIds: List<Int>,
    @field:Schema(description = "現在の投票対象キャラ ID (未投票なら null)")
    val targetCharaId: Int?,
) {
    constructor(situation: ParticipantVoteSituation) : this(
        canVote = situation.canVote,
        targetCharaIds = situation.targetList.map { it.charaId },
        targetCharaId = situation.target?.charaId,
    )
}
