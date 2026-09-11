package com.ort.app.api.village.request

import com.ort.app.api.request.VillageParticipateForm
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

/**
 * 入村の確認/実行。検証は SSR と共通の VillageParticipateFormValidator を [toForm] 変換後に流用する。
 * オリジナル画像 (原画村) は multipart の別パートで受ける。
 */
data class VillageParticipateRequest(
    /** 選択キャラ (キャラチップ制のみ) */
    val charaId: Int? = null,
    @field:NotNull
    val charaName: String? = null,
    @field:NotNull
    val charaShortName: String? = null,
    /** 第 1 希望役職コード (省略はおまかせ) */
    val requestedSkill: String? = null,
    /** 第 2 希望役職コード (省略はおまかせ) */
    val secondRequestedSkill: String? = null,
    @field:NotNull
    val joinMessage: String? = null,
    val joinPassword: String? = null,
    /** 見学者として入村するか */
    val spectator: Boolean? = null,
) {
    fun toForm(charaImageFile: MultipartFile? = null): VillageParticipateForm =
        VillageParticipateForm(
            charaId = charaId,
            charaImageFile = charaImageFile,
            charaName = charaName,
            charaShortName = charaShortName,
            requestedSkill = requestedSkill,
            secondRequestedSkill = secondRequestedSkill,
            joinMessage = joinMessage,
            joinPassword = joinPassword,
            spectator = spectator,
        )
}
