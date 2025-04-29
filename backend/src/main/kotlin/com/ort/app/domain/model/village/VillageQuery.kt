package com.ort.app.domain.model.village

import com.ort.app.domain.model.skill.Skill

data class VillageQuery(
    val ids: List<Int> = emptyList(),
    val statuses: List<VillageStatus> = emptyList(),
    val charachipIds: List<Int> = emptyList(),
    val skills: List<Skill> = emptyList(),
    val isRandomOrg: Boolean? = null
)