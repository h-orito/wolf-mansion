package com.ort.app.domain.model.village.participant.dead

import com.ort.dbflute.allcommon.CDef

data class DeadReason(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.DeadReason) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.DeadReason =
        CDef.DeadReason.codeOf(code) ?: throw IllegalStateException("unknown reason: $code")
}