package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageChangeRequestSkillForm
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageParticipateForm
import com.ort.app.api.request.validator.VillageParticipateFormValidator
import com.ort.app.api.view.village.VillageFormContent
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.interceptor.getIpAddress
import com.ort.app.fw.interceptor.getRefererQueryString
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
class VillageParticipateController(
    // validator
    private val villageParticipateFormValidator: VillageParticipateFormValidator,
    // service
    private val villageControllerHelper: VillageControllerHelper,
    private val villageCoordinator: VillageCoordinator,
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val playerService: PlayerService,
    // servlet
    private val httpServletRequest: HttpServletRequest
) {

    @InitBinder("participateForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(villageParticipateFormValidator)
    }

    // 入村確認画面へ
    @PostMapping("/village/{villageId}/confirm-participate")
    private fun confirmParticipate(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("participateForm") participateForm: VillageParticipateForm,
        request: HttpServletRequest,  //
        result: BindingResult,
        model: Model //
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val player = WolfMansionUserInfoUtil.getUserInfo()?.let { playerService.findPlayer(it.username) }
        if (result.hasErrors() || player == null) {
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms(participateForm = participateForm)
            )
            return "village"
        }
        try {
            villageCoordinator.assertParticipate(
                village,
                player,
                participateForm.charaId,
                participateForm.charaName,
                participateForm.charaShortName,
                participateForm.charaImageFile,
                participateForm.joinPassword,
                participateForm.spectator == true
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("participateErrorMessage", e.message)
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms(participateForm = participateForm)
            )
            return "village"
        }

        if (!village.setting.chara.isOriginalCharachip) {
            val chara = charaService.findChara(participateForm.charaId!!, false)
                ?: throw WolfMansionBusinessException("chara not found.")
            model.addAttribute("characterImgUrl", chara.defaultImage().url)
            model.addAttribute("characterImgWidth", chara.size.width)
            model.addAttribute("characterImgHeight", chara.size.height)
        } else {
            model.addAttribute("characterImgHeight", 60)
        }
        model.addAttribute("isOriginalChara", village.setting.chara.isOriginalCharachip)
        model.addAttribute("villageId", villageId)
        model.addAttribute("villageName", village.name)

        // 発言確認画面へ
        return "participate-confirm"
    }

    // 入村
    @PostMapping("/village/{villageId}/participate")
    private fun participate(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("participateForm") participateForm: VillageParticipateForm,
        request: HttpServletRequest,  //
        result: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val player = WolfMansionUserInfoUtil.getUserInfo()?.let { playerService.findPlayer(it.username) }
        if (result.hasErrors() || player == null) {
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms(participateForm = participateForm)
            )
            return "village"
        }
        val first = participateForm.requestedSkill?.let {
            CDef.Skill.codeOf(it)?.let { cdef -> Skill(cdef) }
        } ?: Skill(CDef.Skill.おまかせ)
        val second = participateForm.secondRequestedSkill?.let {
            CDef.Skill.codeOf(it)?.let { cdef -> Skill(cdef) }
        } ?: Skill(CDef.Skill.おまかせ)

        try {
            if (village.setting.chara.isOriginalCharachip && participateForm.charaImageFile == null) {
                throw WolfMansionBusinessException("キャラクター画像は必須です")
            }
            villageCoordinator.participate(
                village,
                player,
                participateForm.charaId,
                participateForm.charaName,
                participateForm.charaShortName,
                participateForm.charaImageFile,
                first,
                second,
                participateForm.joinMessage!!,
                participateForm.joinPassword,
                participateForm.spectator == true,
                httpServletRequest.getIpAddress()
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("participateErrorMessage", e.message)
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms(participateForm = participateForm)
            )
            return "village"
        }
        // 最新の日へ
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    // 参加見学切り替え
    @PostMapping("/village/{villageId}/switch-participate")
    private fun switchParticipate(
        @PathVariable villageId: Int,
        request: HttpServletRequest,  //
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        if (myself == null) {
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms()
            )
            return "village"
        }

        try {
            villageCoordinator.switchParticipate(village, myself)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("participateErrorMessage", e.message)
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms()
            )
            return "village"
        }
        // 最新の日へ
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    // 希望役職変更
    @PostMapping("/village/{villageId}/change-skill")
    private fun changeSkill(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("changeRequestSkill") changeRequestSkillForm: VillageChangeRequestSkillForm,
        request: HttpServletRequest,  //
        result: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        if (result.hasErrors() || myself == null) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        val first = CDef.Skill.codeOf(changeRequestSkillForm.requestedSkill!!)?.let { cdef -> Skill(cdef) }
            ?: throw WolfMansionBusinessException("skill not found.")
        val second = CDef.Skill.codeOf(changeRequestSkillForm.secondRequestedSkill!!)?.let { cdef -> Skill(cdef) }
            ?: throw WolfMansionBusinessException("skill not found.")
        try {
            villageCoordinator.changeRequestSkill(village, myself, first, second)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("changeRequestSkillErrorMessage", e.message)
        }
        // 最新の日へ
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    // 退村
    @PostMapping("/village/{villageId}/leave")
    private fun leave(
        @PathVariable villageId: Int,
        request: HttpServletRequest,  //
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        if (myself == null) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        try {
            villageCoordinator.leave(village, myself)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("leaveErrorMessage", e.message)
        }
        // 最新の日へ
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    @GetMapping("/getSelectableCharaList/{villageId}")
    @ResponseBody
    private fun getSelectableCharaList(
        @PathVariable villageId: Int,
        form: GetSelectableCharaListForm
    ): List<VillageFormContent.VillageParticipateFormContent.VillageCharaContent> {
        val charas = villageCoordinator.findSelectableCharaList(villageId, form.charachipId)
        return charas.map { VillageFormContent.VillageParticipateFormContent.VillageCharaContent(it) }
    }

    data class GetSelectableCharaListForm(val charachipId: Int = 0)
}