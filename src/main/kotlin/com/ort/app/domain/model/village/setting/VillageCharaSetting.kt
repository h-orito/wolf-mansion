package com.ort.app.domain.model.village.setting

data class VillageCharaSetting(
    val isOriginalCharachip: Boolean,
    val dummyCharaId: Int,
    val charachipIds: List<Int>
)