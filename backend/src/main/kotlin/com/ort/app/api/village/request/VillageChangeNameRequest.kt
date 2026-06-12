package com.ort.app.api.village.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/** キャラ名・略称の変更。制約は SSR の VillageChangeNameForm と同じ。 */
data class VillageChangeNameRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 40)
    val name: String? = null,
    @field:NotBlank
    @field:Size(min = 1, max = 1)
    val shortName: String? = null,
)
