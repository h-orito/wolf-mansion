package com.ort.app.domain.model.skill

import com.ort.dbflute.allcommon.CDef

data class Skill(
    val code: String,
    val name: String,
    val shortName: String
) {
    constructor(cdef: CDef.Skill) : this(code = cdef.code(), name = cdef.alias(), shortName = cdef.skill_short_name())

    fun toCdef(): CDef.Skill =
        CDef.Skill.codeOf(code) ?: throw IllegalStateException("unknown skill: $code")
}