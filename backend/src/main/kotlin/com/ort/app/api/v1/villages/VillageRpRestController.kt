package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageChangeNameBody
import com.ort.app.api.request.village.VillageFaceTypeModifyBody
import com.ort.app.api.request.village.VillageMemoBody
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
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
 *
 * 旧 Thymeleaf 実装は POST だが、REST 的に状態更新は PUT が自然なので統一した。
 *
 * NOTE: face-types は現在 `loadVillageAndRequireMyself` で「参加者である」までしかチェックして
 *       いない。code (= original_chara_image_id) が自分のキャラの画像かどうかの認可チェックは
 *       旧 Thymeleaf でも実施していない既存挙動。引き続き脆弱性として残るが、JSON API 公開で
 *       攻撃面が広がるため別 issue で追跡する想定 (`.issues/` 参照)。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageRpRestController(
    private val villageContextLoader: VillageContextLoader,
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
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        villageCoordinator.changeName(village, myself, body.name, body.shortName)
    }

    @PutMapping("/{villageId}/rp/memo")
    @Operation(summary = "簡易メモ変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun memo(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageMemoBody,
    ) {
        val (_, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
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
        villageContextLoader.loadVillageAndRequireMyself(villageId)  // 参加していなければ拒否
        body.faceTypeList.forEach { charaService.updateOriginalCharaImage(it.code, it.name, it.display) }
    }
}
