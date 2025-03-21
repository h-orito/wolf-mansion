package com.ort.app.domain.model.vote

data class Votes(val list: List<Vote>) {

    fun filterByDay(day: Int): Votes = copy(list = list.filter { it.day == day })

    fun filterByCharaId(charaId: Int): Votes = copy(list = list.filter { it.charaId == charaId })

    fun filterPastDay(day: Int): Votes = copy(list = list.filter { it.day < day })

    fun sortedByDay(): Votes = copy(list = list.sortedBy { it.day })

    fun isSame(other: Votes): Boolean {
        return list.size == other.list.size
                && list.all { vote -> other.list.any { otherVote -> vote.isSame(otherVote) } }
    }

    fun add(vote: Vote): Votes = copy(list = list + vote)
}