package com.ort.app.api.village

import com.ort.app.api.request.VillageSayForm
import com.ort.app.api.request.validator.CreatorSayFormValidator
import com.ort.app.api.view.VillageSayConfirmContent
import com.ort.app.api.village.request.VillageCreatorSayRequest
import com.ort.app.api.village.request.VillageKickRequest
import com.ort.app.application.coordinator.CreatorCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import com.ort.app.fw.security.jwt.JwtPrincipal
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.Locale

/** 村建て (creator) 機能の REST。権限検証は resolveCreatorVillage で一元化する。 */
@RestController
@RequestMapping("/api/v1/villages/{id}/creator")
class VillageCreatorRestController(
    private val villageService: VillageService,
    private val creatorCoordinator: CreatorCoordinator,
    private val charaService: CharaService,
    private val randomKeywordService: RandomKeywordService,
    private val messageDomainService: MessageDomainService,
    private val creatorSayFormValidator: CreatorSayFormValidator,
    private val messageSource: MessageSource,
) {
    /** 村建て発言の確認 (プレビュー)。表示用に整形済みの発言を返す (まだ保存しない)。 */
    @Operation(operationId = "confirmVillageCreatorSay")
    @PostMapping("/say-confirm")
    fun confirm(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageCreatorSayRequest,
    ): VillageSayConfirmContent {
        val village = resolveCreatorVillage(principal, id)
        validate(request)
        val message = messageDomainService.createCreatorMessage(village, request.message!!, request.convertDisable ?: false)
        val charas =
            village.setting.chara.let {
                charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas()
            }
        return VillageSayConfirmContent(
            village = village,
            message = message,
            fromParticipant = null,
            player = null,
            charas = charas,
            keywords = randomKeywordService.findRandomKeywords(),
        )
    }

    /** 村建て発言する。 */
    @Operation(operationId = "sayVillageCreator")
    @PostMapping("/say")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun say(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageCreatorSayRequest,
    ) {
        resolveCreatorVillage(principal, id)
        validate(request)
        creatorCoordinator.say(id, request.message!!, request.convertDisable ?: false)
    }

    /** 強制退村 (プロローグ中のみ)。 */
    @Operation(operationId = "kickVillageParticipant")
    @PostMapping("/kick")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun kick(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageKickRequest,
    ) {
        val village = resolveCreatorVillage(principal, id)
        // プロローグ以外では退村画面が表示されないが、サーバ側でも状態を検証する
        if (!village.status.isPrologue()) throw WolfMansionBusinessException("プロローグ中でなければ強制退村できません")
        creatorCoordinator.kick(id, request.charaId!!)
    }

    /** 廃村。可否は CreatorCoordinator (domain) が検証する。 */
    @Operation(operationId = "cancelVillage")
    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancel(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        resolveCreatorVillage(principal, id)
        creatorCoordinator.cancel(id)
    }

    /** エピローグ延長。可否は CreatorCoordinator (domain) が検証する。 */
    @Operation(operationId = "extendVillageEpilogue")
    @PostMapping("/extend-epilogue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun extendEpilogue(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        resolveCreatorVillage(principal, id)
        creatorCoordinator.extendEpilogue(id)
    }

    /** エピローグ短縮。可否は CreatorCoordinator (domain) が検証する。 */
    @Operation(operationId = "shortenVillageEpilogue")
    @PostMapping("/shorten-epilogue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun shortenEpilogue(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        resolveCreatorVillage(principal, id)
        creatorCoordinator.shortenEpilogue(id)
    }

    private fun resolveCreatorVillage(
        principal: JwtPrincipal?,
        villageId: Int,
    ): Village {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(villageId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        if (!creatorCoordinator.isCreator(principal.name, village.id)) throw WolfMansionBusinessException("村建てプレイヤーのみ実行できます")
        return village
    }

    private fun validate(request: VillageCreatorSayRequest) {
        val form =
            VillageSayForm(
                message = request.message,
                messageType = CDef.MessageType.村建て発言.code(),
                convertDisable = request.convertDisable,
            )
        val errors = BeanPropertyBindingResult(form, "creatorSayForm")
        creatorSayFormValidator.validate(form, errors)
        if (errors.hasErrors()) {
            throw WolfMansionValidationException(
                errors.fieldErrors.map {
                    FieldErrorItem(it.field, messageSource.getMessage(it, Locale.JAPAN))
                },
            )
        }
    }
}
