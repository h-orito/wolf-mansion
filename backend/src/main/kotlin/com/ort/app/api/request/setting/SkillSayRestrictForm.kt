package com.ort.app.api.request.setting

import com.ort.app.domain.model.skill.Skill

data class SkillSayRestrictForm(
    // 表示用
    var skillName: String? = null,

    // 入力用
    /** 役職 */
    var skillCode: String? = null,

    /** 制限するか */
    var restrict: Boolean? = null,

    /** 発言回数 */
    var count: Int? = null,

    /** 文字数 */
    var length: Int? = null
) {
    constructor(skill: Skill) : this(
        skillName = skill.name,
        skillCode = skill.code,
        restrict = false,
        count = 20,
        length = 400
    )
}