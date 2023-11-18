package com.ort.app.domain.model.village.setting

import com.ort.dbflute.allcommon.CDef

data class SecretSayRange(
    val code: String,
    val name: String
) {
    constructor(
        cdef: CDef.AllowedSecretSay
    ) : this(
        code = cdef.code(),
        name = cdef.alias(),
    )

    fun toCdef(): CDef.AllowedSecretSay = CDef.AllowedSecretSay.codeOf(code)
    fun canSay(): Boolean = toCdef() != CDef.AllowedSecretSay.なし
    fun canSayAll(): Boolean = toCdef() == CDef.AllowedSecretSay.全員
    fun canOnlyCreator(): Boolean = toCdef() == CDef.AllowedSecretSay.村建てとのみ
}