package com.ort.app.api.village

import com.ort.app.api.view.PlayerRecordsContent
import com.ort.app.application.coordinator.PlayerCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/players")
class PlayerRestController(
    private val playerService: PlayerService,
    private val playerCoordinator: PlayerCoordinator,
    private val charaService: CharaService,
) {
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
}
