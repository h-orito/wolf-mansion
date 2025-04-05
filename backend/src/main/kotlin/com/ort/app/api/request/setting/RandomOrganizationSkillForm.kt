package com.ort.app.api.request.setting

import com.ort.app.domain.model.skill.Skill
import com.ort.dbflute.allcommon.CDef
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class RandomOrganizationSkillForm(
    /** 役職 */
    var skillCode: String? = null,

    /** 役職名 */
    var skillName: String? = null,

    /** 最少人数 */
    @field:NotNull
    @field:Min(0)
    @field:Max(100)
    var minNum: Int? = null,

    /** 最多人数 */
    @field:Min(0)
    @field:Max(100)
    var maxNum: Int? = null,

    /** 配分 */
    @field:NotNull
    @field:Min(0)
    @field:Max(100)
    var allocation: Int? = null,

    /** 転生配分 */
    @field:NotNull
    @field:Min(0)
    @field:Max(100)
    var reincarnationAllocation: Int? = null
) {
    constructor(skill: Skill) : this(
        skillCode = skill.code,
        skillName = skill.name,
        minNum = if (skill.toCdef() == CDef.Skill.村人) 1 else 0,
        maxNum = if (skill.isRequestable()) null else 0,
        allocation = 50,
        reincarnationAllocation = if (skill.isRevivable()) 50 else 0
    )
}