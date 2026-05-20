package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageSettingsUpdateBody
import com.ort.app.api.response.village.VillageSettingsFormView
import com.ort.app.application.coordinator.CreatorCoordinator
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
 * 村設定変更 (creator 専用) の REST API。
 *
 * 旧 `CreatorController` の `GET/POST /village/{id}/settings` 置き換え。
 *
 * - `GET /api/v1/villages/{id}/settings/form`: 編集 UI 用 (現在値 + 候補値) を返す
 * - `PUT /api/v1/villages/{id}/settings`: 設定を更新する
 *
 * Step 8e の `VillageCreatorRestController` に同居せず controller を分けている理由:
 * - 認可 (`loadVillageAndRequireCreator`) は同じだが、Body / View が独立しており
 *   import / フィールドが膨らむため
 * - 設定変更は Step 8f で切り出しが完了するまで進行中の作業
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageSettingsRestController(
    private val villageContextLoader: VillageContextLoader,
    private val creatorCoordinator: CreatorCoordinator,
    private val applier: VillageSettingsUpdateApplier,
) {

    @GetMapping("/{villageId}/settings/form")
    @Operation(
        summary = "村設定編集フォーム取得",
        description = "creator 専用。編集 UI で必要な現在値 + 候補値 (役職一覧 / 陣営一覧 / 募集範囲 / " +
                "年齢制限 / 秘話可能範囲 / 発言種別) を 1 リクエストで返す。",
    )
    fun getEditForm(@PathVariable villageId: Int): VillageSettingsFormView {
        val village = villageContextLoader.loadVillageAndRequireCreator(villageId)
        return VillageSettingsFormView(village)
    }

    @PutMapping("/{villageId}/settings")
    @Operation(
        summary = "村設定変更",
        description = "creator 専用。プロローグ中のみ可。オリジナルキャラチップ村では入村パスワード必須。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSettings(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageSettingsUpdateBody,
    ) {
        val village = villageContextLoader.loadVillageAndRequireCreator(villageId)
        // オリジナルキャラチップ村ではパスワード必須 (旧 Thymeleaf 実装と同じガード)
        if (village.setting.chara.isOriginalCharachip && body.joinPassword.isNullOrBlank()) {
            throw WolfMansionBusinessException("オリジナルキャラクターを登録する村ではパスワードは必須です")
        }
        val merged = applier.apply(village, body)
        creatorCoordinator.saveSettings(merged)
    }
}
