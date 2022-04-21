package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.*
import com.ort.app.api.request.validator.VillageFaceTypeFormValidator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class VillageRpController(
    private val villageFaceTypeFormValidator: VillageFaceTypeFormValidator,
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val villageCoordinator: VillageCoordinator,
    private val villageControllerHelper: VillageControllerHelper
) {

    @InitBinder("faceTypeForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(villageFaceTypeFormValidator)
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 名前を変更する
    @PostMapping("/village/{villageId}/change-name")
    private fun changeName(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("changeNameForm") changeNameForm: VillageChangeNameForm,
        result: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        try {
            villageCoordinator.changeName(village, myself, changeNameForm.name!!, changeNameForm.shortName!!)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("changeNameErrorMessage", e.message)
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }

    // 簡易メモを変更する
    @PostMapping("/village/{villageId}/memo")
    private fun memo(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("changeNameForm") memoForm: VillageMemoForm,
        result: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        try {
            villageService.changeMemo(myself, memoForm.memo!!)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("memoErrorMessage", e.message)
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }

    // 表情差分を編集する
    @PostMapping("/village/{villageId}/modify-face-type")
    private fun faceType(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("faceTypeModifyForm") faceTypeModifyForm: VillageFaceTypeModifyForm,
        result: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms(faceTypeModifyForm = faceTypeModifyForm)
            )
            return "village"
        }
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("ログインしてください")
        try {
            faceTypeModifyForm.faceTypeList!!.forEach {
                charaService.updateOriginalCharaImage(it.code!!, it.name!!, it.display!!)
            }
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("faceTypeModifyErrorMessage", e.message)
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }

    // 表情差分を追加する
    @PostMapping("/village/{villageId}/add-face-type")
    private fun faceType(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("faceTypeForm") faceTypeForm: VillageFaceTypeForm,
        result: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(
                village,
                village.latestDay(),
                model,
                VillageForms(faceTypeForm = faceTypeForm)
            )
            return "village"
        }
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("ログインしてください")
        try {
            charaService.registerOriginalCharaImage(
                village.setting.chara.charachipIds.first(),
                myself.charaId,
                faceTypeForm.faceTypeName!!,
                faceTypeForm.image!!
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("faceTypeErrorMessage", e.message)
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }
}