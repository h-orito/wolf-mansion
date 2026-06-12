package com.ort.app.api.village

import com.ort.app.api.village.request.VillageAdminLeaveRequest
import com.ort.app.api.village.response.AdminVillagePlayersResponse
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.AdminVillageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.security.jwt.JwtPrincipal
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/** 管理者 (admin) 機能の REST。権限検証は resolveAdminVillage で一元化する。 */
@RestController
@RequestMapping("/api/v1/villages/{id}/admin")
class VillageAdminRestController(
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator,
    private val adminVillageService: AdminVillageService,
    private val playerService: PlayerService,
) {
    /** 参加者を強制退村させる。 */
    @Operation(operationId = "adminLeaveVillageParticipant")
    @PostMapping("/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun leave(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Validated request: VillageAdminLeaveRequest,
    ) {
        val village = resolveAdminVillage(principal, id)
        // 退村済み (gone) は取得時点で除外されるため、ここに到達するのは在籍中の参加者のみ。
        // 所属検証は別村の villagePlayerId を弾くためのもの
        val participant =
            villageService.findVillageParticipant(request.villagePlayerId!!)
                ?: throw WolfMansionBusinessException("村参加者が見つかりません")
        if (village.allParticipants().list.none { it.id == participant.id }) {
            throw WolfMansionBusinessException("この村の参加者ではありません")
        }
        villageCoordinator.leave(village, participant)
    }

    /** 全参加者のアクセス日時を現在日時に更新する。 */
    @Operation(operationId = "adminUpdateAllAccess")
    @PostMapping("/access")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun access(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        val village = resolveAdminVillage(principal, id)
        adminVillageService.updateAllLastAccessDatetime(village.id)
    }

    /** 未投票の生存者に自己投票を追加する。 */
    @Operation(operationId = "adminInsertSelfVotes")
    @PostMapping("/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun vote(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ) {
        val village = resolveAdminVillage(principal, id)
        adminVillageService.insertSelfVotesForNonVoters(village.id)
    }

    /** 村の参加プレイヤー一覧を取得する。 */
    @Operation(operationId = "getAdminVillagePlayers")
    @GetMapping("/players")
    fun players(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ): AdminVillagePlayersResponse {
        val village = resolveAdminVillage(principal, id)
        return AdminVillagePlayersResponse.of(adminVillageService.findVillageCharaPlayers(village.id))
    }

    /** 管理者権限は村単位ではなくシステム全体のもの (村との関係は問わない)。 */
    private fun resolveAdminVillage(
        principal: JwtPrincipal?,
        villageId: Int,
    ): Village {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(villageId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        // 重要操作のため JWT claim でなく DB を再確認して管理者権限を検証する
        val player = playerService.findPlayer(principal.name)
        if (player == null || player.authority.toCdef() != CDef.Authority.管理者) {
            throw WolfMansionBusinessException("管理者のみ実行できます")
        }
        return village
    }
}
