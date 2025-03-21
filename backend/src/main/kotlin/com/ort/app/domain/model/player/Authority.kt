package com.ort.app.domain.model.player

import com.ort.dbflute.allcommon.CDef

data class Authority(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.Authority) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.Authority =
        CDef.Authority.codeOf(code) ?: throw IllegalStateException("unknown authority: $code")
}