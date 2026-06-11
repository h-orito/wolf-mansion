package com.ort.app.api.village

import com.ort.app.api.request.validator.NewVillageFormValidator
import com.ort.app.api.village.request.VillageCreateRequest
import com.ort.app.api.village.request.VillageSearchRequest
import com.ort.app.api.village.response.VillageListResponse
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionValidationException
import com.ort.app.fw.exception.WolfMansionValidationException.FieldErrorItem
import com.ort.app.fw.security.jwt.JwtPrincipal
import org.springdoc.core.annotations.ParameterObject
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
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
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val newVillageFormValidator: NewVillageFormValidator,
    private val messageSource: MessageSource,
) {
    @GetMapping
    fun list(
        @ParameterObject request: VillageSearchRequest,
    ): VillageListResponse = VillageListResponse(villageService.findVillages(request.toQuery()))

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
    ): Village {
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
        return villageCoordinator.registerVillage(
            form.toVillage(player),
            form.dummyCharaName!!,
            form.dummyCharaShortName!!,
            form.dummyCharaImageFile,
            form.dummyJoinMessage!!,
            form.dummyDay1Message,
        )
    }
}
