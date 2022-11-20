package com.ort.app.domain.model.village.setting

data class VillageRule(
    val isOpenVote: Boolean,
    val isPossibleSkillRequest: Boolean,
    val isAvailableSpectate: Boolean,
    val isCreatorIsProducer: Boolean,
    val isAvailableSameWolfAttack: Boolean,
    val isOpenSkillInGrave: Boolean,
    val isVisibleGraveSpectateMessage: Boolean,
    val isAvailableSuddenlyDeath: Boolean,
    val isAvailableCommit: Boolean,
    val isAvailableGuardSameTarget: Boolean,
    val isAvailableSecretSay: Boolean,
    val isAvailableAction: Boolean,
    val isRandomOrganization: Boolean,
    val isReincarnationSkillAll: Boolean
) {
    fun isSame(other: VillageRule): Boolean {
        return isOpenVote == other.isOpenVote
                && isPossibleSkillRequest == other.isPossibleSkillRequest
                && isAvailableSpectate == other.isAvailableSpectate
                && isCreatorIsProducer == other.isCreatorIsProducer
                && isAvailableSameWolfAttack == other.isAvailableSameWolfAttack
                && isOpenSkillInGrave == other.isOpenSkillInGrave
                && isVisibleGraveSpectateMessage == other.isVisibleGraveSpectateMessage
                && isAvailableSuddenlyDeath == other.isAvailableSuddenlyDeath
                && isAvailableCommit == other.isAvailableCommit
                && isAvailableGuardSameTarget == other.isAvailableGuardSameTarget
                && isAvailableSecretSay == other.isAvailableSecretSay
                && isAvailableAction == other.isAvailableAction
                && isRandomOrganization == other.isRandomOrganization
                && isReincarnationSkillAll == other.isReincarnationSkillAll
    }
}