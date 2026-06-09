package com.ort.app.api.skill.request

import com.ort.app.domain.model.skill.SkillTag

data class SkillSearchRequest(
    val tags: List<String>? = null,
    val name: String? = null,
    val villageId: Int? = null,
) {
    fun parseTags(): List<SkillTag> = SkillTag.of(tags ?: emptyList())
}
