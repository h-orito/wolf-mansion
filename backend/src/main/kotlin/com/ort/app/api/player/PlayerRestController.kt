package com.ort.app.api.player

import com.ort.app.api.player.request.PlayerSearchRequest
import com.ort.app.api.player.response.PlayerListResponse
import com.ort.app.api.view.PlayerRecordsContent
import com.ort.app.application.coordinator.PlayerCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.constraints.Size
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/players")
class PlayerRestController(
    private val playerService: PlayerService,
    private val playerCoordinator: PlayerCoordinator,
    private val charaService: CharaService,
) {
    @GetMapping
    @Operation(operationId = "getPlayers")
    fun list(
        @ParameterObject request: PlayerSearchRequest,
    ): PlayerListResponse = PlayerListResponse(playerService.findAllPlayers(pageSize = 30, pageNum = request.pageNum ?: 1))

    @GetMapping("/{name}")
    @Operation(operationId = "getPlayerByName")
    fun getPlayerByName(
        @PathVariable name: String,
    ): PlayerRecordsContent {
        val player =
            playerService.findPlayer(name)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "player not found")
        val playerRecords = playerCoordinator.findPlayerRecords(player)
        val originalCharachipVillages = playerRecords.participateVillageList.filter { it.village.setting.chara.isOriginalCharachip }
        val originalCharaIdList = originalCharachipVillages.map { it.participant.charaId }
        val originalCharas = charaService.findCharasByCharachipId(originalCharaIdList, true)
        val charachipVillages = playerRecords.participateVillageList.filterNot { it.village.setting.chara.isOriginalCharachip }
        val charaIdList = charachipVillages.map { it.participant.charaId }
        val charas = charaService.findCharasByCharachipId(charaIdList, false)
        return PlayerRecordsContent(playerRecords, charas, originalCharas, player.twitterUserName, player.introduction)
    }

    @PutMapping("/me/detail")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(operationId = "updateMyPlayerDetail")
    fun updateMyDetail(
        @AuthenticationPrincipal principal: JwtPrincipal,
        @RequestBody request: PlayerDetailRequest,
    ) {
        playerService.updatePlayerDetail(principal.name, request.twitterUserName, request.introduction)
    }
}

data class PlayerDetailRequest(
    @field:Size(max = 50)
    val twitterUserName: String? = null,
    @field:Size(max = 2000)
    val introduction: String? = null,
)
