package com.ort.app.domain.model.village.participant

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.dead.DeadReason

data class VillageParticipants(
    val count: Int,
    val list: List<VillageParticipant>
) {
    fun member(id: Int): VillageParticipant = list.first { it.id == id }

    fun chara(charaId: Int): VillageParticipant = list.first { it.charaId == charaId }

    fun findByRoomNumber(roomNumber: Int, day: Int): VillageParticipant? =
        list.find { it.roomNumberWhen(day) == roomNumber }

    fun filterSpectator(): VillageParticipants = copy(list = list.filter { it.isSpectator })

    fun filterNotSpectator(): VillageParticipants = copy(list = list.filter { !it.isSpectator })

    fun filterNotGone(): VillageParticipants = copy(list = list.filterNot { it.isGone })

    fun filterAlive(): VillageParticipants = copy(list = list.filterNot { it.dead.isDead })

    fun filterDead(): VillageParticipants = copy(list = list.filter { it.dead.isDead })

    fun filterNotParticipant(participant: VillageParticipant) = copy(list = list.filterNot { it.id == participant.id })

    fun filterPsychicable(): VillageParticipants = copy(list = list.filter { it.dead.isPsychicable() })
    fun filterExecuted(): VillageParticipants = copy(list = list.filter { it.dead.isExecuted() })
    fun filterMiserable(): VillageParticipants = copy(list = list.filter { it.dead.isMiserableDead() })
    fun filterSuicide(): VillageParticipants = copy(list = list.filter { it.dead.isSuicideDead() })

    fun filterDeadDay(day: Int): VillageParticipants = copy(list = list.filter { it.dead.deadDay == day })

    fun filterExistsDeadHistory(day: Int, reason: DeadReason): VillageParticipants =
        copy(list = list.filter { it.dead.histories.deadWhen(day, reason) })

    fun filterExistsMiserableDeadHistory(day: Int): VillageParticipants =
        copy(list = list.filter { it.dead.histories.miserableDeadWhen(day) })

    fun filterExistsReviveHistory(day: Int): VillageParticipants =
        copy(list = list.filter { it.dead.histories.reviveWhen(day) })

    fun filterSuddenly(): VillageParticipants = copy(list = list.filter { it.dead.isSuddenlyDead() })

    fun filterNotDummy(dummyParticipant: VillageParticipant) =
        copy(list = list.filterNot { it.id == dummyParticipant.id })

    fun filterBySkill(skill: Skill): VillageParticipants =
        copy(list = list.filter { it.skill?.toCdef() == skill.toCdef() })

    fun filterNotAssignSkill(): VillageParticipants = copy(list = list.filter { it.skill == null })

    fun filterByFirstRequestSkill(skill: Skill): VillageParticipants =
        copy(list = list.filter { it.requestSkill?.first?.code == skill.code })

    fun filterBySecondRequestSkill(skill: Skill): VillageParticipants =
        copy(list = list.filter { it.requestSkill?.second?.code == skill.code })

    fun sortedByRoomNumber(): VillageParticipants {
        return this.copy(
            list = list.sortedWith(compareBy<VillageParticipant> { it.isSpectator }
                .thenBy { it.room?.number ?: 0 }
                .thenBy { it.charaId })
        )
    }

    // 日付更新用
    fun isSame(other: VillageParticipants): Boolean {
        return list.size == other.list.size
                && list.all { participant ->
            other.list.find { otherParticipant ->
                otherParticipant.id == participant.id
            }?.let { otherParticipant ->
                participant.isSame(otherParticipant)
            } ?: false
        }
    }

    fun leave(id: Int): VillageParticipants {
        return this.copy(
            count = count - 1,
            list = list.map {
                if (it.id == id) it.gone() else it.copy()
            }
        )
    }

    fun assignSkill(id: Int, skill: Skill, day: Int): VillageParticipants =
        transformParticipant(id) { it.assignSkill(skill, day) }

    fun assignRoom(id: Int, roomNumber: Int, day: Int): VillageParticipants =
        transformParticipant(id) { it.assignRoom(roomNumber, day) }

    // 死亡・復活
    fun suddenlyDeadh(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.suddenlyDeath(day) }
    fun execute(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.execute(day) }
    fun divineKill(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.divineKill(day) }
    fun attacked(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.attacked(day) }
    fun trapKill(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.trapKill(day) }
    fun bombKill(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.bombKill(day) }
    fun zakoKilled(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.zakoKilled(day) }
    fun suicide(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.suicide(day) }
    fun forceReincarnation(id: Int, day: Int, skill: Skill): VillageParticipants =
        transformParticipant(id) { it.forceReincarnation(day, skill) }

    fun revive(id: Int, day: Int): VillageParticipants = transformParticipant(id) { it.revive(day) }

    // ステータス変化
    fun foxPossession(village: Village, fromId: Int, toId: Int): VillageParticipants =
        transformParticipant(toId) { it.foxPossessioned(village, fromId) }

    fun insane(village: Village, fromId: Int, toId: Int): VillageParticipants =
        transformParticipant(toId) { it.insaned(village, fromId) }

    fun persuade(village: Village, fromId: Int, toId: Int): VillageParticipants =
        transformParticipant(toId) { it.persuaded(village, fromId) }

    fun court(fromParticipantId: Int, toParticipantId: Int): VillageParticipants {
        return copy(
            list = list.map {
                when (it.id) {
                    fromParticipantId -> it.court(toParticipantId)
                    toParticipantId -> it.courted(fromParticipantId)
                    else -> it.copy()
                }
            }
        )
    }

    fun addLover(from: VillageParticipant, to: VillageParticipant): VillageParticipants =
        transformParticipant(from.id) { it.addLover(to.id) }

    fun stalking(fromId: Int, toId: Int): VillageParticipants = transformParticipant(fromId) { it.stalking(toId) }
    fun cheatLove(village: Village, fromId: Int, toId: Int): VillageParticipants = transformParticipant(fromId) { it.cheatLove(village, toId) }
    fun seduce(fromId: Int, toId: Int): VillageParticipants = transformParticipant(toId) { it.seduced(fromId) }
    fun insurance(fromId: Int, toId: Int): VillageParticipants = transformParticipant(toId) { it.insurance(fromId) }
    fun breakup(id: Int, village: Village): VillageParticipants = transformParticipant(id) { it.breakup(village) }
    fun stealLove(id: Int, stealerId: Int, village: Village): VillageParticipants =
        transformParticipant(id) { it.stealLove(stealerId, village) }

    fun useInsurance(id: Int): VillageParticipants = transformParticipant(id) { it.useInsurance() }
    fun disrespect(fromId: Int, toId: Int): VillageParticipants = transformParticipant(toId) { it.disrespect(fromId) }
    fun addCurseMark(id: Int): VillageParticipants = transformParticipant(id) { it.addCurseMark() }
    fun clearCurseMark(id: Int): VillageParticipants = transformParticipant(id) { it.clearCurseMark() }
    fun addCounterCurseMark(id: Int): VillageParticipants = transformParticipant(id) { it.addCounterCurseMark() }
    fun clearCounterCurseMark(id: Int): VillageParticipants = transformParticipant(id) { it.clearCounterCurseMark() }
    fun addTelekinesis(id: Int): VillageParticipants = transformParticipant(id) { it.addTelekinesis() }
    fun clearTelekinesis(id: Int): VillageParticipants = transformParticipant(id) { it.clearTelekinesis() }

    fun judgeWin(winCamp: Camp): VillageParticipants = copy(list = list.map { it.judgeWin(winCamp) })

    private fun transformParticipant(id: Int, transform: (VillageParticipant) -> (VillageParticipant)) =
        copy(list = list.map { if (it.id == id) transform(it) else it.copy() })
}