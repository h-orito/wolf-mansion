package com.ort.app.domain.model.footstep

data class Footsteps(val list: List<Footstep>) {

    fun filterByDay(day: Int): Footsteps = copy(list = list.filter { it.day == day })

    fun filterByCharaId(charaId: Int): Footsteps = copy(list = list.filter { it.charaId == charaId })

    fun filterPastDay(day: Int): Footsteps = copy(list = list.filter { it.day < day })

    fun sortedByDay(): Footsteps = copy(list = list.sortedBy { it.day })

    fun isSame(other: Footsteps): Boolean {
        return list.size == other.list.size
                && list.all { footstep -> other.list.any { otherFootstep -> footstep.isSame(otherFootstep) } }
    }

    fun add(footstep: Footstep): Footsteps = copy(list = list + footstep)
}