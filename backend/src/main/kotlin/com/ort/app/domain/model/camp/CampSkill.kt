package com.ort.app.domain.model.camp

import com.ort.app.domain.model.skill.Skill

data class CampSkill(
    val camp: Camp,
    val skillList: List<Skill>
)