package com.ort.app.domain.model.message

import com.ort.dbflute.allcommon.CDef

data class MessageType(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.MessageType) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.MessageType =
        CDef.MessageType.codeOf(code) ?: throw IllegalStateException("unknown type: $code")
}