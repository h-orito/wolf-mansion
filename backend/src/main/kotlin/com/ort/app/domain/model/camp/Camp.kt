package com.ort.app.domain.model.camp

import com.ort.dbflute.allcommon.CDef

data class Camp(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.Camp) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.Camp =
        CDef.Camp.codeOf(code) ?: throw IllegalStateException("unknown camp: $code")

    fun isVillagers(): Boolean = toCdef() == CDef.Camp.村人陣営
    fun isWolfs(): Boolean = toCdef() == CDef.Camp.人狼陣営
    fun isFoxs(): Boolean = toCdef() == CDef.Camp.狐陣営
    fun isLovers(): Boolean = toCdef() == CDef.Camp.恋人陣営
    fun isCriminals(): Boolean = toCdef() == CDef.Camp.愉快犯陣営
}

fun CDef.Camp.toModel(): Camp = Camp(this)