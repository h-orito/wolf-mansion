package com.ort.app.application.service

import com.ort.app.domain.model.camp.CampRepository
import com.ort.app.domain.model.camp.CampSkill
import org.springframework.stereotype.Service

@Service
class CampService(private val campRepository: CampRepository) {

    fun findCampSkills(): List<CampSkill> = campRepository.findCampSkills()
}