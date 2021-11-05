package com.ort.app.domain.model.message

import com.ort.dbflute.allcommon.CDef

data class MessageType(
    val code: String,
    val name: String
) {
    companion object {
        val sayTypeList = listOf(
            CDef.MessageType.見学発言,
            CDef.MessageType.独り言,
            CDef.MessageType.死者の呻き,
            CDef.MessageType.人狼の囁き,
            CDef.MessageType.共鳴発言,
            CDef.MessageType.恋人発言,
            CDef.MessageType.念話,
            CDef.MessageType.通常発言,
            CDef.MessageType.秘話,
            CDef.MessageType.アクション
        )
        val owlViewableSayTypeList = listOf(
            CDef.MessageType.人狼の囁き,
            CDef.MessageType.共鳴発言,
            CDef.MessageType.恋人発言,
            CDef.MessageType.念話
        )
        val commonSystemTypeList = listOf(
            CDef.MessageType.非公開システムメッセージ,
            CDef.MessageType.白黒霊視結果,
            CDef.MessageType.役職霊視結果,
            CDef.MessageType.検死結果,
            CDef.MessageType.襲撃結果
        )
        val personalSystemTypeList = listOf(
            CDef.MessageType.足音調査結果,
            CDef.MessageType.白黒占い結果,
            CDef.MessageType.役職占い結果,
            CDef.MessageType.恋人メッセージ,
            CDef.MessageType.妖狐メッセージ
        )
        val everyoneViewableTypeList = listOf(
            CDef.MessageType.通常発言,
            CDef.MessageType.村建て発言,
            CDef.MessageType.公開システムメッセージ
        )
    }

    constructor(cdef: CDef.MessageType) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.MessageType =
        CDef.MessageType.codeOf(code) ?: throw IllegalStateException("unknown type: $code")

    fun isSayType(): Boolean = sayTypeList.contains(this.toCdef())
    fun isOwlViewableType(): Boolean = owlViewableSayTypeList.contains(this.toCdef())
}

fun CDef.MessageType.toModel(): MessageType = MessageType(this)