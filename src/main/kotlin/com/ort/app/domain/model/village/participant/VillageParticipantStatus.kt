package com.ort.app.domain.model.village.participant

data class VillageParticipantStatus(
    val loverIdList: List<Int>,
    val foxPossessionIdList: List<Int>,
    val foxPossessionedIdList: List<Int>
) {
    fun hasLover(): Boolean = loverIdList.isNotEmpty()

    fun isFoxPossessioning(): Boolean = foxPossessionIdList.isNotEmpty()

    fun isFoxPossessioned(): Boolean = foxPossessionedIdList.isNotEmpty()
}