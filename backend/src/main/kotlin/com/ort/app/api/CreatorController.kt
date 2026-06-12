package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageKickForm
import com.ort.app.api.request.VillageSayForm
import com.ort.app.api.request.VillageSettingForm
import com.ort.app.api.request.validator.CreatorSayFormValidator
import com.ort.app.api.request.validator.SettingFormValidator
import com.ort.app.api.view.VillageSettingsContent
import com.ort.app.application.coordinator.CreatorCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Controller
class CreatorController(
    // validator
    private val villageSayFormValidator: CreatorSayFormValidator,
    private val villageSettingFormValidator: SettingFormValidator,
    // helper
    private val villageControllerHelper: VillageControllerHelper,
    // service
    private val creatorCoordinator: CreatorCoordinator,
    private val villageService: VillageService,
    private val randomKeywordService: RandomKeywordService,
    private val charaService: CharaService,
) {
    @InitBinder("creatorSayForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(villageSayFormValidator)
    }

    @InitBinder("settingsForm")
    fun initSettingsBinder(binder: WebDataBinder) {
        binder.addValidators(villageSettingFormValidator)
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村設定変更
    @GetMapping("/village/{villageId}/settings")
    private fun settingsIndex(
        @PathVariable villageId: Int,
        model: Model,
    ): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        val village = villageService.findVillage(villageId) ?: throw WolfMansionBusinessException("village not found.")
        val charachips =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip) }
        setSettingsIndexModel(village, charachips, model)
        return "village-settings"
    }

    // 村設定変更
    @PostMapping("/village/{villageId}/settings")
    private fun storeSettings(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("settingsForm") form: VillageSettingForm,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        val village = villageService.findVillage(villageId) ?: throw WolfMansionBusinessException("village not found.")
        val charachips =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip) }
        if (bindingResult.hasErrors()) {
            setSettingsIndexModel(village, charachips, model, form)
            return "village-settings"
        }
        if (village.setting.chara.isOriginalCharachip && form.joinPassword.isNullOrEmpty()) {
            model.addAttribute("errorMessage", "オリジナルキャラクターを登録する村ではパスワードは必須です")
            setSettingsIndexModel(village, charachips, model)
            return "village-settings"
        }
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        try {
            creatorCoordinator.saveSettings(form.mergeTo(village))
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            setSettingsIndexModel(village, charachips, model)
            return "village-settings"
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 村建て機能：強制退村
    @PostMapping("/village/{villageId}/kick")
    private fun kick(
        @PathVariable villageId: Int,
        kickForm: VillageKickForm,
    ): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        val charaId = kickForm.charaId ?: throw WolfMansionBusinessException("存在しない参加者です")
        creatorCoordinator.kick(villageId, charaId)
        return "redirect:/village/$villageId#bottom"
    }

    // 村建て機能：村建て発言確認画面へ
    @PostMapping("/village/{villageId}/creator-say-confirm")
    private fun confirm(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("creatorSayForm") creatorSayForm: VillageSayForm,
        result: BindingResult,
        model: Model,
    ): String {
        val village = villageService.findVillage(villageId) ?: return "redirect:/village/$villageId#bottom"
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        model.addAttribute("villageId", villageId)
        model.addAttribute("villageName", village.name)
        val keywords = randomKeywordService.findRandomKeywords()
        model.addAttribute("randomKeywords", keywords.list.joinToString(",") { it.keyword })
        return "creator-say-confirm"
    }

    // 村建て機能：村建て発言
    @PostMapping("/village/{villageId}/creator-say")
    private fun creatorSay(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("creatorSayForm") creatorSayForm: VillageSayForm,
        result: BindingResult,
        model: Model,
    ): String {
        val village = villageService.findVillage(villageId) ?: return "redirect:/village/$villageId#bottom"
        if (result.hasErrors()) {
            model.addAttribute("creatorSayForm", creatorSayForm)
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        creatorCoordinator.say(villageId, creatorSayForm.message!!, creatorSayForm.convertDisable ?: false)
        return "redirect:/village/$villageId#bottom"
    }

    // 村建て機能：廃村
    @PostMapping("/village/{villageId}/cancel")
    private fun cancel(
        @PathVariable villageId: Int,
    ): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        creatorCoordinator.cancel(villageId)
        return "redirect:/village/$villageId#bottom"
    }

    // 村建て機能：エピローグ延長
    @PostMapping("/village/{villageId}/extend-epilogue")
    private fun extend(
        @PathVariable villageId: Int,
    ): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        creatorCoordinator.extendEpilogue(villageId)
        return "redirect:/village/$villageId#bottom"
    }

    // 村建て機能：エピローグ短縮
    @PostMapping("/village/{villageId}/shorten-epilogue")
    private fun shorten(
        @PathVariable villageId: Int,
    ): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        creatorCoordinator.shortenEpilogue(villageId)
        return "redirect:/village/$villageId#bottom"
    }

    private fun setSettingsIndexModel(
        village: Village,
        charachips: Charachips,
        model: Model,
        form: VillageSettingForm? = null,
    ) {
        model.addAttribute("content", VillageSettingsContent(village, charachips))
        model.addAttribute("skillListStr", Skills.getSkillListStr())
        model.addAttribute("nowYear", LocalDate.now().year)
        model.addAttribute(
            "settingsForm",
            form ?: VillageSettingForm(village),
        )
    }
}
