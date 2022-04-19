package com.ort.app.domain.model.village.participant

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.camp.toModel
import com.ort.app.domain.model.skill.RequestSkill
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillHistories
import com.ort.app.domain.model.skill.SkillHistory
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
    val memo: String?,
    val ipAddresses: List<String>
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

    fun skillWhen(day: Int): Skill? = skill?.skillWhen(day)

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

    fun isViewableGraveSay(): Boolean = isAdmin() || isSpectator || (isDead() && !dead.reason!!.isSuddenly())
    fun isSayableGraveSay(): Boolean {
        if (isAdmin()) return true
        return isDead() && !isSpectator && !dead.reason!!.isSuddenly()
    }

    fun isViewableMonologueSay(): Boolean = isAdmin()
    fun isSayableMonologueSay(): Boolean = true

    fun isViewableSpectateSay(): Boolean = isAdmin() || isSpectator || (isDead() && !dead.reason!!.isSuddenly())
    fun isSayableSpectateSay(): Boolean = isAdmin() || isSpectator

    fun isViewableWerewolfSay(): Boolean = isAdmin() || skill?.isViewableWerewolfSay() ?: false
    fun isSayableWerewolfSay(): Boolean {
        if (isAdmin()) return true
        return isAlive() && skill?.isSayableWerewolfSay() ?: false
    }

    fun isViewableSympathizeSay(): Boolean = isAdmin() || skill?.isViewableSympathizeSay() ?: false
    fun isSayableSympathizeSay(): Boolean {
        if (isAdmin()) return true
        return isAlive() && skill?.isSayableSympathizeSay() ?: false
    }

    fun isViewableTelepathy(): Boolean =
        isAdmin() || status.isFoxPossessioned() || skill?.isViewableTelepathy() ?: false

    fun isSayableTelepathy(): Boolean {
        if (isAdmin()) return true
        return isAlive() && skill?.isSayableTelepathy() ?: false
    }

    fun isViewableLoversSay(): Boolean = isAdmin() || status.hasLover()
    fun isSayableLoversSay(): Boolean = isAdmin() || (isAlive() && status.hasLover())

    fun isViewableSecretSay(): Boolean = isAdmin()
    fun isSayableSecretSay(): Boolean = true

    fun isViewablePsychicMessage(): Boolean = isAdmin() || skill?.isViewablePsychicMessage() ?: false
    fun isViewableGuruMessage(): Boolean = isAdmin() || skill?.isViewableGuruMessage() ?: false
    fun isViewableAttackMessage(): Boolean = isAdmin() || skill?.isViewableAttackMessage() ?: false
    fun isViewableCoronerMessage(): Boolean = isAdmin() || skill?.isViewableCoronerMessage() ?: false
    fun isViewableDivineMessage(): Boolean = isAdmin() || skill?.isViewableDivineMessage() ?: false
    fun isViewableWiseMessage(): Boolean = isAdmin() || skill?.isViewableWiseMessage() ?: false
    fun isViewableInvestigateMessage(): Boolean = isAdmin() || skill?.isViewableInvestigateMessage() ?: false
    fun isViewableLoversMessage(): Boolean =
        isAdmin() || (status.hasLover() || skill?.isViewableLoversMessage() ?: false)

    fun isViewableFoxMessage(): Boolean =
        isAdmin() || status.isFoxPossessioned() || skill?.isViewableFoxMessage() ?: false

    fun isViewablePrivateSystemMessage(): Boolean = isAdmin()
    fun isViewablePrivateAbilityMessage(): Boolean = isAdmin()

    fun canUseAbility(): Boolean = isAlive() && !isSpectator

    fun canVote(): Boolean = isAlive() && !isSpectator

    fun canChangeName(isEpilogue: Boolean): Boolean = !dead.isDead || !dead.reason!!.isSuddenly() || isEpilogue
    fun canAddImage(): Boolean = true

    fun isViewableSpoilerContent(isOpenSkillInGrave: Boolean): Boolean = isOpenSkillInGrave && (isDead() || isSpectator)

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

    fun assignSkill(skill: Skill, day: Int): VillageParticipant = copy(
        skill = if (this.skill == null) {
            skill.copy(histories = SkillHistories(list = listOf(SkillHistory(skill = skill, day = day))))
        } else {
            this.skill.assignSkill(skill, day)
        },
        camp = getCurrentWinCamp(status, skill)
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
    fun forceReincarnation(day: Int, skill: Skill): VillageParticipant =
        assignSkill(skill, day).copy(dead = dead.forceReincarnation(day))

    fun foxPossessioned(village: Village, fromParticipantId: Int): VillageParticipant {
        // 自分が同棲者の場合は同棲者を除いて恋絆を解除する
        val cohabitor = if (skill!!.toCdef() == CDef.Skill.同棲者) getTargetCohabitor(village) else null
        val newStatus = status.foxPossessioned(fromParticipantId, cohabitor)
        return copy(
            status = newStatus,
            camp = getCurrentWinCamp(newStatus, skill)
        )
    }

    fun insaned(village: Village, fromParticipantId: Int): VillageParticipant {
        // 自分が同棲者の場合は同棲者を除いて恋絆を解除する
        val cohabitor = if (skill!!.toCdef() == CDef.Skill.同棲者) getTargetCohabitor(village) else null
        val newStatus = status.insaned(fromParticipantId, cohabitor)
        return copy(
            status = newStatus,
            camp = getCurrentWinCamp(newStatus, skill)
        )
    }

    fun persuaded(village: Village, fromParticipantId: Int): VillageParticipant {
        // 自分が同棲者の場合は同棲者を除いて恋絆を解除する
        val cohabitor = if (skill!!.toCdef() == CDef.Skill.同棲者) getTargetCohabitor(village) else null
        val newStatus = status.persuaded(fromParticipantId, cohabitor)
        return copy(
            status = newStatus,
            camp = getCurrentWinCamp(newStatus, skill)
        )
    }

    fun court(participantId: Int): VillageParticipant = love(participantId)
    fun courted(participantId: Int): VillageParticipant = love(participantId)
    fun stalking(participantId: Int): VillageParticipant = love(participantId)
    fun seduced(participantId: Int): VillageParticipant = love(participantId)

    fun insurance(participantId: Int): VillageParticipant =
        copy(status = status.insurance(participantId))

    fun breakup(village: Village): VillageParticipant {
        // 自分が同棲者の場合は同棲者を除いて恋絆を解除する
        val cohabitor = if (skill!!.toCdef() == CDef.Skill.同棲者) getTargetCohabitor(village) else null
        val newStatus = status.breakup(cohabitor)
        return copy(
            status = newStatus,
            camp = getCurrentWinCamp(newStatus, skill)
        )
    }

    fun stealLove(stealerId: Int, village: Village): VillageParticipant {
        // 自分が同棲者の場合は同棲者を除いた恋絆が解除される
        val cohabitor = if (skill!!.toCdef() == CDef.Skill.同棲者) getTargetCohabitor(village) else null
        val newStatus = status.loveSteal(stealerId, cohabitor)
        return copy(
            status = newStatus,
            camp = getCurrentWinCamp(newStatus, skill)
        )
    }

    fun useInsurance(): VillageParticipant = copy(status = status.useInsurance())

    fun judgeWin(winCamp: Camp): VillageParticipant = copy(
        isWin = when {
            // ババは必ず敗北
            skill!!.toCdef() == CDef.Skill.ババ -> false
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
        else if (this == null) false
        else if (other == null) false
        else this.isSame(other)
    }

    private fun getCurrentWinCamp(currentStatus: VillageParticipantStatus, currentSkill: Skill): Camp =
        when {
            currentStatus.hasLover() -> CDef.Camp.恋人陣営.toModel()
            currentStatus.isFoxPossessioned() -> CDef.Camp.狐陣営.toModel()
            currentStatus.isInsaned() -> CDef.Camp.人狼陣営.toModel()
            currentStatus.isPersuaded() -> CDef.Camp.村人陣営.toModel()
            else -> currentSkill.camp()
        }
}