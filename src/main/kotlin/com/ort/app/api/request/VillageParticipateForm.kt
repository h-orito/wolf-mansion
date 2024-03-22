package com.ort.app.api.request

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotNull

data class VillageParticipateForm(
    // キャラチップ制の場合
    val charachipId: Int? = null,
    val charaId: Int? = null,
    // オリジナルキャラ制の場合
    val charaName: String? = null,
    val charaShortName: String? = null,
    var charaImageFile: MultipartFile? = null,

    val requestedSkill: String? = null,
    val secondRequestedSkill: String? = null,
    @field:NotNull
    val joinMessage: String? = null,
    val joinPassword: String? = null,
    val spectator: Boolean? = null,
    val personNumber: Int? = null
)