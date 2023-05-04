package com.ort.app.domain.model.village.participant

data class VillageParticipantStatus(
    val loverIdList: List<Int>,
    val foxPossessionedIdList: List<Int>,
    // 狂気付与してきた人
    val insanedIdList: List<Int>,
    // 説得してきた人
    val persuadedIdList: List<Int>,
    // 保険勧誘してきた人
    val insuranceIdList: List<Int>,
    // 不敬を付与してきた人
    val disrespectfulList: List<Int>,
    // 呪縛符
    val hasCurseMark: Boolean,
    // 反呪符
    val hasCounterCurseMark: Boolean
) {
    fun hasLover(): Boolean = loverIdList.isNotEmpty()

    fun hasInsurance(): Boolean = insuranceIdList.isNotEmpty()

    fun isFoxPossessioned(): Boolean = foxPossessionedIdList.isNotEmpty()

    fun isInsaned(): Boolean = insanedIdList.isNotEmpty()

    fun isPersuaded(): Boolean = persuadedIdList.isNotEmpty()

    fun isDisrespectful(): Boolean = disrespectfulList.isNotEmpty()

    fun isSame(other: VillageParticipantStatus): Boolean {
        return loverIdList.size == other.loverIdList.size
                && foxPossessionedIdList.size == other.foxPossessionedIdList.size
                && insanedIdList.size == other.insanedIdList.size
                && persuadedIdList.size == other.persuadedIdList.size
                && insuranceIdList.size == other.insuranceIdList.size
                && disrespectfulList.size == other.disrespectfulList.size
                && loverIdList.all { other.loverIdList.contains(it) }
                && foxPossessionedIdList.all { other.foxPossessionedIdList.contains(it) }
                && insanedIdList.all { other.insanedIdList.contains(it) }
                && persuadedIdList.all { other.persuadedIdList.contains(it) }
                && insuranceIdList.all { other.insuranceIdList.contains(it) }
                && disrespectfulList.all { other.disrespectfulList.contains(it) }
                && hasCurseMark == other.hasCurseMark
                && hasCounterCurseMark == other.hasCounterCurseMark
    }

    fun addLover(id: Int): VillageParticipantStatus {
        return copy(loverIdList = (loverIdList + id).distinct())
    }

    fun breakup(excludeParticipant: VillageParticipant?): VillageParticipantStatus {
        return if (excludeParticipant != null) {
            copy(loverIdList = listOf(excludeParticipant.id))
        } else {
            copy(loverIdList = emptyList())
        }
    }

    fun loveSteal(stealerId: Int, excludeParticipant: VillageParticipant?): VillageParticipantStatus {
        return if (excludeParticipant != null) {
            copy(loverIdList = listOf(stealerId, excludeParticipant.id))
        } else {
            copy(loverIdList = listOf(stealerId))
        }
    }

    fun foxPossessioned(
        fromParticipantId: Int,
        excludeParticipant: VillageParticipant?
    ): VillageParticipantStatus {
        // 破局させた上で狐憑きを付与
        return this.breakup(excludeParticipant).copy(
            foxPossessionedIdList = (foxPossessionedIdList + fromParticipantId).distinct()
        )
    }

    private fun clearFoxposessioned(): VillageParticipantStatus =
        copy(foxPossessionedIdList = emptyList())

    fun insaned(
        fromParticipantId: Int,
        excludeParticipant: VillageParticipant?
    ): VillageParticipantStatus {
        // 恋絆、狐憑きを削除して狂気を付与
        return this
            .breakup(excludeParticipant)
            .clearFoxposessioned()
            .copy(insanedIdList = (insanedIdList + fromParticipantId).distinct())
    }

    private fun clearInsaned(): VillageParticipantStatus = copy(insanedIdList = emptyList())

    fun persuaded(fromParticipantId: Int, excludeParticipant: VillageParticipant?): VillageParticipantStatus {
        // 恋絆、狐憑き、狂気を削除して信念を付与
        return this
            .breakup(excludeParticipant)
            .clearFoxposessioned()
            .clearInsaned()
            .copy(persuadedIdList = (persuadedIdList + fromParticipantId).distinct())
    }

    fun insurance(participantId: Int): VillageParticipantStatus {
        return copy(insuranceIdList = (insuranceIdList + participantId).distinct())
    }

    fun useInsurance(): VillageParticipantStatus = copy(insuranceIdList = emptyList())

    fun disrespect(fromParticipantId: Int): VillageParticipantStatus =
        copy(disrespectfulList = (disrespectfulList + fromParticipantId).distinct())

    fun respect(): VillageParticipantStatus = copy(disrespectfulList = emptyList())

    fun addCurseMark(): VillageParticipantStatus = copy(hasCurseMark = true)

    fun clearCurseMark(): VillageParticipantStatus = copy(hasCurseMark = false)

    fun addCounterCursed(): VillageParticipantStatus = copy(hasCounterCurseMark = true)

    fun clearCounterCursed(): VillageParticipantStatus = copy(hasCounterCurseMark = false)
}