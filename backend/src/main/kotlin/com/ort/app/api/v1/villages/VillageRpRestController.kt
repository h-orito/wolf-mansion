package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageChangeNameBody
import com.ort.app.api.request.village.VillageFaceTypeModifyBody
import com.ort.app.api.request.village.VillageMemoBody
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * RP (キャラ名 / メモ / 表情差分) 系の REST API。
 *
 * 既存 `VillageRpController` (Thymeleaf) の置き換え。
 *
 * - PUT /api/v1/villages/{id}/rp/name: キャラ名 + 略称変更
 * - PUT /api/v1/villages/{id}/rp/memo: 簡易メモ変更
 * - PUT /api/v1/villages/{id}/rp/face-types: 表情差分の表示名 / 表示有無を編集 (オリジナルキャラチップ村)
 *
 * 表情差分の "追加" (画像アップロード) は multipart 必須なので別 endpoint として将来追加する想定。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageRpRestController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
) {

    @PutMapping("/{villageId}/rp/name")
    @Operation(summary = "キャラ名変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeName(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageChangeNameBody,
    ) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        villageCoordinator.changeName(village, myself, body.name, body.shortName)
    }

    @PutMapping("/{villageId}/rp/memo")
    @Operation(summary = "簡易メモ変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun memo(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageMemoBody,
    ) {
        val (_, myself) = loadVillageAndRequireMyself(villageId)
        villageService.changeMemo(myself, body.memo)
    }

    @PutMapping("/{villageId}/rp/face-types")
    @Operation(
        summary = "表情差分編集 (オリジナルキャラチップ)",
        description = "既存の表情差分の name / display を一括更新する。画像追加は別 endpoint (multipart)。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun modifyFaceTypes(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageFaceTypeModifyBody,
    ) {
        loadVillageAndRequireMyself(villageId)  // 参加していなければ拒否
        body.faceTypeList.forEach { charaService.updateOriginalCharaImage(it.code, it.name, it.display) }
    }

    // 注意: PUT のみ提供する点について。
    // 旧 Thymeleaf は POST のみだったが、REST 的に状態更新は PUT が自然なので統一した。
    // POST 互換が必要になった場合は別 issue で追加検討。

    private fun loadVillageAndRequireMyself(villageId: Int): Pair<Village, VillageParticipant> {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val user = WolfMansionUserInfoUtil.getUserInfo()
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val myself = villageService.findVillageParticipant(village.id, user.username)
            ?: throw WolfMansionBusinessException("この村に参加していません")
        return village to myself
    }
}
