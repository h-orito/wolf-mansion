package com.ort.app.api.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull

data class VillageFaceTypeModifyForm(
    @field:Valid
    @field:NotNull
    var faceTypeList: List<FaceTypeForm>? = null
) {
    data class FaceTypeForm(
        @field:NotBlank
        var code: String? = null,

        @field:NotBlank
        @field:Length(min = 1, max = 5)
        var name: String? = null,

        @field:NotNull
        var display: Boolean? = null,

        // 表示用
        var url: String? = null
    )
}