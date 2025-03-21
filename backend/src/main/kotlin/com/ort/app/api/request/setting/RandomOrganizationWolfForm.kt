package com.ort.app.api.request.setting

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

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