package com.ort.app.domain.model.situation.participant

import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill

data class ParticipantSkillRequestSituation(
    val isAvailableSkillRequest: Boolean,
    val selectableSkillList: List<Skill> = listOf(),
    val skillRequest: RequestSkill?
)