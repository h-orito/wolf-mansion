package com.ort.app.domain.model.village.participant.dead

import com.ort.dbflute.allcommon.CDef

data class Dead(
    val isDead: Boolean,
    val deadDay: Int?,
    val reason: DeadReason?,
    val histories: DeadHistories
) {
    fun isMiserableDead(): Boolean = isDead && reason?.isMiserable() ?: false
    fun isExecuted(): Boolean = isDead && reason?.isExecuted() ?: false
    fun isAttacked(): Boolean = isDead && reason?.isAttacked() ?: false
    fun isSuicideDead(): Boolean = isDead && reason?.isSuicide() ?: false
    fun isSuddenlyDead(): Boolean = isDead && reason?.isSuddenly() ?: false
    fun isPsychicable(): Boolean = isDead && reason?.isPsychicable() ?: false

    fun isDeadWhen(day: Int): Boolean {
        // 最後に死んだ日
        val maxDeadDay = histories.list.filter { it.isDead && it.day <= day }.maxOfOrNull { it.day } ?: return false
        val latestDeadHistory = histories.list.last { it.isDead && it.day == maxDeadDay }
        val latestDeadDay = latestDeadHistory.day
        val latestDeadReason = latestDeadHistory.reason!!
        // 指定した日までに生き返っていれば生存、なければ死亡
        // 死んだ日と生き返った日が同じ場合は、後追いなら死亡で、そうでなければ生きている
        return if (latestDeadReason.isSuicide()) {
            histories.list.none { !it.isDead && it.day <= day && it.day > latestDeadDay }
        } else {
            histories.list.none { !it.isDead && it.day <= day && it.day >= latestDeadDay }
        }
    }

    fun deadDayWhen(day: Int): Int? {
        return if (!isDeadWhen(day)) null
        else histories.list.filter { it.isDead && it.day <= day }.maxByOrNull { it.day }!!.day
    }

    fun deadReasonWhen(day: Int): DeadReason? {
        return if (!isDeadWhen(day)) null
        else histories.list.filter { it.isDead && it.day <= day }.maxByOrNull { it.day }!!.reason
    }

    fun isSame(other: Dead): Boolean {
        return isDead == other.isDead
                && deadDay == other.deadDay
                && reason?.code == other.reason?.code
                && histories.isSame(other.histories)
    }

    fun suddenlyDeath(day: Int): Dead = dead(day, CDef.DeadReason.突然.toModel())
    fun execute(day: Int): Dead = dead(day, CDef.DeadReason.処刑.toModel())
    fun divineKill(day: Int): Dead = dead(day, CDef.DeadReason.呪殺.toModel())
    fun attacked(day: Int): Dead = dead(day, CDef.DeadReason.襲撃.toModel())
    fun trapKill(day: Int): Dead = dead(day, CDef.DeadReason.罠死.toModel())
    fun bombKill(day: Int): Dead = dead(day, CDef.DeadReason.爆死.toModel())
    fun zakoKilled(day: Int): Dead = dead(day, CDef.DeadReason.雑魚.toModel())
    fun suicide(day: Int): Dead = dead(day, CDef.DeadReason.後追.toModel())
    fun forceReincarnation(day: Int): Dead = attacked(day).revive(day)
    fun revive(day: Int): Dead {
        if (!isDead) return this
        return copy(
            isDead = false,
            deadDay = null,
            reason = null,
            histories = histories.copy(
                list = histories.list + DeadHistory(
                    day = day,
                    isDead = false,
                    reason = null
                )
            )
        )
    }

    private fun dead(day: Int, reason: DeadReason): Dead {
        if (isDead) return this
        return copy(
            isDead = true,
            deadDay = day,
            reason = reason,
            histories = histories.copy(
                list = histories.list + DeadHistory(
                    day = day,
                    isDead = true,
                    reason = reason
                )
            )
        )
    }
}