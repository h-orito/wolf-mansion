package com.ort.app.api.village.request

/**
 * 能力セット。役職の入力パターンにより使うフィールドが異なる
 * (襲撃 = attacker + target + footstep / 対象選択 = target / 調査・徘徊 = footstep)。
 */
data class VillageAbilityRequest(
    val attackerCharaId: Int? = null,
    val targetCharaId: Int? = null,
    /** 足音 (調査対象、または徘徊の通過部屋 CSV・「なし」) */
    val footstep: String? = null,
)
