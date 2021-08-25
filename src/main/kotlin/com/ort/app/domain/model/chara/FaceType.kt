package com.ort.app.domain.model.chara

import com.ort.dbflute.allcommon.CDef

data class FaceType(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.FaceType) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.FaceType =
        CDef.FaceType.codeOf(code) ?: throw IllegalStateException("unknown face: $code")

    companion object {
        fun codeOf(code: String): FaceType = FaceType(CDef.FaceType.codeOf(code))
    }
}

fun CDef.FaceType.toModel(): FaceType = FaceType(this)