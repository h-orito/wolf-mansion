package com.ort.app.api.village.response

import com.ort.app.domain.model.village.participant.VillageParticipant
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 能力セットの候補 (襲撃対象 / 足音)。ビューア本人の役職知識に基づくため認証必須 API でのみ返す。
 */
data class AbilityCandidatesView(
    val targets: List<TargetView>,
    val footsteps: List<String>,
) {
    @Schema(name = "AbilityCandidatesTarget")
    data class TargetView(
        val charaId: Int,
        val name: String,
    ) {
        constructor(participant: VillageParticipant) : this(
            charaId = participant.charaId,
            name = participant.name(),
        )
    }
}
