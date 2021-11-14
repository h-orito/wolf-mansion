package com.ort.app.api.request

data class VillageListForm(
    val charachipIds: List<Int>? = null,
    val skillCodes: List<String>? = null,
    val random: Boolean? = null
)