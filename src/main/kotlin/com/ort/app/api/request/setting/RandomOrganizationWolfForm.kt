package com.ort.app.api.request.setting

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class RandomOrganizationWolfForm(
    /** 最少人数 */
    @field:NotNull
    @field:Min(1)
    @field:Max(100)
    var minNum: Int? = null,

    /** 最多人数 */
    @field:Min(1)
    @field:Max(100)
    var maxNum: Int? = null,
) {
    constructor() : this(
        minNum = 1,
    )
}