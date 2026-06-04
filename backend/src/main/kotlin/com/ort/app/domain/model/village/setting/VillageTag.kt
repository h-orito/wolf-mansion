package com.ort.app.domain.model.village.setting

import com.ort.dbflute.allcommon.CDef

data class VillageTag(
    val code: String,
    val name: String
) {
    constructor(
        cdef: CDef.VillageTagItem
    ) : this(
        code = cdef.code(),
        name = cdef.alias()
    )

    fun toCdef(): CDef.VillageTagItem = CDef.VillageTagItem.codeOf(code)
}

fun CDef.VillageTagItem.toModel(): VillageTag = VillageTag(this)