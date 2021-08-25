package com.ort.app.api.request.setting

import com.ort.app.domain.model.skill.Skill
import com.ort.dbflute.allcommon.CDef
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class RandomOrganizationSkillForm(
    /** 役職 */
    var skillCode: String? = null,

    /** 役職名 */
    var skillName: String? = null,

    /** 最低人数 */
    @field:NotNull
    @Min(0)
    @Max(100)
    var minNum: Int? = null,

    /** 最大人数 */
    @Min(0)
    @Max(100)
    var maxNum: Int? = null,

    /** 配分 */
    @field:NotNull
    @Min(0)
    @Max(100)
    var allocation: Int? = null
) {
    constructor(skill: Skill) : this(
        skillCode = skill.code,
        skillName = skill.name,
        minNum = if (skill.toCdef() == CDef.Skill.村人) 1 else 0,
        allocation = 50
    )
}