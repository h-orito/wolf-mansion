package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageChangeNameForm
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageMemoForm
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class VillageRpController(
    private val villageService: VillageApplicationService,
    private val villageCoordinator: VillageCoordinator,
    private val villageControllerHelper: VillageControllerHelper
) {

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
}