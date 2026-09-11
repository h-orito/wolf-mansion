package com.ort.app.api.village

import com.ort.app.api.village.request.VillageAbilityRequest
import com.ort.app.api.village.response.AbilityCandidatesView
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * 役職能力の REST。セット可否・対象妥当性は既存 VillageCoordinator.setAbility (domain) が検証する。
 * 候補はビューア本人の役職知識に基づくため要認証。
 */
@RestController
@RequestMapping("/api/v1/villages/{id}/ability")
class VillageAbilityRestController(
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator,
) {
    /** 能力をセットする。 */
    @Operation(operationId = "setVillageAbility")
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setAbility(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody request: VillageAbilityRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        villageCoordinator.setAbility(
            village,
            myself,
            request.attackerCharaId,
            request.targetCharaId,
            request.footstep,
        )
    }

    /** 襲撃者を選んだときの襲撃対象候補。 */
    @Operation(operationId = "getVillageAttackTargets")
    @GetMapping("/attack-targets")
    fun attackTargets(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam charaId: Int,
    ): AbilityCandidatesView {
        val (village, myself) = resolveParticipant(principal, id)
        val targets = villageCoordinator.getAttackableTargets(village, myself, charaId)
        return AbilityCandidatesView(
            targets = targets.list.map { AbilityCandidatesView.TargetView(it) },
            footsteps = emptyList(),
        )
    }

    /** 対象を選んだときの足音 (通過する部屋) 候補。 */
    @Operation(operationId = "getVillageAbilityFootsteps")
    @GetMapping("/footsteps")
    fun footsteps(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam(required = false) charaId: Int?,
        @RequestParam(required = false) targetCharaId: Int?,
    ): AbilityCandidatesView {
        val (village, myself) = resolveParticipant(principal, id)
        val footsteps = villageCoordinator.getSelectableFootstepList(village, myself, charaId, targetCharaId)
        return AbilityCandidatesView(targets = emptyList(), footsteps = footsteps)
    }

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
