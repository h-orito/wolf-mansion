package com.ort.app.domain.model.village.participant

import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.village.participant.dead.Dead
import com.ort.app.domain.model.village.room.Room
import java.time.LocalDateTime

data class VillageParticipant(
    val id: Int,
    val charaName: VillageParticipantName,
    val playerId: Int,
    val charaId: Int,
    val skill: Skill?,
    val requestSkill: RequestSkill?,
    val room: Room?,
    val status: VillageParticipantStatus,
    val dead: Dead,
    val isSpectator: Boolean,
    val isGone: Boolean,
    val isWin: Boolean?,
    val camp: Camp?,
    val lastAccessDatetime: LocalDateTime,
    val memo: String?
)