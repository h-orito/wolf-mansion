package com.ort.app.domain.model.village.participant

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.camp.toModel
import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.dead.Dead
import com.ort.app.domain.model.village.room.Room
import com.ort.dbflute.allcommon.CDef
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
) {
    fun name(): String = "[${shortName()}] ${charaName.name}"

    fun nameWhen(day: Int): String = "[${shortNameWhen(day)}] ${charaName.name}"

    fun shortName(): String {
        val shortName = charaName.shortName
        return if (room != null) {
            val roomNumber = room.number.toString().padStart(2, '0')
            "$roomNumber$shortName"
        } else shortName
    }

    // 部屋移動があった場合は移動後の部屋が取得される
    fun shortNameWhen(day: Int): String {
        val shortName = charaName.shortName
        return if (room != null) {
            val roomNumber = roomNumberWhen(day).toString().padStart(2, '0')
            "$roomNumber$shortName"
        } else shortName
    }

    fun roomNumberWhen(day: Int): Int? = room?.numberWhen(day)

    fun isDead(): Boolean = dead.isDead
    fun isDeadWhen(day: Int): Boolean = dead.isDeadWhen(day)

    fun isAlive(): Boolean = !isDead()
    fun isAliveWhen(day: Int): Boolean = !isDeadWhen(day)

    fun isAdmin(): Boolean = playerId == 1
    fun hasBigEar(): Boolean = skill?.toCdef() == CDef.Skill.梟

    fun isAvailableRequestSkill(): Boolean = !isSpectator

    fun isAvailableCommit(dummyId: Int): Boolean = !isSpectator && isAlive() && id != dummyId

    fun isAvailableSay(isEpilogue: Boolean): Boolean = !dead.isDead || !dead.reason!!.isSuddenly() || isEpilogue

    fun isSayableNormalSay(isEpilogue: Boolean): Boolean {
        if (isAdmin()) return true
        return !isSpectator && (isAlive() || isEpilogue)
    }

    fun isViewableGraveSay(): Boolean = isSpectator || (isDead() && !dead.reason!!.isSuddenly())
    fun isSayableGraveSay(): Boolean {
        if (isAdmin()) return true
        return isDead() && !isSpectator && !dead.reason!!.isSuddenly()
    }

    fun isSayableMonologueSay(): Boolean = true

    fun isViewableSpectateSay(): Boolean = isSpectator || (isDead() && !dead.reason!!.isSuddenly())
    fun isSayableSpectateSay(): Boolean = isAdmin() || isSpectator

    fun isViewableWerewolfSay(): Boolean = skill?.isViewableWerewolfSay() ?: false
    fun isSayableWerewolfSay(): Boolean {
        if (isAdmin()) return true
        return isAlive() && skill?.isSayableWerewolfSay() ?: false
    }

    fun isViewableSympathizeSay(): Boolean = skill?.isViewableSympathizeSay() ?: false
    fun isSayableSympathizeSay(): Boolean {
        if (isAdmin()) return true
        return isAlive() && skill?.isSayableSympathizeSay() ?: false
    }

    fun isViewableLoversSay(): Boolean = status.hasLover()
    fun isSayableLoversSay(): Boolean = isAdmin() || status.hasLover()

    fun isSayableSecretSay(): Boolean = true

    fun isViewablePsychicMessage(): Boolean = isAlive() && skill?.isViewablePsychicMessage() ?: false
    fun isViewableGuruMessage(): Boolean = isAlive() && skill?.isViewableGuruMessage() ?: false
    fun isViewableAttackMessage(): Boolean = isAlive() && skill?.isViewableAttackMessage() ?: false
    fun isViewableCoronerMessage(): Boolean = isAlive() && skill?.isViewableCoronerMessage() ?: false
    fun isViewableDivineMessage(): Boolean = isAlive() && skill?.isViewableDivineMessage() ?: false
    fun isViewableWiseMessage(): Boolean = isAlive() && skill?.isViewableWiseMessage() ?: false
    fun isViewableInvestigateMessage(): Boolean = isAlive() && skill?.isViewableInvestigateMessage() ?: false
    fun isViewableLoversMessage(): Boolean = isAlive()
            && (status.hasLover() || skill?.isViewableLoversMessage() ?: false)

    fun isViewableFoxMessage(): Boolean = isAlive()
            && (status.isFoxPossessioned() || status.isFoxPossessioning())

    fun canUseAbility(): Boolean = isAlive() && !isSpectator

    fun canVote(): Boolean = isAlive() && !isSpectator

    fun canChangeName(isEpilogue: Boolean): Boolean = !dead.isDead || !dead.reason!!.isSuddenly() || isEpilogue

    fun isViewableSpoilerContent(isOpenSkillInGrave: Boolean): Boolean = (isDead() && isOpenSkillInGrave) || isSpectator

    fun getTargetCohabitor(village: Village): VillageParticipant? {
        return getTargetLovers(village).filterBySkill(Skill(CDef.Skill.同棲者)).list.firstOrNull()
    }

    fun getTargetLovers(village: Village): VillageParticipants {
        val list = village.participants.list.filter { status.loverIdList.contains(it.id) }
        return VillageParticipants(count = list.size, list = list)
    }

    fun isSame(other: VillageParticipant): Boolean {
        return skill?.code == other.skill?.code
                && room.isSameRoom(other.room)
                && requestSkill?.first?.code == other.requestSkill?.first?.code
                && status.isSame(other.status)
                && dead.isSame(other.dead)
                && isGone == other.isGone
                && isWin == other.isWin
                && camp?.code == other.camp?.code
    }

    fun gone(): VillageParticipant = copy(isGone = true)

    fun assignSkill(skill: Skill): VillageParticipant = copy(
        skill = skill,
        camp = when {
            status.hasLover() -> CDef.Camp.恋人陣営.toModel()
            status.isFoxPossessioned() -> CDef.Camp.狐陣営.toModel()
            else -> skill.camp()
        }
    )

    fun assignRoom(roomNumber: Int, day: Int): VillageParticipant {
        return copy(
            room = this.room?.reAssign(roomNumber, day) ?: Room(roomNumber, day)
        )
    }

    fun changeRequestSkill(requestSkill: RequestSkill) = copy(requestSkill = requestSkill)

    fun addLover(id: Int): VillageParticipant = copy(status = status.addLover(id))

    fun suddenlyDeath(day: Int): VillageParticipant = copy(dead = dead.suddenlyDeath(day))
    fun execute(day: Int): VillageParticipant = copy(dead = dead.execute(day))
    fun divineKill(day: Int): VillageParticipant = copy(dead = dead.divineKill(day))
    fun attacked(day: Int): VillageParticipant = copy(dead = dead.attacked(day))
    fun trapKill(day: Int): VillageParticipant = copy(dead = dead.trapKill(day))
    fun bombKill(day: Int): VillageParticipant = copy(dead = dead.bombKill(day))
    fun suicide(day: Int): VillageParticipant = copy(dead = dead.suicide(day))
    fun revive(day: Int): VillageParticipant = copy(dead = dead.revive(day))

    fun foxPossession(targetParticipantId: Int): VillageParticipant =
        copy(status = status.foxPossession(targetParticipantId))

    fun foxPossessioned(fromParticipantId: Int): VillageParticipant = copy(
        status = status.foxPossessioned(fromParticipantId),
        camp = if (status.hasLover()) camp else CDef.Camp.狐陣営.toModel()
    )

    fun court(participantId: Int): VillageParticipant = love(participantId)
    fun courted(participantId: Int): VillageParticipant = love(participantId)
    fun stalking(participantId: Int): VillageParticipant = love(participantId)
    fun seduced(participantId: Int): VillageParticipant = love(participantId)

    fun judgeWin(winCamp: Camp): VillageParticipant = copy(
        isWin = when {
            // 愉快犯陣営は生存していれば勝利
            camp!!.isCriminals() -> isAlive()
            // 勝敗判定陣営が一致していたら勝利
            else -> winCamp.toCdef() == camp.toCdef()
        }
    )

    private fun love(participantId: Int): VillageParticipant = copy(
        status = status.addLover(participantId),
        camp = CDef.Camp.恋人陣営.toModel()
    )

    private fun Room?.isSameRoom(other: Room?): Boolean {
        return if (this == null && other == null) true
        else if (this == null && other != null) false
        else if (this != null && other == null) false
        else this!!.isSame(other!!)
    }
}