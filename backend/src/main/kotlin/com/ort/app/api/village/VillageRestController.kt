package com.ort.app.api.village

import com.ort.app.api.request.validator.NewVillageFormValidator
import com.ort.app.api.village.request.VillageCreateRequest
import com.ort.app.api.village.request.VillageSearchRequest
import com.ort.app.api.village.response.ParticipantSituationView
import com.ort.app.api.village.response.VillageCreateResponse
import com.ort.app.api.village.response.VillageDetailView
import com.ort.app.api.village.response.VillageListResponse
import com.ort.app.api.village.response.VillageSettingView
import com.ort.app.api.village.response.VillageSituationView
import com.ort.app.api.village.response.VillageUpdateResponse
import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.SpoilerDomainService
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springdoc.core.annotations.ParameterObject
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.Locale

/**
 * 村の REST。一覧は公開 (トップページ = 未終了村、村一覧画面 = 全村 + 絞り込み)、作成は要認証。
 * 状態・キャラセット・役職・編成での絞り込みと並び順は [VillageSearchRequest] で受ける。
 * 村作成可否は player の情報なので本 API では返さない (me の `canCreateVillage`)。
 */
@RestController
@RequestMapping("/api/v1/villages")
class VillageRestController(
    private val villageService: VillageService,
    private val villageCoordinator: VillageCoordinator,
    private val daychangeCoordinator: DaychangeCoordinator,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val voteService: VoteApplicationService,
    private val footstepService: FootstepApplicationService,
    private val abilityService: AbilityService,
    private val spoilerDomainService: SpoilerDomainService,
    private val newVillageFormValidator: NewVillageFormValidator,
    private val messageSource: MessageSource,
) {
    @GetMapping
    fun list(
        @ParameterObject request: VillageSearchRequest,
    ): VillageListResponse = VillageListResponse(villageService.findVillages(request.toQuery()))

    /**
     * 村詳細。公開情報のみ (入村パスワードは [VillageSettingView] が除外)。
     * operationId は明示する (`detail`/`update` は他 Controller と単純名が衝突し、
     * SpringDoc の自動連番が探索順で揺れて spec の不要差分になるため)。
     */
    @Operation(operationId = "getVillage")
    @GetMapping("/{id}")
    fun detail(
        @PathVariable id: Int,
    ): VillageDetailView = VillageDetailView(findVillageOrThrow(id))

    /**
     * 村状況 (状況サマリ)。村全体の現況のため認証不要だが、ログインしていれば
     * スポイラーマスク (役職・能力欄・足音の表示形式) に視点を反映する。
     */
    @Operation(operationId = "getVillageSituation")
    @GetMapping("/{id}/situation")
    fun situation(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam(required = false) day: Int?,
    ): VillageSituationView {
        val village = findVillageOrThrow(id)
        val targetDay = resolveDay(village, day)
        val myself = principal?.let { villageService.findVillageParticipant(village.id, it.name) }
        val player = principal?.let { playerService.findPlayer(it.name) }
        val votes = voteService.findVotes(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        val abilities = abilityService.findAbilities(village.id)
        val charachips =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip) }
        val villageSituation =
            villageCoordinator.findVillageSituation(
                village = village,
                myself = myself,
                votes = votes,
                abilities = abilities,
                footsteps = footsteps,
                day = targetDay,
            )
        return VillageSituationView(
            village = village,
            day = targetDay,
            villageSituation = villageSituation,
            charachips = charachips,
            myself = myself,
            player = player,
            isViewableSpoilerContent = spoilerDomainService.isViewableSpoilerContent(village, myself),
        )
    }

    /** 参加者本人の状態 (capability)。ログインユーザー固有のため認証必須。 */
    @Operation(operationId = "getMyVillageSituation")
    @GetMapping("/{id}/situation/me")
    fun mySituation(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam(required = false) day: Int?,
    ): ParticipantSituationView {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village = findVillageOrThrow(id)
        val targetDay = resolveDay(village, day)
        val myself = villageService.findVillageParticipant(village.id, principal.name)
        val votes = voteService.findVotes(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        val abilities = abilityService.findAbilities(village.id)
        val charachips =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip) }
        return ParticipantSituationView(
            villageCoordinator.findParticipantSituation(
                village = village,
                username = principal.name,
                myself = myself,
                votes = votes,
                abilities = abilities,
                footsteps = footsteps,
                charachips = charachips,
                day = targetDay,
            ),
        )
    }

    /**
     * 村ポーリング。最終アクセス日時の更新と、更新時刻を過ぎていた場合の日付更新を行う
     * (日付更新は本番でもポーリング駆動)。匿名の閲覧者もポーリングするため認証不要。
     * 応答の latestDay は日付更新前に取得した村のもので、更新が起きた場合は次回ポーリングで
     * 新しい日付を返す。
     */
    @Operation(operationId = "updateVillage")
    @PostMapping("/{id}/update")
    fun update(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
    ): VillageUpdateResponse {
        val village =
            villageService.findVillage(id, excludeGone = false)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        principal
            ?.let { villageService.findVillageParticipant(village.id, it.name) }
            ?.let { villageService.updateLastAccessDatetime(it) }
        daychangeCoordinator.changeDayIfNeeded(village)
        return VillageUpdateResponse(latestDay = village.latestDay())
    }

    /** 村設定。村画面で公開されている情報のため認証不要 (入村パスワードのみマスク)。 */
    @GetMapping("/{id}/setting")
    fun setting(
        @PathVariable id: Int,
    ): VillageSettingView =
        villageService.findVillage(id)?.let { VillageSettingView(it.setting) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")

    private fun findVillageOrThrow(id: Int): Village =
        villageService.findVillage(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")

    /** 表示対象日。省略時は最新日、存在しない日は 400。 */
    private fun resolveDay(
        village: Village,
        day: Int?,
    ): Int {
        day ?: return village.latestDay()
        if (village.days.list.none { it.day == day }) {
            throw WolfMansionBusinessException("存在しない日付です: $day")
        }
        return day
    }

    /**
     * 村作成。multipart/form-data で JSON part (`request`) + オリジナルダミーキャラ画像
     * (`dummyCharaImage`、任意) を受ける。相関検証は SSR と共通の [NewVillageFormValidator] を
     * 流用し、フィールドエラーは ProblemDetail の `fieldErrors` で返す。
     */
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @RequestPart @Validated request: VillageCreateRequest,
        @RequestPart(required = false) dummyCharaImage: MultipartFile?,
    ): VillageCreateResponse {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        val player =
            principal?.let { playerService.findPlayer(it.name) }
                ?: throw WolfMansionAuthException("ログインしてください")

        val codeErrors = request.validateCodes()
        if (codeErrors.isNotEmpty()) throw WolfMansionValidationException(codeErrors)

        val form = request.toForm(dummyCharaImage)
        val errors = BeanPropertyBindingResult(form, "villageForm")
        newVillageFormValidator.validate(form, errors)
        if (errors.hasErrors()) {
            throw WolfMansionValidationException(
                errors.fieldErrors.map {
                    FieldErrorItem(it.field, messageSource.getMessage(it, Locale.JAPAN))
                },
            )
        }

        val isOriginal = form.shouldOriginalImage!!
        val charachips = charaService.findCharachips(form.characterSetId!!, isOriginal)
        if (!isOriginal) {
            if (charachips.list.size != form.characterSetId!!.size) {
                throw WolfMansionValidationException(
                    listOf(FieldErrorItem("characterSetId", "存在しないキャラセットが指定されています")),
                )
            }
            if (charachips.list.flatMap { it.charas.list }.none { it.id == form.dummyCharaId }) {
                throw WolfMansionValidationException(
                    listOf(FieldErrorItem("dummyCharaId", "選択したキャラセットに含まれないダミーキャラです")),
                )
            }
        }

        // 村建て可否・キャラ数不足は WolfMansionBusinessException → 400 (business_error)
        villageCoordinator.assertCreateVillage(player, form.personMaxNum!!, charachips, isOriginal)
        val village =
            villageCoordinator.registerVillage(
                form.toVillage(player),
                form.dummyCharaName!!,
                form.dummyCharaShortName!!,
                form.dummyCharaImageFile,
                form.dummyJoinMessage!!,
                form.dummyDay1Message,
            )
        return VillageCreateResponse(village)
    }
}
