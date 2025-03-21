package com.ort.app.api.request

data class VillageAbilityForm(
    val attackerCharaId: Int? = null,
    val targetCharaId: Int? = null,
    val footstep: String? = null
)