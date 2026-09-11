package com.ort.app.api.village

import com.ort.app.api.village.request.VillageDebugParticipateRequest
import com.ort.app.api.village.response.VillageDebugView
import com.ort.app.application.service.DebugVillageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/** ローカル開発向けデバッグ操作の REST。debug 無効時は GET は空応答、POST は 404 を返す。 */
@RestController
@RequestMapping("/api/v1/villages/{id}/debug")
class VillageDebugRestController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val debugVillageService: DebugVillageService,
) {
    @Value("\${app.debug:}")
    private lateinit var debug: String

    /** デバッグ情報を取得する。debug 無効時は isDebugMode=false・players 空を返す。 */
    @Operation(operationId = "getVillageDebugInfo")
    @GetMapping("")
    fun debugInfo(
        @PathVariable id: Int,
    ): VillageDebugView {
        if (!debug.toBoolean()) return VillageDebugView(isDebugMode = false, players = emptyList())
        val village =
            villageService.findVillage(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val players = playerService.findPlayers(id)
        val playerViews =
            village
                .allParticipants()
                .sortedByRoomNumber()
                .list
                .map { participant ->
                    val name = participant.name()
                    val skillSuffix = participant.skill?.name?.let { ": $it" } ?: ""
                    val userId = players.player(participant.playerId).name
                    VillageDebugView.DebugPlayerView(
                        userId = userId,
                        label = "$name$skillSuffix",
                    )
                }
        return VillageDebugView(isDebugMode = true, players = playerViews)
    }

    /** 未参加キャラを personNumber 分入村させる。プロローグ以外では business error。 */
    @Operation(operationId = "debugAllParticipateVillage")
    @PostMapping("/all-participate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun allParticipate(
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageDebugParticipateRequest,
    ) {
        if (!debug.toBoolean()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
        val village =
            villageService.findVillage(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        if (!village.status.isPrologue()) throw WolfMansionBusinessException("プロローグ中のみ実行できます")
        debugVillageService.allParticipate(id, request.personNumber!!)
    }

    /** 最新 VillageDay の daychangeDatetime を過去にして日付更新を強制実行する。 */
    @Operation(operationId = "debugDayChangeVillage")
    @PostMapping("/day-change")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun dayChange(
        @PathVariable id: Int,
    ) {
        if (!debug.toBoolean()) throw ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
        debugVillageService.forceDayChange(id)
    }
}
