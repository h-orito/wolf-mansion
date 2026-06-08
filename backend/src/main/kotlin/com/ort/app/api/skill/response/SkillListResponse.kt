package com.ort.app.api.skill.response

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills

/**
 * 役職一覧 (`GET /api/v1/skills`) のレスポンス。
 * 「おまかせ」系の仮想役職を除いた実役職を返す (村一覧の絞り込み候補・役職一覧画面で共有)。
 */
data class SkillListResponse(
    val skills: List<SimpleSkillView>,
) {
    constructor(skills: Skills) : this(skills = skills.list.map { SimpleSkillView(it) })
}

/** 役職の軽量ビュー。 */
data class SimpleSkillView(
    val code: String,
    val name: String,
    val shortName: String,
) {
    constructor(skill: Skill) : this(code = skill.code, name = skill.name, shortName = skill.shortName)
}
