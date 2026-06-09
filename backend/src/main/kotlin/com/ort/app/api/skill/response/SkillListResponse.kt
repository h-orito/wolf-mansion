package com.ort.app.api.skill.response

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillTag
import com.ort.app.domain.model.skill.Skills

/**
 * 役職一覧 (`GET /api/v1/skills`) のレスポンス。
 * 「おまかせ」系の仮想役職を除いた実役職を返す (村一覧の絞り込み候補・役職一覧画面で共有)。
 */
data class SkillListResponse(
    val skills: List<SimpleSkillView>,
    val tags: List<String>,
) {
    constructor(skills: Skills) : this(
        skills = skills.list.map { SimpleSkillView(it) },
        tags = SkillTag.entries.map { it.name },
    )
}

/** 役職のビュー。陣営・タグ情報を含む。 */
data class SimpleSkillView(
    val code: String,
    val name: String,
    val shortName: String,
    val campCode: String,
    val campName: String,
    val tags: List<String>,
) {
    constructor(skill: Skill) : this(
        code = skill.code,
        name = skill.name,
        shortName = skill.shortName,
        campCode = skill.camp().code,
        campName = skill.camp().name,
        tags = SkillTag.entries.filter { tag -> tag.getSkillList().any { it.code == skill.code } }.map { it.name },
    )
}
