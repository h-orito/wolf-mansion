package com.ort.app.api.v1.admin

import com.ort.app.api.request.village.VillageAdminLeaveBody
import com.ort.app.api.response.village.VillageAdminPlayerView
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.AdminVillageService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 管理者 (ROLE_ADMIN) 専用 操作の REST API。
 *
 * `/api/v1/admin/` 配下は SecurityConfig で `hasRole("ADMIN")` 必須に設定済みのため、
 * controller 内で追加の権限チェックは不要。
 *
 * 旧 `AdminController` (Thymeleaf form) の置き換え。strong-side operation 用なので
 * UI 上は管理者のみ AdminPanel から呼び出す想定。
 *
 * - GET  /api/v1/admin/villages/{id}/players: 村参加プレイヤー一覧 (キャラ ↔ 中の人)
 * - POST /api/v1/admin/villages/{id}/access:  全参加者の最終アクセス時刻を now に更新
 * - POST /api/v1/admin/villages/{id}/vote:    当日未投票の参加者に「自分票」を一括追加
 * - POST /api/v1/admin/villages/{id}/leave:   指定 villagePlayerId を強制退村
 */
@RestController
@RequestMapping("/api/v1/admin/villages")
@Tag(name = "admin-villages", description = "管理者向け村操作")
class VillageAdminRestController(
    private val adminVillageService: AdminVillageService,
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator,
) {

    @GetMapping("/{villageId}/players")
    @Operation(
        summary = "参加プレイヤー一覧",
        description = "村に参加中のキャラ名と中の人プレイヤー名を返す。退村済 (gone) は除外。",
    )
    fun players(@PathVariable villageId: Int): List<VillageAdminPlayerView> {
        return adminVillageService.listVillageCharaPlayers(villageId).map {
            VillageAdminPlayerView(charaName = it.charaName, playerName = it.playerName)
        }
    }

    @PostMapping("/{villageId}/access")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "全員アクセス",
        description = "全参加者の最終アクセス時刻を now() に更新する (突然死しないようにする救済操作)。",
    )
    fun forceAccess(@PathVariable villageId: Int) {
        adminVillageService.updateAllLastAccess(villageId)
    }

    @PostMapping("/{villageId}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "全員自分投票",
        description = "当日まだ投票していない生存者全員に「自分票」を入れる (進行不能回避用)。",
    )
    fun forceVoteForSelf(@PathVariable villageId: Int) {
        adminVillageService.voteForSelfAll(villageId)
    }

    @PostMapping("/{villageId}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "強制退村 (管理者)",
        description = "指定 villagePlayerId の参加者を退村させる。assertLeave を通すためプロローグ中のみ成功する。",
    )
    fun forceLeave(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageAdminLeaveBody,
    ) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val participant = villageService.findVillageParticipant(body.villagePlayerId)
            ?: throw WolfMansionBusinessException("参加者が見つかりません")
        villageCoordinator.leave(village, participant)
    }
}
