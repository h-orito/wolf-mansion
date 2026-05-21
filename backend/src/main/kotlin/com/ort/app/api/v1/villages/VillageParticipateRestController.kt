package com.ort.app.api.v1.villages

import com.ort.app.api.request.village.VillageChangeRequestSkillBody
import com.ort.app.api.request.village.VillageParticipateBody
import com.ort.app.api.response.chara.CharaView
import com.ort.app.api.v1.support.ImageUploadValidator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 入村 / 退村 / 見学切替 / 希望役職変更 / 選択可キャラ一覧の REST API。
 *
 * 既存 `VillageParticipateController` (Thymeleaf) の置き換え。
 *
 * オリジナルキャラチップ村 (`isOriginalCharachip = true`) は multipart 版エンドポイント
 * (`POST /api/v1/villages/{id}/participate`, `consumes=multipart/form-data`) を使う。
 * JSON 版は非オリジナル村専用。preview も同様 (画像不要、JSON で `assertParticipate` のみ)。
 *
 * `/participate/switch` は state-changing action だが、リクエスト body を持たない単純トグルで
 * あり PUT/PATCH より POST が自然と判断して POST のまま採用している。
 */
@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageParticipateRestController(
    private val villageContextLoader: VillageContextLoader,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
    private val httpServletRequest: HttpServletRequest,
) {

    @PostMapping("/{villageId}/participate/preview")
    @Operation(
        summary = "入村プレビュー (assertParticipate)",
        description = "入村が可能か確認する。問題なければ 204、不正なら 400 (例外メッセージは ErrorResponse)。" +
                "オリジナルキャラチップ村でも JSON で OK (画像のサイズ等は最終 submit 時の multipart で検証)。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun previewParticipate(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageParticipateBody,
    ) {
        val (village, player) = villageContextLoader.loadVillageAndPlayer(villageId)
        villageCoordinator.assertParticipate(
            village,
            player,
            body.charaId,
            body.charaName,
            body.charaShortName,
            // オリジナル村でも preview は assertParticipate の charaImageFile 引数を null で許容する
            // (`assertParticipate` 内部の `charaImageFile?.size == 0L` ガードは null では false 評価で素通り)。
            // ファイルサイズ等の画像バリデーションは最終 submit (multipart) で行う。
            null,
            body.joinPassword,
            body.spectator,
        )
    }

    @PostMapping("/{villageId}/participate", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "入村 (非オリジナルキャラチップ)",
        description = "公式キャラチップ村への入村。オリジナル村は multipart 版 `POST /participate` を使う (400)。",
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun participate(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageParticipateBody,
    ) {
        val (village, player) = villageContextLoader.loadVillageAndPlayer(villageId)
        if (village.setting.chara.isOriginalCharachip) {
            throw WolfMansionBusinessException(
                "オリジナルキャラチップ村は multipart endpoint を使用してください。",
            )
        }
        val charaId = body.charaId
            ?: throw WolfMansionBusinessException("キャラクターを選択してください")
        val first = resolveSkill(body.requestedSkill)
        val second = resolveSkill(body.secondRequestedSkill)
        villageCoordinator.participate(
            village,
            player,
            charaId,
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

    @PostMapping("/{villageId}/participate", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(
        summary = "入村 (オリジナルキャラチップ)",
        description = "オリジナルキャラチップ村への入村。`body` (JSON、`VillageParticipateBody`) と `charaImage` (画像) の 2 パート構成。" +
                "非オリジナル村に対してこの endpoint を呼ぶと 400。" +
                "画像は 1〜100KB、許可拡張子は png / jpg / jpeg / gif / webp。",
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun participateOriginal(
        @PathVariable villageId: Int,
        @Valid @RequestPart("body") body: VillageParticipateBody,
        @RequestPart("charaImage") charaImage: MultipartFile,
    ) {
        val (village, player) = villageContextLoader.loadVillageAndPlayer(villageId)
        if (!village.setting.chara.isOriginalCharachip) {
            throw WolfMansionBusinessException(
                "オリジナルキャラチップ村ではないため multipart endpoint は使用できません。",
            )
        }
        ImageUploadValidator.validate(charaImage)
        val first = resolveSkill(body.requestedSkill)
        val second = resolveSkill(body.secondRequestedSkill)
        villageCoordinator.participate(
            village,
            player,
            body.charaId,
            body.charaName,
            body.charaShortName,
            charaImage,
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
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        villageCoordinator.switchParticipate(village, myself)
    }

    @PutMapping("/{villageId}/participate/skill")
    @Operation(summary = "希望役職変更")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeRequestSkill(
        @PathVariable villageId: Int,
        @Valid @RequestBody body: VillageChangeRequestSkillBody,
    ) {
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
        val first = resolveSkill(body.requestedSkill)
        val second = resolveSkill(body.secondRequestedSkill)
        villageCoordinator.changeRequestSkill(village, myself, first, second)
    }

    @DeleteMapping("/{villageId}/participate")
    @Operation(summary = "退村")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun leave(@PathVariable villageId: Int) {
        val (village, myself) = villageContextLoader.loadVillageAndRequireMyself(villageId)
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

    private fun resolveSkill(code: String?): Skill {
        if (code.isNullOrBlank()) return Skill(CDef.Skill.おまかせ)
        return CDef.Skill.codeOf(code)?.let { Skill(it) }
            ?: throw WolfMansionBusinessException("skill not found. code=$code")
    }
}
