package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(description = "コミット (確定) リクエスト")
data class VillageCommitBody(
    @field:NotNull
    @field:Schema(description = "true でコミット、false で取り消し")
    val commit: Boolean,
)
