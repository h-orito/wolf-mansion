package com.ort.app.domain.model.ability

import com.ort.app.domain.model.message.MessageType
import com.ort.dbflute.allcommon.CDef

data class AbilityType(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.AbilityType) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.AbilityType = CDef.AbilityType.codeOf(code)

    fun getSetMessageType(): MessageType =
        if (toCdef() == CDef.AbilityType.指揮 || toCdef() == CDef.AbilityType.煽動) MessageType(CDef.MessageType.公開システムメッセージ)
        else MessageType(CDef.MessageType.非公開システムメッセージ)
}

fun CDef.AbilityType.toModel(): AbilityType = AbilityType(this)