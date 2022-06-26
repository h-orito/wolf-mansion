package com.ort.app.domain.model.ability

data class Ability(
    val day: Int,
    val type: AbilityType,
    val charaId: Int,
    val attackerCharaId: Int? = null,
    val targetCharaId: Int? = null,
    val targetFootstep: String? = null
) {
    fun isSame(other: Ability): Boolean {
        return day == other.day
                && type.code == other.type.code
                && charaId == other.charaId
                && attackerCharaId == other.attackerCharaId
                && targetCharaId == other.targetCharaId
                && targetFootstep == other.targetFootstep
    }
}