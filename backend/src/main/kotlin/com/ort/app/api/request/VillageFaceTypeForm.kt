package com.ort.app.api.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import org.springframework.web.multipart.MultipartFile

data class VillageFaceTypeForm(
    @field:NotBlank
    @field:Length(min = 1, max = 5)
    var faceTypeName: String? = null,
    @field:NotNull
    var image: MultipartFile? = null
)