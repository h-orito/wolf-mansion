package com.ort.app.api.village.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class VillageModifyFaceTypesRequest(
    @field:Valid
    @field:NotNull
    val list: List<FaceTypeModifyItem>? = null,
) {
    @Schema(name = "VillageModifyFaceTypesRequestItem")
    data class FaceTypeModifyItem(
        @field:NotBlank
        val code: String? = null,
        @field:NotBlank
        @field:Size(min = 1, max = 5)
        val name: String? = null,
        @field:NotNull
        val isDisplay: Boolean? = null,
    )
}
