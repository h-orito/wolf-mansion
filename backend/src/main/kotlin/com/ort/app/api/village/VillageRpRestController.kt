package com.ort.app.api.village

import com.ort.app.api.village.request.VillageChangeNameRequest
import com.ort.app.api.village.request.VillageMemoRequest
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.RpDomainService
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * RP 支援 (名前変更・簡易メモ) の REST。SSR は表示制御のみで実行可否を検証していないため、
 * RpDomainService の situation 変換を流用して可否をサーバ側でも検証する。
 */
@RestController
@RequestMapping("/api/v1/villages/{id}")
class VillageRpRestController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
    private val rpDomainService: RpDomainService,
) {
    /** キャラ名・略称を変更する。 */
    @Operation(operationId = "changeVillageCharaName")
    @PostMapping("/change-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeName(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageChangeNameRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        if (!rpSituation(village, myself).isAvailableChangeName) {
            throw WolfMansionBusinessException("名前を変更できません")
        }
        villageCoordinator.changeName(village, myself, request.name!!, request.shortName!!)
    }

    /** 簡易メモを変更する。 */
    @Operation(operationId = "changeVillageMemo")
    @PostMapping("/memo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeMemo(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageMemoRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        if (!rpSituation(village, myself).isAvailableMemo) {
            throw WolfMansionBusinessException("簡易メモを変更できません")
        }
        villageService.changeMemo(myself, request.memo!!)
    }

    private fun rpSituation(
        village: Village,
        myself: VillageParticipant,
    ) = rpDomainService.convertToSituation(
        village = village,
        myself = myself,
        charachips =
            village.setting.chara.let {
                charaService.findCharachips(it.charachipIds, it.isOriginalCharachip)
            },
        day = village.latestDay(),
    )

    private fun resolveParticipant(
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
}
