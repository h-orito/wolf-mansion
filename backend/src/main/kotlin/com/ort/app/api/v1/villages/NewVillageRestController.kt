package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.NewVillageCreateBody
import com.ort.app.api.response.village.NewVillageFormView
import com.ort.app.api.v1.support.ImageUploadValidator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.player.Player
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 新規村作成 (creator) の REST API。
 *
 * 旧 Thymeleaf `NewVillageController` (`/new-village`, `/new-village/confirm`,
 * `/new-village/create`, `/new-village/divert/...`) の置き換え。
 *
 * - `GET /api/v1/new-village/form-defaults`: 初期 form 値 + 候補値 + canCreate を返す
 * - `POST /api/v1/villages` (JSON): 新規村作成 (非オリジナル)
 * - `POST /api/v1/villages` (multipart): 新規村作成 (オリジナルキャラチップ、ダミーキャラ画像必須)
 *
 * オリジナル / 非オリジナルは `consumes` で振り分ける。multipart 版は `body` (JSON) と
 * `dummyCharaImage` (画像 file) の 2 パートを受け取る。
 *
 * **意図的にスコープ外** (後続 step):
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

    @PostMapping("/villages", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "新規村作成 (非オリジナルキャラチップ)",
        description = "公式キャラチップ村を作成して 201 + 作成された村 ID を返す。" +
                "creator 認可 (= `player.canCreateVillage()`) を満たさない場合は 400。" +
                "オリジナルキャラチップ村 (`shouldOriginalImage=true`) はこの JSON endpoint では非対応 (400)。" +
                "オリジナル村は multipart 版 `POST /api/v1/villages` (`consumes=multipart/form-data`) を使用。" +
                "cross-field バリデーションは旧 `NewVillageFormValidator` を共有。",
    )
    fun create(
        @Valid @RequestBody body: NewVillageCreateBody,
    ): CreatedVillageView {
        val player = requireCreatorPlayer()
        if (body.shouldOriginalImage == true) {
            throw WolfMansionBusinessException(
                "オリジナルキャラチップ村は multipart endpoint を使用してください。",
            )
        }
        return createOfficial(body, player)
    }

    @PostMapping("/villages", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "新規村作成 (オリジナルキャラチップ)",
        description = "オリジナルキャラチップ村を作成する。`body` (JSON) と `dummyCharaImage` (画像) の 2 パート構成。" +
                "`shouldOriginalImage=true` を必須。creator 認可を満たさない場合は 400。" +
                "画像は 1〜100KB、許可拡張子は png / jpg / jpeg / gif / webp。",
    )
    fun createOriginal(
        @Valid @RequestPart("body") body: NewVillageCreateBody,
        @RequestPart("dummyCharaImage") dummyCharaImage: MultipartFile,
    ): CreatedVillageView {
        val player = requireCreatorPlayer()
        if (body.shouldOriginalImage != true) {
            throw WolfMansionBusinessException(
                "shouldOriginalImage=true を指定してください (非オリジナル村は JSON endpoint を使用)。",
            )
        }
        ImageUploadValidator.validate(dummyCharaImage)
        villageCoordinator.assertCreateVillage(
            player, body.personMaxNum!!, charachips = emptyCharachips(), isOriginal = true,
        )
        val paramVillage = applier.toVillage(body, player)
        val village = villageCoordinator.registerVillage(
            paramVillage = paramVillage,
            dummyCharaName = body.dummyCharaName!!,
            dummyCharaShortName = body.dummyCharaShortName!!,
            dummyCharaImage = dummyCharaImage,
            joinMessage = body.dummyJoinMessage!!,
            day1Message = body.dummyDay1Message,
        )
        return CreatedVillageView(id = village.id)
    }

    private fun createOfficial(body: NewVillageCreateBody, player: Player): CreatedVillageView {
        val characterSetId = body.characterSetId
        val dummyCharaId = body.dummyCharaId
        if (characterSetId.isNullOrEmpty() || dummyCharaId == null) {
            throw WolfMansionBusinessException("キャラチップとダミーキャラを選択してください")
        }
        val charachips = charaService.findCharachips(characterSetId, false)
        if (charachips.list.size != characterSetId.size) {
            throw WolfMansionBusinessException("指定したキャラチップが見つかりません")
        }
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

    private fun requireCreatorPlayer(): Player {
        val userName = WolfMansionUserInfoUtil.getUserInfo()?.username
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val player = playerService.findPlayer(userName)
            ?: throw WolfMansionBusinessException("プレイヤー情報が見つかりません")
        if (!player.isAvailableCreateVillage()) {
            throw WolfMansionBusinessException("村建てした村の決着がつくまでは村を建てられません。")
        }
        return player
    }

    private fun emptyCharachips(): Charachips = Charachips(list = emptyList())

    @Schema(description = "作成された村の識別子")
    data class CreatedVillageView(
        @field:Schema(description = "村 ID") val id: Int,
    )
}
