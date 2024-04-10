package com.ort.app.api.request.setting

import com.ort.app.domain.model.skill.Skills
import com.ort.dbflute.allcommon.CDef
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class RandomOrganizationCampForm(
    /** 陣営 */
    var campCode: String? = null,

    /** 陣営名 */
    var campName: String? = null,

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
    var reincarnationAllocation: Int? = null,

    /** 役職ごとの配分 */
    @Valid
    @field:NotNull
    var skillAllocation: List<RandomOrganizationSkillForm>? = null
) {
    constructor(cdef: CDef.Camp) : this(
        campCode = cdef.code(),
        campName = cdef.alias(),
        minNum = 0,
        allocation = 50,
        reincarnationAllocation = 50,
        skillAllocation = initializeSkillAllocation(cdef)
    )

    companion object {
        private fun initializeSkillAllocation(camp: CDef.Camp): List<RandomOrganizationSkillForm> {
            return Skills.all().filterNotSomeone().filterByCamp(camp).list.map {
                RandomOrganizationSkillForm(it)
            }
        }
    }
}