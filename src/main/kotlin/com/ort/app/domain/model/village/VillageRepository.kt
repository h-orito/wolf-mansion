package com.ort.app.domain.model.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.VillageParticipant
import java.time.LocalDateTime

interface VillageRepository {

    // ----------------------------------------------------
    // village

    fun findLatestVillageId(
        statusList: List<VillageStatus>
    ): Int

    fun findVillages(
        query: VillageQuery
    ): Villages

    fun findVillage(
        id: Int,
        excludeGone: Boolean
    ): Village?

    fun updateStatus(id: Int, status: VillageStatus)

    fun extendDay(id: Int, day: Int, datetime: LocalDateTime)

    fun shortenDay(id: Int, day: Int, datetime: LocalDateTime)

    fun updateSetting(village: Village)

    fun updateDaychangeDifference(current: Village, changed: Village)

    fun updateDummyCharaId(id: Int, charaId: Int)

    // ----------------------------------------------------
    // participant

    fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean
    ): VillageParticipant?

    fun findVillageParticipant(
        villageId: Int,
        userName: String,
        excludeGone: Boolean
    ): VillageParticipant?

    fun registerVillage(paramVillage: Village): Village

    fun participate(
        villageId: Int,
        playerId: Int,
        chara: Chara,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill,
        spectator: Boolean
    ): VillageParticipant

    fun switchParticipate(
        villageId: Int,
        participantId: Int,
        isSpectator: Boolean
    ): VillageParticipant

    fun leave(participant: VillageParticipant)

    fun changeParticipantName(participant: VillageParticipant, name: String, shortName: String)

    fun changeRequestSkill(participant: VillageParticipant, first: Skill, second: Skill)

    fun changeMemo(participant: VillageParticipant, memo: String)

    fun updateLastAccessDatetime(participant: VillageParticipant)

    fun addIpAddress(participant: VillageParticipant, ipAddress: String)

    fun registerNotificationSetting(participant: VillageParticipant)
}