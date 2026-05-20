package com.ort.app.api.response.skill

import com.ort.app.domain.model.camp.CampSkill
import com.ort.app.domain.model.skill.SkillTag
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 役職カタログ (陣営別の役職一覧 + 役職タグ一覧)。`/skills` 一覧画面で使用。
 *
 * 旧 Thymeleaf 側の `SkillContent` (役職一覧表示) と `SkillListContent` (フッタの簡易カード)
 * を統合した形。タグは絞り込みフィルタの選択肢として返す。
 */
@Schema(description = "役職カタログ (陣営別 + タグ一覧)")
data class SkillCatalogView(
    @field:Schema(description = "陣営別の役職一覧")
    val camps: List<CampSkills>,
    @field:Schema(description = "役職タグ一覧 (絞り込み用)")
    val tags: List<String>,
) {
    constructor(campSkills: List<CampSkill>) : this(
        camps = campSkills.map { CampSkills(it) },
        tags = SkillTag.values().map { it.name },
    )

    @Schema(description = "陣営とその陣営に属する役職一覧")
    data class CampSkills(
        @field:Schema(description = "陣営コード (CDef.Camp)") val campCode: String,
        @field:Schema(description = "陣営名") val campName: String,
        val skills: List<SkillView>,
    ) {
        constructor(campSkill: CampSkill) : this(
            campCode = campSkill.camp.code,
            campName = campSkill.camp.name,
            skills = campSkill.skillList.map { SkillView(it) },
        )
    }
}
