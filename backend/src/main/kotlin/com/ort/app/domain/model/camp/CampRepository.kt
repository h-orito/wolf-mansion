package com.ort.app.domain.model.camp

interface CampRepository {

    fun findCampSkills(): List<CampSkill>
}