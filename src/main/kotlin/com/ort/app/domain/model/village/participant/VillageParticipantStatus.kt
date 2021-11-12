package com.ort.app.domain.model.village.participant

data class VillageParticipantStatus(
    val loverIdList: List<Int>,
    val foxPossessionIdList: List<Int>,
    val foxPossessionedIdList: List<Int>,
    // 保険勧誘してきた人
    val insuranceIdList: List<Int>
) {
    fun hasLover(): Boolean = loverIdList.isNotEmpty()

    fun hasInsurance(): Boolean = insuranceIdList.isNotEmpty()

    fun isFoxPossessioning(): Boolean = foxPossessionIdList.isNotEmpty()

    fun isFoxPossessioned(): Boolean = foxPossessionedIdList.isNotEmpty()

    fun isSame(other: VillageParticipantStatus): Boolean {
        return loverIdList.size == other.loverIdList.size
                && foxPossessionIdList.size == other.foxPossessionIdList.size
                && foxPossessionedIdList.size == other.foxPossessionedIdList.size
                && insuranceIdList.size == other.insuranceIdList.size
                && loverIdList.all { other.loverIdList.contains(it) }
                && foxPossessionIdList.all { other.foxPossessionIdList.contains(it) }
                && foxPossessionedIdList.all { other.foxPossessionedIdList.contains(it) }
                && insuranceIdList.all { other.insuranceIdList.contains(it) }
    }

    fun addLover(id: Int): VillageParticipantStatus {
        return copy(loverIdList = (loverIdList + id).distinct())
    }

    fun foxPossession(targetParticipantId: Int): VillageParticipantStatus {
        return copy(foxPossessionIdList = (foxPossessionIdList + targetParticipantId).distinct())
    }

    fun foxPossessioned(fromParticipantId: Int): VillageParticipantStatus {
        return copy(foxPossessionedIdList = (foxPossessionedIdList + fromParticipantId).distinct())
    }

    fun insurance(participantId: Int): VillageParticipantStatus {
        return copy(insuranceIdList = (insuranceIdList + participantId).distinct())
    }

    fun useInsurance(): VillageParticipantStatus = copy(insuranceIdList = emptyList())
}