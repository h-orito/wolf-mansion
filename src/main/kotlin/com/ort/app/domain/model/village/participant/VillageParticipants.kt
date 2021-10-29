package com.ort.app.domain.model.village.participant

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.skill.Skill
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

    fun assignSkill(id: Int, skill: Skill): VillageParticipants {
        return this.copy(
            list = list.map {
                if (it.id == id) it.assignSkill(skill) else it.copy()
            }
        )
    }

    fun addLover(from: VillageParticipant, to: VillageParticipant): VillageParticipants {
        return this.copy(
            list = list.map {
                if (it.id == from.id) it.addLover(to.id) else it.copy()
            }
        )
    }

    fun assignRoom(id: Int, roomNumber: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.assignRoom(roomNumber, day) else it.copy()
            }
        )
    }

    fun suddenlyDeadh(id: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.suddenlyDeath(day) else it.copy()
            }
        )
    }

    fun execute(id: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.execute(day) else it.copy()
            }
        )
    }

    fun divineKill(id: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.divineKill(day) else it.copy()
            }
        )
    }

    fun attacked(id: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.attacked(day) else it.copy()
            }
        )
    }

    fun trapKill(id: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.trapKill(day) else it.copy()
            }
        )
    }

    fun bombKill(id: Int, day: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == id) it.bombKill(day) else it.copy()
            }
        )
    }

    fun suicide(id: Int, day: Int): VillageParticipants {
        return copy(list = list.map { if (it.id == id) it.suicide(day) else it.copy() })
    }

    fun revive(id: Int, day: Int): VillageParticipants {
        return copy(list = list.map { if (it.id == id) it.revive(day) else it.copy() })
    }

    fun foxPossession(fromParticipantId: Int, toParticipantId: Int): VillageParticipants {
        return copy(
            list = list.map {
                when (it.id) {
                    fromParticipantId -> it.foxPossession(toParticipantId)
                    toParticipantId -> it.foxPossessioned(fromParticipantId)
                    else -> it.copy()
                }
            }
        )
    }

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

    fun stalking(fromParticipantId: Int, toParticipantId: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == fromParticipantId) it.stalking(toParticipantId) else it.copy()
            }
        )
    }

    fun seduce(fromParticipantId: Int, toParticipantId: Int): VillageParticipants {
        return copy(
            list = list.map {
                if (it.id == toParticipantId) it.seduced(fromParticipantId) else it.copy()
            }
        )
    }

    fun judgeWin(winCamp: Camp): VillageParticipants = copy(list = list.map { it.judgeWin(winCamp) })
}