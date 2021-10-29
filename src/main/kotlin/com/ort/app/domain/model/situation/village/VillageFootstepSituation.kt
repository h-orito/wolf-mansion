package com.ort.app.domain.model.situation.village

data class VillageFootstepSituation(val list: List<VillageDayFootstep>)

data class VillageDayFootstep(
    val day: Int,
    val footstep: String
)
