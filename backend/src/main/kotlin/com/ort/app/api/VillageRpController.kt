package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageChangeNameForm
import com.ort.app.api.request.VillageFaceTypeForm
import com.ort.app.api.request.VillageFaceTypeModifyForm
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageMemoForm
import com.ort.app.api.request.validator.VillageFaceTypeFormValidator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.interceptor.getRefererQueryString
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException

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
        request: HttpServletRequest,  //
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
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    @PutMapping("/api/village/{villageId}/my-name")
    @ResponseBody
    private fun apiChangeName(
        @PathVariable villageId: Int,
        @Validated body: VillageChangeNameForm,
    ) {
        val village = villageService.findVillage(villageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "ログインしてください")
        villageCoordinator.changeName(village, myself, body.name!!, body.shortName!!)
    }

    // 簡易メモを変更する
    @PostMapping("/village/{villageId}/memo")
    private fun memo(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("changeNameForm") memoForm: VillageMemoForm,
        request: HttpServletRequest,  //
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
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    @PostMapping("/api/village/{villageId}/memo")
    @ResponseBody
    private fun apiMemo(
        @PathVariable villageId: Int,
        @RequestBody @Validated body: VillageMemoForm,
    ) {
        val village = villageService.findVillage(villageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "ログインしてください")
        villageService.changeMemo(myself, body.memo!!)
    }

    // 表情差分を編集する
    @PostMapping("/village/{villageId}/modify-face-type")
    private fun faceType(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("faceTypeModifyForm") faceTypeModifyForm: VillageFaceTypeModifyForm,
        request: HttpServletRequest,  //
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
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    @PutMapping("/api/village/{villageId}/face-type")
    @ResponseBody
    private fun apiFaceType(
        @PathVariable villageId: Int,
        @RequestBody @Validated @ModelAttribute("faceTypeModifyForm") body: VillageFaceTypeModifyForm,
    ) {
        val village = villageService.findVillage(villageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found. id: $villageId")
        WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("ログインしてください")
        body.faceTypeList!!.forEach {
            charaService.updateOriginalCharaImage(it.code!!, it.name!!, it.display!!)
        }
    }

    // 表情差分を追加する
    @PostMapping("/village/{villageId}/add-face-type")
    private fun faceType(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("faceTypeForm") faceTypeForm: VillageFaceTypeForm,
        request: HttpServletRequest,  //
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
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    @PostMapping("/api/village/{villageId}/face-type")
    @ResponseBody
    private fun apiPostFaceType(
        @PathVariable villageId: Int,
        @RequestBody @Validated @ModelAttribute("faceTypeForm") body: VillageFaceTypeForm,
    ) {
        val village = villageService.findVillage(villageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("ログインしてください")
        charaService.registerOriginalCharaImage(
            village.setting.chara.charachipIds.first(),
            myself.charaId,
            body.faceTypeName!!,
            body.image!!
        )
    }
}