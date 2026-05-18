package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageAbilityBody
import com.ort.app.api.request.village.VillageCommitBody
import com.ort.app.api.request.village.VillageVoteBody
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * 能力 / 投票 / コミット系の REST API。
 *
 * 既存 `VillageAbilityController` (Thymeleaf) の置き換え。
 *
 * - POST /api/v1/villages/{id}/abilities: 能力セット
 * - POST /api/v1/villages/{id}/votes: 投票セット
 * - PUT  /api/v1/villages/{id}/commit: 行動確定 (commit)
 * - GET  /api/v1/villages/{id}/abilities/attack-targets: 襲撃対象候補
 * - GET  /api/v1/villages/{id}/abilities/footstep-candidates: 足音候補
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageAbilityRestController(
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator,
) {

    @PostMapping("/{villageId}/abilities")
    @Operation(
        summary = "能力セット",
        description = "対象 / 足音 を null にすると当日の能力発動を取り消す。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setAbility(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageAbilityBody,
    ) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        villageCoordinator.setAbility(village, myself, body.attackerCharaId, body.targetCharaId, body.footstep)
    }

    @PostMapping("/{villageId}/votes")
    @Operation(summary = "投票セット")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setVote(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageVoteBody,
    ) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        villageCoordinator.setVote(village, myself, body.targetCharaId)
    }

    @PutMapping("/{villageId}/commit")
    @Operation(summary = "行動確定 (commit)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun setCommit(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageCommitBody,
    ) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        villageCoordinator.setCommit(village, myself, body.commit)
    }

    @GetMapping("/{villageId}/abilities/attack-targets")
    @Operation(
        summary = "襲撃可能対象一覧",
        description = "指定キャラが今日襲撃可能な参加者の charaId 一覧。",
    )
    fun attackTargets(
        @PathVariable villageId: Int,
        @Parameter(description = "襲撃者のキャラ ID", required = true)
        @RequestParam charaId: Int,
    ): List<Int> {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        return villageCoordinator.getAttackableTargets(village, myself, charaId).list.map { it.charaId }
    }

    @GetMapping("/{villageId}/abilities/footstep-candidates")
    @Operation(
        summary = "足音候補一覧",
        description = "指定キャラが指定対象に対して残せる足音 (カンマ区切り部屋番号 / 'なし') の候補リスト。",
    )
    fun footstepCandidates(
        @PathVariable villageId: Int,
        @Parameter(description = "能力主体のキャラ ID")
        @RequestParam(required = false) charaId: Int?,
        @Parameter(description = "能力対象のキャラ ID (null なら 'なし' のみ)")
        @RequestParam(required = false) targetCharaId: Int?,
    ): List<String> {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        return villageCoordinator.getSelectableFootstepList(village, myself, charaId, targetCharaId)
    }

    private fun loadVillageAndRequireMyself(villageId: Int): Pair<Village, VillageParticipant> {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val user = WolfMansionUserInfoUtil.getUserInfo()
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val myself = villageService.findVillageParticipant(village.id, user.username)
            ?: throw WolfMansionBusinessException("この村に参加していません")
        return village to myself
    }
}
