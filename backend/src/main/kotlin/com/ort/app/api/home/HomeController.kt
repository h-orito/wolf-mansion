package com.ort.app.api.home

import com.ort.app.api.home.response.HomeResponse
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.canCreateVillage
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.fw.security.jwt.JwtPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * ホーム (公開ランディング) の REST。SSR の `IndexController.index` 相当。
 * 開催中 (未終了) の村一覧は公開情報。`canCreateVillage` はログイン時のみ player から判定する。
 */
@RestController
@RequestMapping("/api/v1/home")
class HomeController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
) {
    @GetMapping
    fun home(
        @AuthenticationPrincipal principal: JwtPrincipal?,
    ): HomeResponse {
        val villages =
            villageService.findVillages(
                query = VillageQuery(statuses = VillageStatus.notFinishedStatusList.map { VillageStatus(it) }),
            )
        val player = principal?.let { playerService.findPlayer(it.name) }
        return HomeResponse(villages, player.canCreateVillage())
    }
}
