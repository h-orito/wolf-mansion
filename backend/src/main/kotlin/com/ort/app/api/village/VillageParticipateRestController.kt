package com.ort.app.api.village

import com.ort.app.api.request.validator.VillageParticipateFormValidator
import com.ort.app.api.village.request.VillageChangeSkillRequest
import com.ort.app.api.village.request.VillageParticipateRequest
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.app.fw.security.jwt.JwtPrincipal
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.Locale

/**
 * 入村の REST。確認 (検証のみ) → 入村の 2 段フロー。入村可否 (人数・パスワード・キャラ重複など) は
 * 既存 VillageCoordinator.assertParticipate (domain) が検証する。
 */
@RestController
@RequestMapping("/api/v1/villages/{id}")
class VillageParticipateRestController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val villageCoordinator: VillageCoordinator,
    private val villageParticipateFormValidator: VillageParticipateFormValidator,
    private val messageSource: MessageSource,
    private val httpServletRequest: HttpServletRequest,
) {
    /** 入村確認。検証だけ行い、通れば 204 (フロントは確認画面へ進む)。 */
    @Operation(operationId = "confirmVillageParticipate")
    @PostMapping("/participate-confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun confirm(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageParticipateRequest,
    ) {
        val (village, player) = resolvePlayer(principal, id)
        validate(request, null)
        villageCoordinator.assertParticipate(
            village,
            player,
            request.charaId,
            request.charaName,
            request.charaShortName,
            null,
            request.joinPassword,
            request.spectator == true,
        )
    }

    /**
     * 入村する。multipart/form-data で JSON part (`request`) + オリジナル画像 part
     * (`charaImage`、原画村のみ必須) を受ける。IP アドレスの記録は SSR と同じ。
     */
    @Operation(operationId = "participateVillage")
    @PostMapping("/participate", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun participate(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestPart @Validated request: VillageParticipateRequest,
        @RequestPart(required = false) charaImage: MultipartFile?,
    ) {
        val (village, player) = resolvePlayer(principal, id)
        validate(request, charaImage)
        if (village.setting.chara.isOriginalCharachip && charaImage == null) {
            throw WolfMansionBusinessException("キャラクター画像は必須です")
        }
        val first = request.requestedSkill?.let { code -> CDef.Skill.codeOf(code)?.toModel() } ?: Skill(CDef.Skill.おまかせ)
        val second =
            request.secondRequestedSkill?.let { code -> CDef.Skill.codeOf(code)?.toModel() }
                ?: Skill(CDef.Skill.おまかせ)
        villageCoordinator.participate(
            village,
            player,
            request.charaId,
            request.charaName!!,
            request.charaShortName!!,
            charaImage,
            first,
            second,
            request.joinMessage!!,
            request.joinPassword,
            request.spectator == true,
            httpServletRequest.getIpAddress(),
        )
    }

    /** 参加 ⇄ 見学の切替。可否は domain (switchParticipate) が検証する。 */
    @Operation(operationId = "switchVillageParticipate")
    @PostMapping("/switch-participate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun switchParticipate(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        villageCoordinator.switchParticipate(village, myself)
    }

    /** 希望役職 (第 1/第 2) の変更。 */
    @Operation(operationId = "changeVillageRequestSkill")
    @PostMapping("/change-skill")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeSkill(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageChangeSkillRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        val first =
            CDef.Skill.codeOf(request.requestedSkill!!)?.toModel()
                ?: throw WolfMansionBusinessException("skill not found.")
        val second =
            CDef.Skill.codeOf(request.secondRequestedSkill!!)?.toModel()
                ?: throw WolfMansionBusinessException("skill not found.")
        villageCoordinator.changeRequestSkill(village, myself, first, second)
    }

    /** 退村。可否は domain (leave) が検証する。 */
    @Operation(operationId = "leaveVillage")
    @PostMapping("/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun leave(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        villageCoordinator.leave(village, myself)
    }

    private fun resolveParticipant(
        principal: JwtPrincipal?,
        villageId: Int,
    ): Pair<Village, VillageParticipant> {
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(villageId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val myself =
            villageService.findVillageParticipant(village.id, principal.name)
                ?: throw WolfMansionBusinessException("村に参加していません")
        return village to myself
    }

    private fun resolvePlayer(
        principal: JwtPrincipal?,
        villageId: Int,
    ): Pair<Village, Player> {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(villageId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val player =
            playerService.findPlayer(principal.name)
                ?: throw WolfMansionAuthException("ログインしてください")
        return village to player
    }

    private fun validate(
        request: VillageParticipateRequest,
        charaImage: MultipartFile?,
    ) {
        val form = request.toForm(charaImage)
        val errors = BeanPropertyBindingResult(form, "participateForm")
        villageParticipateFormValidator.validate(form, errors)
        if (errors.hasErrors()) {
            throw WolfMansionValidationException(
                errors.fieldErrors.map {
                    FieldErrorItem(it.field, messageSource.getMessage(it, Locale.JAPAN))
                },
            )
        }
    }
}
