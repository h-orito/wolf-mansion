package com.ort.app.domain.model.village

import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.setting.*
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime

data class VillageSetting(
    val chara: VillageCharaSetting,
    val personMin: Int,
    val personMax: Int,
    val startDatetime: LocalDateTime,
    val dayChangeIntervalSeconds: Int,
    val rule: VillageRule,
    val joinPassword: String?,
    val organize: VillageOrganize,
    val sayRestriction: SayRestriction,
    val tags: VillageTags
) {
    fun allRequestableSkillList(): List<Skill> {
        return if (rule.isRandomOrganization) Skills.all().list
        else organize.allRequestableSkillList()
    }

    fun findRestrict(myself: VillageParticipant, type: MessageType): SayRestriction.Restriction? =
        sayRestriction.restrict(myself, type.toCdef())

    fun isSame(other: VillageSetting): Boolean {
        return startDatetime == other.startDatetime
                && rule.isSame(other.rule)
    }

    fun extendPrologue(): VillageSetting = copy(startDatetime = startDatetime.plusDays(1L))

    fun mapToSkillCount(participantsCount: Int): Map<CDef.Skill, Int> =
        organize.mapToSkillCount(rule.isRandomOrganization, participantsCount)

}