package com.ort.app.domain.model.player

import com.ort.dbflute.allcommon.CDef

data class Player(
    val id: Int,
    val name: String,
    val authority: Authority,
    val isRestrictedParticipation: Boolean,
    val participatingProgressVillageIdList: List<Int>,
    val creatingProgressVillageIdList: List<Int>
) {
    fun isAvailableCreateVillage(): Boolean =
        authority.toCdef() == CDef.Authority.管理者 || creatingProgressVillageIdList.isEmpty()
}

fun Player?.canCreateVillage(): Boolean = this?.isAvailableCreateVillage() ?: false

