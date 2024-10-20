package com.ort.app.domain.model.village.setting

data class VillageCharaSetting(
    val isOriginalCharachip: Boolean,
    val dummyCharaId: Int,
    val dummyDay1Message: String?,
    val charachipIds: List<Int>
)