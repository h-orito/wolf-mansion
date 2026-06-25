package com.ort.app.api.village.request

import jakarta.validation.constraints.NotNull

/** 投票セット。対象の妥当性は domain (VoteDomainService.assertVote) が検証する。 */
data class VillageVoteRequest(
    @field:NotNull
    val targetCharaId: Int? = null,
)
