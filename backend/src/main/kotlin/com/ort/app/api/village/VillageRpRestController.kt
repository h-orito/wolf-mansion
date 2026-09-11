package com.ort.app.api.village

import com.ort.app.api.village.request.VillageChangeNameRequest
import com.ort.app.api.village.request.VillageMemoRequest
import com.ort.app.api.village.request.VillageModifyFaceTypesRequest
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.RpDomainService
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

/**
 * RP 支援 (名前変更・簡易メモ) の REST。SSR は表示制御のみで実行可否を検証していないため、
 * RpDomainService の situation 変換を流用して可否をサーバ側でも検証する。
 */
@RestController
@RequestMapping("/api/v1/villages/{id}")
class VillageRpRestController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
    private val rpDomainService: RpDomainService,
) {
    /** キャラ名・略称を変更する。 */
    @Operation(operationId = "changeVillageCharaName")
    @PostMapping("/change-name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeName(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageChangeNameRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        if (!rpSituation(village, myself).isAvailableChangeName) {
            throw WolfMansionBusinessException("名前を変更できません")
        }
        villageCoordinator.changeName(village, myself, request.name!!, request.shortName!!)
    }

    /** 簡易メモを変更する。 */
    @Operation(operationId = "changeVillageMemo")
    @PostMapping("/memo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeMemo(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageMemoRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        if (!rpSituation(village, myself).isAvailableMemo) {
            throw WolfMansionBusinessException("簡易メモを変更できません")
        }
        villageService.changeMemo(myself, request.memo!!)
    }

    /** 表情差分を追加する（原画村限定）。 */
    @Operation(operationId = "addVillageFaceType")
    @PostMapping("/face-types", consumes = ["multipart/form-data"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun addFaceType(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestPart faceTypeName: String,
        @RequestPart image: MultipartFile,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        if (!rpSituation(village, myself).canAddImage) {
            throw WolfMansionBusinessException("表情差分を追加できません")
        }
        validateFaceTypeName(faceTypeName)
        validateImage(image)
        charaService.registerOriginalCharaImage(
            village.setting.chara.charachipIds
                .first(),
            myself.charaId,
            faceTypeName,
            image,
        )
    }

    /** 表情差分を一括編集する（原画村限定）。 */
    @Operation(operationId = "modifyVillageFaceTypes")
    @PutMapping("/face-types")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun modifyFaceTypes(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageModifyFaceTypesRequest,
    ) {
        val (village, myself) = resolveParticipant(principal, id)
        if (!rpSituation(village, myself).canAddImage) {
            throw WolfMansionBusinessException("表情差分を編集できません")
        }
        val chara =
            charaService.findChara(myself.charaId, true)
                ?: throw WolfMansionBusinessException("キャラクターが見つかりません")
        val ownCodes =
            chara.images.list
                .map { it.faceType.code }
                .toSet()
        request.list!!.forEach { item ->
            if (item.code!! !in ownCodes) {
                throw WolfMansionBusinessException("自分のキャラの表情差分のみ編集できます")
            }
            charaService.updateOriginalCharaImage(item.code, item.name!!, item.isDisplay!!)
        }
    }

    private fun validateFaceTypeName(name: String) {
        if (name.isBlank() || name.length > 5) {
            throw WolfMansionBusinessException("表情差分名は1～5文字で入力してください")
        }
    }

    private fun validateImage(image: MultipartFile) {
        if (image.isEmpty || image.size > 100_000L) {
            throw WolfMansionBusinessException("画像は100KB以下のファイルを指定してください")
        }
        if (image.contentType?.startsWith("image/") != true) {
            throw WolfMansionBusinessException("画像ファイルを指定してください")
        }
    }

    private fun rpSituation(
        village: Village,
        myself: VillageParticipant,
    ) = rpDomainService.convertToSituation(
        village = village,
        myself = myself,
        charachips =
            village.setting.chara.let {
                charaService.findCharachips(it.charachipIds, it.isOriginalCharachip)
            },
        day = village.latestDay(),
    )

    private fun resolveParticipant(
        principal: JwtPrincipal?,
        villageId: Int,
    ): Pair<Village, VillageParticipant> {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(villageId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val myself =
            villageService.findVillageParticipant(village.id, principal.name)
                ?: throw WolfMansionBusinessException("村に参加していません")
        return village to myself
    }
}
