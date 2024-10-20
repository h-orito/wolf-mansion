package com.ort.app.api.request

import org.hibernate.validator.constraints.Length
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotNull

data class VillageParticipateForm(
    // キャラチップ制の場合のみ
    val charachipId: Int? = null,
    val charaId: Int? = null,
    // オリジナルキャラ制の場合のみ
    var charaImageFile: MultipartFile? = null,

    @field:NotNull
    @field:Length(min = 1, max = 40)
    val charaName: String? = null,

    @field:NotNull
    @field:Length(min = 1, max = 1)
    val charaShortName: String? = null,

    val requestedSkill: String? = null,
    val secondRequestedSkill: String? = null,
    @field:NotNull
    val joinMessage: String? = null,
    val joinPassword: String? = null,
    val spectator: Boolean? = null,
    val personNumber: Int? = null
)