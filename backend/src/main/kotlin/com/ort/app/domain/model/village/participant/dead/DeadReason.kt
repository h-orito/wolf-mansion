package com.ort.app.domain.model.village.participant.dead

import com.ort.dbflute.allcommon.CDef

data class DeadReason(
    val code: String,
    val name: String
) {
    constructor(cdef: CDef.DeadReason) : this(code = cdef.code(), name = cdef.alias())

    fun toCdef(): CDef.DeadReason =
        CDef.DeadReason.codeOf(code) ?: throw IllegalStateException("unknown reason: $code")

    fun isMiserable(): Boolean = toCdef().isMiserable
    fun isExecuted(): Boolean = toCdef() == CDef.DeadReason.処刑
    fun isAttacked(): Boolean = toCdef() == CDef.DeadReason.襲撃
    fun isSuicide(): Boolean = toCdef() == CDef.DeadReason.後追
    fun isSuddenly(): Boolean = toCdef() == CDef.DeadReason.突然
    fun isPsychicable(): Boolean = isExecuted() || isSuddenly()

    fun getDisplayName(isSettled: Boolean): String {
        // エピローグを迎えていない場合、無惨の死因は表示しない
        val reason =
            if (!isSettled && isMiserable()) "無惨"
            else name
        return if (reason.endsWith("死")) reason else "${reason}死"
    }
}

fun CDef.DeadReason.toModel(): DeadReason = DeadReason(this)