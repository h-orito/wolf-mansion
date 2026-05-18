package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageActionBody
import com.ort.app.api.request.village.VillageSayBody
import com.ort.app.api.response.message.MessagePreviewView
import com.ort.app.application.coordinator.MessageCoordinator
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 発言 / アクション系の REST API。
 *
 * 既存 `VillageSayController` (Thymeleaf 時代) の置き換え。Body DTO 化 + JSON レスポンスへ。
 * 既存のドメインロジック (`MessageCoordinator.confirmToSay` / `say`) は変更せず利用。
 *
 * - POST /api/v1/villages/{id}/messages/preview: 発言プレビュー (確認画面用)
 * - POST /api/v1/villages/{id}/messages: 発言送信
 * - POST /api/v1/villages/{id}/actions/preview: アクション発言プレビュー
 * - POST /api/v1/villages/{id}/actions: アクション発言送信
 *
 * Say 系は未参加閲覧者からも preview だけ呼べる仕様 (確認画面で「発言できません」を返す既存
 * UX を維持) なので `loadVillageAndOptionalMyself` を使う。`say` の認可は
 * `MessageCoordinator.say` が `myself null` を業務例外として弾く既存実装に委ねる。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageSayRestController(
    private val villageContextLoader: VillageContextLoader,
    private val messageCoordinator: MessageCoordinator,
    private val randomKeywordService: RandomKeywordService,
    private val httpServletRequest: HttpServletRequest,
) {

    @PostMapping("/{villageId}/messages/preview")
    @Operation(
        summary = "発言プレビュー",
        description = "発言送信前にバックエンドが組み立てる Message を確認する。送信は別途 `/messages` を呼ぶ。",
    )
    fun previewSay(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageSayBody,
    ): MessagePreviewView {
        val (village, myself) = villageContextLoader.loadVillageAndOptionalMyself(villageId)
        val messageType = requireValidMessageType(body.messageType)
        val message = messageCoordinator.confirmToSay(
            village,
            myself,
            body.message,
            messageType,
            body.faceType,
            body.convertDisable,
            body.secretSayTargetCharaId,
        )
        return MessagePreviewView(message, randomKeywordService.findRandomKeywords())
    }

    @PostMapping("/{villageId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "発言送信",
        description = "発言を確定して登録する。`secretSayTargetCharaId` は 秘話 のときのみ使用される。",
    )
    fun say(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageSayBody,
    ) {
        val (village, myself) = villageContextLoader.loadVillageAndOptionalMyself(villageId)
        val messageType = requireValidMessageType(body.messageType)
        messageCoordinator.say(
            village,
            myself,
            body.message,
            messageType,
            body.faceType,
            body.convertDisable,
            body.secretSayTargetCharaId,
            httpServletRequest.getIpAddress(),
        )
    }

    @PostMapping("/{villageId}/actions/preview")
    @Operation(
        summary = "アクション発言プレビュー",
        description = "`myself + target + message` を結合した文を CDef.MessageType.アクション として preview する。",
    )
    fun previewAction(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageActionBody,
    ): MessagePreviewView {
        val (village, myself) = villageContextLoader.loadVillageAndOptionalMyself(villageId)
        val text = buildAndValidateActionText(body)
        val message = messageCoordinator.confirmToSay(
            village,
            myself,
            text,
            CDef.MessageType.アクション.code(),
            null,
            body.convertDisable,
            null,
        )
        return MessagePreviewView(message, randomKeywordService.findRandomKeywords())
    }

    @PostMapping("/{villageId}/actions")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "アクション発言送信",
        description = "`myself + target + message` を結合した文を CDef.MessageType.アクション として送信する。",
    )
    fun action(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageActionBody,
    ) {
        val (village, myself) = villageContextLoader.loadVillageAndOptionalMyself(villageId)
        val text = buildAndValidateActionText(body)
        messageCoordinator.say(
            village,
            myself,
            text,
            CDef.MessageType.アクション.code(),
            null,
            body.convertDisable,
            null,
            httpServletRequest.getIpAddress(),
        )
    }

    /**
     * アクション発言の本文を組み立てつつ、合計文字数 (1-400) を検証する。
     * 旧 `ActionFormValidator` の `myself + target + message` 合計長チェックに対応。
     */
    private fun buildAndValidateActionText(body: VillageActionBody): String {
        val text = "${body.myself}${body.target ?: ""}${body.message}".trim()
        if (text.length !in 1..400) {
            throw WolfMansionBusinessException("アクション本文は合計 1〜400 文字以内で入力してください")
        }
        return text
    }

    /**
     * `messageType` が CDef.MessageType に存在することを保証する。
     * 不正値で MessageContent.invoke の `checkNotNull` が `IllegalStateException`
     * → 500 になっていたのを 400 (業務例外) に倒す。
     */
    private fun requireValidMessageType(code: String): String {
        CDef.MessageType.codeOf(code)
            ?: throw WolfMansionBusinessException("invalid messageType: $code")
        return code
    }
}
