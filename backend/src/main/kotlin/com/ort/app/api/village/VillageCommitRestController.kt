package com.ort.app.api.village

import com.ort.app.api.village.request.VillageCommitRequest
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.VillageService
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

/** コミット (時間前の日付更新確定) の REST。可否は既存 VillageCoordinator.setCommit (domain) が検証する。 */
@RestController
@RequestMapping("/api/v1/villages/{id}/commit")
class VillageCommitRestController(
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator,
) {
    /** コミットを ON/OFF する (トグル)。 */
    @Operation(operationId = "setVillageCommit")
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setCommit(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageCommitRequest,
    ) {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val myself =
            villageService.findVillageParticipant(village.id, principal.name)
                ?: throw WolfMansionBusinessException("村に参加していません")
        villageCoordinator.setCommit(village, myself, request.commit!!)
    }
}
