package com.ort.app.api.view

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.camp.Camps
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillTag
import com.ort.app.domain.model.skill.Skills

data class SkillContent(
    val campList: List<CampContent>,
    val tagList: List<String>
) {

    data class CampContent(
        val code: String,
        val name: String,
        val skillList: List<SkillContent>
    ) {
        constructor(camp: Camp) : this(
            code = camp.code.lowercase(),
            name = camp.name,
            skillList = Skills.all().filterNotSomeone().filterByCamp(camp.toCdef()).list.map { SkillContent(it) }
        )
    }

    data class SkillContent(
        val code: String,
        val name: String
    ) {
        constructor(
            skill: Skill
        ) : this(
            code = skill.code.lowercase(),
            name = skill.name
        )
    }

    constructor() : this(
        campList = Camps.all().list.map { CampContent(it) },
        tagList = SkillTag.entries.map { it.name }
    )
}