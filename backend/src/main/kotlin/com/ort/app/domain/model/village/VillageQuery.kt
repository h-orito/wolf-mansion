package com.ort.app.domain.model.village

import com.ort.app.domain.model.skill.Skill

data class VillageQuery(
    val ids: List<Int> = emptyList(),
    val statuses: List<VillageStatus> = emptyList(),
    val charachipIds: List<Int> = emptyList(),
    val skills: List<Skill> = emptyList(),
    val isRandomOrg: Boolean? = null,
    /** 村ID の並び順。true なら降順 (新しい村が先)。既定は昇順。 */
    val isDescending: Boolean = false,
)
