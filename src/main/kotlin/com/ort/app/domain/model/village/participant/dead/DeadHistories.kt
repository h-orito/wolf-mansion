package com.ort.app.domain.model.village.participant.dead

data class DeadHistories(val list: List<DeadHistory>) {

    fun deadWhen(day: Int, reason: DeadReason): Boolean = list.any {
        it.isDead && it.day == day && it.reason!!.code == reason.code
    }

    fun miserableDeadWhen(day: Int): Boolean = list.any {
        it.isDead && it.day == day && it.reason!!.isMiserable()
    }

    fun reviveWhen(day: Int): Boolean = list.any { !it.isDead && it.day == day }

    fun isSame(other: DeadHistories): Boolean {
        return list.size == other.list.size
                && list.all { history -> other.list.any { otherHistory -> history.isSame(otherHistory) } }
    }
}