package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageChangeNameBody
import com.ort.app.api.request.village.VillageFaceTypeModifyBody
import com.ort.app.api.request.village.VillageMemoBody
import com.ort.app.api.response.myself.MyselfFaceTypeView
import com.ort.app.api.response.myself.MyselfFaceTypesView
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
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
 * face-types の認可: `myself.charaId` を `CharaService` に渡し、サービス層で `original_chara_image_id`
 * の所有者が自キャラと一致するかを検証する。旧 Thymeleaf 実装も同じ穴を持っていたため `VillageRpController`
 * 側も同時に修正済み。
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

    @GetMapping("/{villageId}/rp/face-types")
    @Operation(
        summary = "表情差分一覧取得 (オリジナルキャラチップ)",
        description = "自分のキャラに紐づく表情差分一覧 (code / name / 画像 URL / display) を返す。" +
                "オリジナルキャラチップ村以外は空配列を返す。",
    )
    fun listFaceTypes(
        @PathVariable villageId: Int,
    ): MyselfFaceTypesView {
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        if (!village.setting.chara.isOriginalCharachip) {
            return MyselfFaceTypesView(list = emptyList())
        }
        val chara = charaService.findChara(myself.charaId, isOriginal = true)
            ?: throw WolfMansionBusinessException("自分のキャラが見つかりません")
        val list = chara.images.list.map {
            MyselfFaceTypeView(
                code = it.faceType.code,
                name = it.faceType.name,
                url = it.url,
                isDisplay = it.isDisplay,
            )
        }
        return MyselfFaceTypesView(list = list)
    }

    @PutMapping("/{villageId}/rp/face-types")
    @Operation(
        summary = "表情差分編集 (オリジナルキャラチップ)",
        description = "既存の表情差分の name / display を一括更新する。画像追加は別 endpoint (multipart、未実装)。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun modifyFaceTypes(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageFaceTypeModifyBody,
    ) {
        val (_, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        body.faceTypeList.forEach {
            charaService.updateOriginalCharaImage(myself.charaId, it.code, it.name, it.display)
        }
    }
}
