package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageCreatorSayBody
import com.ort.app.api.request.village.VillageKickBody
import com.ort.app.application.coordinator.CreatorCoordinator
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 村建て (creator) 専用操作の REST API。
 *
 * 旧 `CreatorController` (Thymeleaf form) の置き換え。本 controller の全 endpoint は
 * `VillageContextLoader.loadVillageAndRequireCreator` で creator 権限を検証してから
 * 既存 `CreatorCoordinator` を呼ぶ。Player ID=1 (= 旧 admin user) は全村の creator として
 * 扱われる仕様 (旧 `CreatorCoordinator.isCreator` 踏襲)。
 *
 * - POST /api/v1/villages/{id}/creator-say:    村建て発言 (確認画面なし、preview なし)
 * - POST /api/v1/villages/{id}/kick:           参加者 1 名を強制退村
 * - POST /api/v1/villages/{id}/cancel:         廃村 (プロローグ中のみ)
 * - POST /api/v1/villages/{id}/extend-epilogue: エピローグを 1 日延長
 * - POST /api/v1/villages/{id}/shorten-epilogue: エピローグを 1 日短縮
 *
 * NOTE: 設定変更 (`/settings` の旧 GET/POST) は項目数が膨大なため Step 8e から分離し
 * 別 step (8f) でまとめて REST 化する。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageCreatorRestController(
    private val villageContextLoader: VillageContextLoader,
    private val creatorCoordinator: CreatorCoordinator,
) {

    @PostMapping("/{villageId}/creator-say")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "村建て発言",
        description = "村建てとして発言を登録する。preview なし、確認は frontend のダイアログに委ねる。",
    )
    fun creatorSay(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageCreatorSayBody,
    ) {
        villageContextLoader.loadVillageAndRequireCreator(villageId)
        creatorCoordinator.say(villageId, body.message, body.convertDisable ?: false)
    }

    @PostMapping("/{villageId}/kick")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "強制退村 (村建て)",
        description = "プロローグ中、指定キャラを村建て権限で退村させる。退村メッセージも自動登録される。",
    )
    fun kick(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageKickBody,
    ) {
        villageContextLoader.loadVillageAndRequireCreator(villageId)
        creatorCoordinator.kick(villageId, body.charaId)
    }

    @PostMapping("/{villageId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "廃村",
        description = "プロローグ中のみ村を廃村にする。",
    )
    fun cancel(@PathVariable villageId: Int) {
        villageContextLoader.loadVillageAndRequireCreator(villageId)
        creatorCoordinator.cancel(villageId)
    }

    @PostMapping("/{villageId}/extend-epilogue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "エピローグ延長",
        description = "エピローグの最終日を 1 日後ろにずらす。",
    )
    fun extendEpilogue(@PathVariable villageId: Int) {
        villageContextLoader.loadVillageAndRequireCreator(villageId)
        creatorCoordinator.extendEpilogue(villageId)
    }

    @PostMapping("/{villageId}/shorten-epilogue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "エピローグ短縮",
        description = "エピローグの最終日を 1 日前にずらす (残り 1 日超の場合のみ)。",
    )
    fun shortenEpilogue(@PathVariable villageId: Int) {
        villageContextLoader.loadVillageAndRequireCreator(villageId)
        creatorCoordinator.shortenEpilogue(villageId)
    }
}
