package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageActionForm
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageSayForm
import com.ort.app.api.request.validator.ActionFormValidator
import com.ort.app.api.request.validator.SayFormValidator
import com.ort.app.api.view.VillageSayConfirmContent
import com.ort.app.application.coordinator.MessageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.RandomKeywordService
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.web.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import com.ort.fw.util.WolfMansionUserInfoUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class VillageSayController(
    // validator
    private val sayFormValidator: SayFormValidator,
    private val actionFormValidator: ActionFormValidator,
    // service
    private val messageCoordinator: MessageCoordinator,
    private val randomKeywordService: RandomKeywordService,
    private val villageService: VillageApplicationService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val villageControllerHelper: VillageControllerHelper
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @InitBinder("sayForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(sayFormValidator)
    }

    @InitBinder("actionForm")
    fun initBinderAction(binder: WebDataBinder) {
        binder.addValidators(actionFormValidator)
    }

    // 発言確認へ
    @PostMapping("/village/{villageId}/confirm")
    @ResponseBody
    private fun confirm(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("sayForm") sayForm: VillageSayForm,
        result: BindingResult,
        model: Model
    ): VillageSayConfirmContent? {
        if (result.hasErrors()) return null
        val village = villageService.findVillage(villageId) ?: return null
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()?.let { villageService.findVillageParticipant(village.id, it) }
        try {
            val message = messageCoordinator.confirmToSay(
                village,
                myself,
                sayForm.message!!,
                sayForm.messageType!!,
                sayForm.faceType!!,
                sayForm.convertDisable,
                sayForm.secretSayTargetCharaId
            )
            val player = playerService.findPlayer(myself!!.playerId)
            val charas = charaService.findCharas(village.setting.charachipId)
            val randomKeywords = randomKeywordService.findRandomKeywords()
            return VillageSayConfirmContent(
                village = village,
                message = message,
                fromParticipant = myself,
                player = player,
                charas = charas,
                keywords = randomKeywords
            )
        } catch (e: Exception) {
            logger.info(e.message, e)
            return null
        }
    }

    // 発言する
    @PostMapping("/village/{villageId}/say")
    private fun say(
        @PathVariable villageId: Int,  //
        @Validated @ModelAttribute("sayForm") sayForm: VillageSayForm,  //
        result: BindingResult,  //
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it)
        }
        try {
            messageCoordinator.say(
                village,
                myself,
                sayForm.message!!,
                sayForm.messageType!!,
                sayForm.faceType,
                sayForm.convertDisable,
                sayForm.secretSayTargetCharaId
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("sayErrorMessage", e.message)
        }
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }

    // アクション確認画面へ
    @PostMapping("/village/{villageId}/action-confirm")
    @ResponseBody
    private fun actionConfirm(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("actionForm") actionForm: VillageActionForm, result: BindingResult, model: Model
    ): VillageSayConfirmContent? {
        if (result.hasErrors()) return null
        val village = villageService.findVillage(villageId) ?: return null
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()?.let { villageService.findVillageParticipant(village.id, it) }
        try {
            val message = messageCoordinator.confirmToSay(
                village,
                myself,
                "${actionForm.myself!!}${actionForm.target ?: ""}${actionForm.message!!}",
                CDef.MessageType.アクション.code(),
                null,
                actionForm.convertDisable,
                null
            )
            val player = playerService.findPlayer(myself!!.playerId)
            val charas = charaService.findCharas(village.setting.charachipId)
            val randomKeywords = randomKeywordService.findRandomKeywords()
            return VillageSayConfirmContent(
                village = village,
                message = message,
                fromParticipant = myself,
                player = player,
                charas = charas,
                keywords = randomKeywords
            )
        } catch (e: Exception) {
            logger.info(e.message, e)
            return null
        }
    }

    // アクション発言する
    @PostMapping("/village/{villageId}/action")
    private fun action(
        @PathVariable villageId: Int,  //
        @Validated @ModelAttribute("actionForm") actionForm: VillageActionForm,  //
        result: BindingResult,  //
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it)
        }
        try {
            messageCoordinator.say(
                village,
                myself,
                "${actionForm.myself!!}${actionForm.target ?: ""}${actionForm.message!!}",
                CDef.MessageType.アクション.code(),
                null,
                actionForm.convertDisable,
                null
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("actionErrorMessage", e.message)
        }
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }
}