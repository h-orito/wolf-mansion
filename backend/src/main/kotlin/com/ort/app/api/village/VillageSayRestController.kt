package com.ort.app.api.village

import com.ort.app.api.request.validator.SayFormValidator
import com.ort.app.api.view.VillageSayConfirmContent
import com.ort.app.api.village.request.VillageSayRequest
import com.ort.app.application.coordinator.MessageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
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

/**
 * 発言の REST。確認 (プレビュー) → 投稿の 2 段フロー。いずれも要認証で、
 * 発言可否・種別の妥当性は既存 MessageCoordinator (domain) が検証する。
 */
@RestController
@RequestMapping("/api/v1/villages/{id}")
class VillageSayRestController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val randomKeywordService: RandomKeywordService,
    private val messageCoordinator: MessageCoordinator,
    private val sayFormValidator: SayFormValidator,
    private val messageSource: MessageSource,
    private val httpServletRequest: HttpServletRequest,
) {
    /** 発言確認 (プレビュー)。表示用に整形済みの発言を返す (まだ保存しない)。 */
    @Operation(operationId = "confirmVillageSay")
    @PostMapping("/say-confirm")
    fun confirm(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageSayRequest,
    ): VillageSayConfirmContent {
        val (village, myself) = resolveSayer(principal, id)
        validate(request)
        val message =
            messageCoordinator.confirmToSay(
                village,
                myself,
                request.message!!,
                request.messageType!!,
                request.faceType!!,
                request.convertDisable,
                request.secretSayTargetCharaId,
            )
        val player = playerService.findPlayer(myself.playerId)
        val charas =
            village.setting.chara.let {
                charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas()
            }
        return VillageSayConfirmContent(
            village = village,
            message = message,
            fromParticipant = myself,
            player = player,
            charas = charas,
            keywords = randomKeywordService.findRandomKeywords(),
        )
    }

    /** 発言する。IP アドレスの記録は SSR と同じ (不正対策)。 */
    @Operation(operationId = "sayVillage")
    @PostMapping("/say")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun say(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageSayRequest,
    ) {
        val (village, myself) = resolveSayer(principal, id)
        validate(request)
        messageCoordinator.say(
            village,
            myself,
            request.message!!,
            request.messageType!!,
            request.faceType,
            request.convertDisable,
            request.secretSayTargetCharaId,
            httpServletRequest.getIpAddress(),
        )
    }

    private fun resolveSayer(
        principal: JwtPrincipal?,
        villageId: Int,
    ): Pair<Village, VillageParticipant> {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(villageId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val myself =
            villageService.findVillageParticipant(village.id, principal.name)
                ?: throw WolfMansionBusinessException("村に参加していません")
        return village to myself
    }

    private fun validate(request: VillageSayRequest) {
        val form = request.toForm()
        val errors = BeanPropertyBindingResult(form, "sayForm")
        sayFormValidator.validate(form, errors)
        if (errors.hasErrors()) {
            throw WolfMansionValidationException(
                errors.fieldErrors.map {
                    FieldErrorItem(it.field, messageSource.getMessage(it, Locale.JAPAN))
                },
            )
        }
    }
}
