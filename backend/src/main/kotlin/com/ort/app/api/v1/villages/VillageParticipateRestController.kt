package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageChangeRequestSkillBody
import com.ort.app.api.request.village.VillageParticipateBody
import com.ort.app.api.response.chara.CharaView
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
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
 * 入村 / 退村 / 見学切替 / 希望役職変更 / 選択可キャラ一覧の REST API。
 *
 * 既存 `VillageParticipateController` (Thymeleaf) の置き換え。
 * オリジナルキャラチップ村 (`isOriginalCharachip = true`) でのファイルアップロード入村は
 * 別途 multipart endpoint として将来追加する想定で、本 controller では未対応。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageParticipateRestController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
    private val httpServletRequest: HttpServletRequest,
) {

    @PostMapping("/{villageId}/participate/preview")
    @Operation(
        summary = "入村プレビュー (assertParticipate)",
        description = "入村が可能か確認する。問題なければ 204 を返し、不正なら 400 (例外メッセージは ErrorResponse)。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun previewParticipate(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageParticipateBody,
    ) {
        val (village, player) = loadVillageAndPlayer(villageId)
        rejectOriginalCharachipFlow(village)
        villageCoordinator.assertParticipate(
            village,
            player,
            body.charaId,
            body.charaName,
            body.charaShortName,
            null,
            body.joinPassword,
            body.spectator,
        )
    }

    @PostMapping("/{villageId}/participate")
    @Operation(summary = "入村")
    @ResponseStatus(HttpStatus.CREATED)
    fun participate(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageParticipateBody,
    ) {
        val (village, player) = loadVillageAndPlayer(villageId)
        rejectOriginalCharachipFlow(village)
        val first = resolveSkill(body.requestedSkill)
        val second = resolveSkill(body.secondRequestedSkill)
        villageCoordinator.participate(
            village,
            player,
            body.charaId,
            body.charaName,
            body.charaShortName,
            null,
            first,
            second,
            body.joinMessage,
            body.joinPassword,
            body.spectator,
            httpServletRequest.getIpAddress(),
        )
    }

    @PostMapping("/{villageId}/participate/switch")
    @Operation(
        summary = "参加 / 見学 切替",
        description = "プロローグ中、参加者 ↔ 見学者の状態を切り替える。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun switchParticipate(@PathVariable villageId: Int) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        villageCoordinator.switchParticipate(village, myself)
    }

    @PutMapping("/{villageId}/participate/skill")
    @Operation(summary = "希望役職変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeRequestSkill(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageChangeRequestSkillBody,
    ) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        val first = CDef.Skill.codeOf(body.requestedSkill)?.let { Skill(it) }
            ?: throw WolfMansionBusinessException("skill not found. code=${body.requestedSkill}")
        val second = CDef.Skill.codeOf(body.secondRequestedSkill)?.let { Skill(it) }
            ?: throw WolfMansionBusinessException("skill not found. code=${body.secondRequestedSkill}")
        villageCoordinator.changeRequestSkill(village, myself, first, second)
    }

    @DeleteMapping("/{villageId}/participate")
    @Operation(summary = "退村")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun leave(@PathVariable villageId: Int) {
        val (village, myself) = loadVillageAndRequireMyself(villageId)
        villageCoordinator.leave(village, myself)
    }

    @GetMapping("/{villageId}/participate/selectable-charas")
    @Operation(
        summary = "選択可能キャラ一覧",
        description = "指定キャラチップに属するキャラのうち、当該村で参加に選べるものを返す。",
    )
    fun selectableCharas(
        @PathVariable villageId: Int,
        @Parameter(description = "キャラチップ ID", required = true)
        @RequestParam charachipId: Int,
    ): List<CharaView> {
        val charachip = charaService.findCharachips(listOf(charachipId), false).list.firstOrNull()
            ?: throw WolfMansionRecordNotFoundException("charachip not found. id=$charachipId")
        return villageCoordinator.findSelectableCharaList(villageId, charachip.id).map { CharaView(it) }
    }

    // ---------- helper ----------

    private fun loadVillageAndPlayer(villageId: Int): Pair<Village, Player> {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val user = WolfMansionUserInfoUtil.getUserInfo()
            ?: throw WolfMansionBusinessException("ログインが必要です")
        val player = playerService.findPlayer(user.username)
            ?: throw WolfMansionBusinessException("player not found.")
        return village to player
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

    private fun rejectOriginalCharachipFlow(village: Village) {
        if (village.setting.chara.isOriginalCharachip) {
            // multipart 画像アップロードを伴う original charachip 入村は別 endpoint で扱う予定 (issue 化候補)
            throw WolfMansionBusinessException("オリジナルキャラチップ村への入村は現状この API では未対応です")
        }
    }

    private fun resolveSkill(code: String?): Skill {
        if (code.isNullOrBlank()) return Skill(CDef.Skill.おまかせ)
        return CDef.Skill.codeOf(code)?.let { Skill(it) }
            ?: throw WolfMansionBusinessException("skill not found. code=$code")
    }
}
