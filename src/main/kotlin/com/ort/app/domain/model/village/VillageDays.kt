package com.ort.app.domain.model.village

data class VillageDays(val list: List<VillageDay>) {
    fun latestDay(): VillageDay = list.last()
}