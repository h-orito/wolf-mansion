package com.ort.app.domain.model.player

import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef

data class Player(
    val id: Int,
    val name: String,
    val authority: Authority,
    val isRestrictedParticipation: Boolean,
    val participateProgressVillageIdList: List<Int> = listOf(),
    val participateFinishedVillageIdList: List<Int> = listOf(),
    val createProgressVillageIdList: List<Int> = listOf(),
    val createFinishedVillageIdList: List<Int> = listOf()
) {
    fun isAvailableCreateVillage(): Boolean {
        if (authority.toCdef() == CDef.Authority.管理者) return true
        if (isRestrictedParticipation) return false
        return createProgressVillageIdList.isEmpty() && participateFinishedVillageIdList.isNotEmpty()
    }

    fun isAvailableParticipateVillage(villageId: Int): Boolean =
        !participateProgressVillageIdList.contains(villageId) && !isRestrictedParticipation

    fun assertParticipate(villageId: Int) {
        if (!isAvailableParticipateVillage(villageId)) {
            throw WolfMansionBusinessException("参加できません")
        }
    }

    fun assertSpectate(villageId: Int) {
        if (!isAvailableParticipateVillage(villageId)) {
            throw WolfMansionBusinessException("参加できません")
        }
    }

    fun isSame(other: Player): Boolean {
        return id == other.id
                && isRestrictedParticipation == other.isRestrictedParticipation
    }

    fun restrictParticipation(): Player = copy(isRestrictedParticipation = true)
}

fun Player?.canCreateVillage(): Boolean = this?.isAvailableCreateVillage() ?: false

