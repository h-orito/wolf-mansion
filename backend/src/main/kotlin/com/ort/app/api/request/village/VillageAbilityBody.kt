package com.ort.app.api.request.village

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "能力セットリクエスト")
data class VillageAbilityBody(
    @field:Schema(description = "能力主体のキャラ ID (役職によっては null)")
    val attackerCharaId: Int? = null,
    @field:Schema(description = "能力対象のキャラ ID (発動取消は null)")
    val targetCharaId: Int? = null,
    @field:Schema(description = "足音 (足音発生役職のみ、カンマ区切りの部屋番号 or 'なし')")
    val footstep: String? = null,
)
