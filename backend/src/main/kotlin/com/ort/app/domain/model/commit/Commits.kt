package com.ort.app.domain.model.commit

data class Commits(val list: List<Commit>) {
    fun filterByDay(day: Int): Commits = copy(list = list.filter { it.day == day })
}