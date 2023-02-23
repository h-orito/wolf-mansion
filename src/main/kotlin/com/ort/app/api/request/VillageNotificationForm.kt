package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull

data class VillageNotificationForm(
    @field:NotNull
    @field:Length(min = 1)
    val webhookUrl: String? = null,
    val villageStart: Boolean? = null,
    val villageEpilogue: Boolean? = null,
    val secretSay: Boolean? = null,
    val abilitySay: Boolean? = null
)