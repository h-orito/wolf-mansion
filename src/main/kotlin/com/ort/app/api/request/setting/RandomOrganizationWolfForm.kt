package com.ort.app.api.request.setting

import com.ort.app.domain.model.skill.Skills
import com.ort.dbflute.allcommon.CDef
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class RandomOrganizationWolfForm(
    /** 最低人数 */
    @field:NotNull
    @field:Min(1)
    @field:Max(100)
    var minNum: Int? = null,

    /** 最大人数 */
    @field:Min(1)
    @field:Max(100)
    var maxNum: Int? = null,
) {
    constructor() : this(
        minNum = 1,
    )
}