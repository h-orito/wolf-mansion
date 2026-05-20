package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.NewVillageCreateBody
import com.ort.app.api.response.village.NewVillageFormView
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionNotImplementedException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 新規村作成 (creator) の REST API。
 *
 * 旧 Thymeleaf `NewVillageController` (`/new-village`, `/new-village/confirm`,
 * `/new-village/create`, `/new-village/divert/...`) の置き換え。
 *
 * - `GET /api/v1/new-village/form-defaults`: 初期 form 値 + 候補値 + canCreate を返す
 * - `POST /api/v1/villages`: 新規村作成 (JSON-only、`shouldOriginalImage=true` は 501)
 *
 * **意図的にスコープ外** (Step 8h):
 * - オリジナルキャラチップ村 (`shouldOriginalImage=true`): multipart 画像アップロードが必要。
 *   入村フロー (Step 8a) も同じ理由で 501 にしている。後続 step で multipart endpoint を追加予定。
 * - 既存村からの設定流用 (旧 `/new-village/divert/{id}`): UX 補助機能のため後続 step に持ち越し。
 *   フロントから `villages/{id}/settings/form` 相当を借りるか、専用 endpoint を追加する。
 * - 確認画面: SPA 側で `<dialog>` ベースの確認 UI に置き換え (server preview endpoint 不要)。
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "villages", description = "村")
class NewVillageRestController(
    private val villageCoordinator: VillageCoordinator,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val applier: NewVillageCreateApplier,
) {

    @GetMapping("/new-village/form-defaults")
    @Operation(
        summary = "新規村作成フォーム初期値",
        description = "認証ユーザの canCreate 判定 + フォーム初期値 + 候補値 (公式キャラチップ / 役職 / 陣営 / タグ / 秘話可能範囲 / 発言種別) を 1 リクエストで返す。未認証は 400。",
    )
    fun formDefaults(): NewVillageFormView {
        val userName = WolfMansionUserInfoUtil.getUserInfo()?.username
            ?: throw WolfMansionBusinessException("ログインが必要です")
        // JWT は持っているが player レコードが見つからない (= 退会済み等) は本来起きないが、
        // 起きた場合は canCreate=false で誤魔化さず 400 で原因を明示する。
        val player = playerService.findPlayer(userName)
            ?: throw WolfMansionBusinessException("プレイヤー情報が見つかりません")
        val charachips = charaService.findCharachips()
        return NewVillageFormView(
            canCreate = player.isAvailableCreateVillage(),
            userName = userName,
            defaults = NewVillageFormView.NewVillageDefaults.create(),
            options = NewVillageFormView.NewVillageOptions(charachips),
        )
    }

    @PostMapping("/villages")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "新規村作成",
        description = "新規村を作成して 201 + 作成された村 ID を返す。" +
                "creator 認可 (= `player.canCreateVillage()`) を満たさない場合は 400。" +
                "オリジナルキャラチップ村 (`shouldOriginalImage=true`) は本 endpoint では非対応で 501 を返す。" +
                "cross-field バリデーションは旧 `NewVillageFormValidator` を共有。",
    )
    fun create(
        @Valid @RequestBody body: NewVillageCreateBody,
    ): CreatedVillageView {
        val userName = WolfMansionUserInfoUtil.getUserInfo()?.username
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val player = playerService.findPlayer(userName)
            ?: throw WolfMansionBusinessException("プレイヤー情報が見つかりません")
        if (!player.isAvailableCreateVillage()) {
            throw WolfMansionBusinessException("村建てした村の決着がつくまでは村を建てられません。")
        }
        // オリジナル画像登録は multipart が必要。本 endpoint では受け付けない。
        if (body.shouldOriginalImage == true) {
            throw WolfMansionNotImplementedException(
                "オリジナル画像を使用する村の作成はこの API では未対応です。",
            )
        }
        // 公式キャラチップ村は characterSetId / dummyCharaId が必須
        val characterSetId = body.characterSetId
        val dummyCharaId = body.dummyCharaId
        if (characterSetId.isNullOrEmpty() || dummyCharaId == null) {
            throw WolfMansionBusinessException("キャラチップとダミーキャラを選択してください")
        }
        val charachips = charaService.findCharachips(characterSetId, false)
        if (charachips.list.size != characterSetId.size) {
            throw WolfMansionBusinessException("指定したキャラチップが見つかりません")
        }
        // dummyCharaId が選択したキャラチップ群のいずれかに属していることを検証
        // (任意の charaId で別チップのキャラをダミーにできないようにする)
        val dummyInScope = charachips.list.any { chip -> chip.charas.list.any { it.id == dummyCharaId } }
        if (!dummyInScope) {
            throw WolfMansionBusinessException("ダミーキャラは選択したキャラチップから選んでください")
        }
        villageCoordinator.assertCreateVillage(player, body.personMaxNum!!, charachips, isOriginal = false)
        val paramVillage = applier.toVillage(body, player)
        val village = villageCoordinator.registerVillage(
            paramVillage = paramVillage,
            dummyCharaName = body.dummyCharaName!!,
            dummyCharaShortName = body.dummyCharaShortName!!,
            dummyCharaImage = null,
            joinMessage = body.dummyJoinMessage!!,
            day1Message = body.dummyDay1Message,
        )
        return CreatedVillageView(id = village.id)
    }

    @Schema(description = "作成された村の識別子")
    data class CreatedVillageView(
        @field:Schema(description = "村 ID") val id: Int,
    )
}
