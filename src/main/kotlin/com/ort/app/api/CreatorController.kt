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
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.setting.SayRestriction
import com.ort.app.domain.model.village.setting.VillageOrganize
import com.ort.app.domain.model.village.setting.VillageRandomOrganize
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
class CreatorController(
    // validator
    private val villageSayFormValidator: CreatorSayFormValidator,
    private val villageSettingFormValidator: SettingFormValidator,
    // helper
    private val villageControllerHelper: VillageControllerHelper,
    // service
    private val creatorCoordinator: CreatorCoordinator,
    private val villageService: VillageApplicationService,
    private val randomKeywordService: RandomKeywordService,
    private val charaService: CharaService
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
    private fun settingsIndex(@PathVariable villageId: Int, model: Model): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        val village = villageService.findVillage(villageId) ?: throw WolfMansionBusinessException("village not found.")
        val charachip = charaService.findCharachip(village.setting.charachipId)
            ?: throw WolfMansionBusinessException("charachip not found.")
        setSettingsIndexModel(village, charachip, model)
        return "village-settings"
    }

    // 村設定変更
    @PostMapping("/village/{villageId}/settings")
    private fun storeSettings(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("settingsForm") form: VillageSettingForm,
        bindingResult: BindingResult,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId) ?: throw WolfMansionBusinessException("village not found.")
        val charachip = charaService.findCharachip(village.setting.charachipId)
            ?: throw WolfMansionBusinessException("charachip not found.")
        if (bindingResult.hasErrors()) {
            setSettingsIndexModel(village, charachip, model)
            return "village-settings"
        }
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        try {
            creatorCoordinator.saveSettings(merge(village, form))
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            setSettingsIndexModel(village, charachip, model)
            return "village-settings"
        }
        return "redirect:/village/$villageId#bottom"
    }

    // 村建て機能：強制退村
    @PostMapping("/village/{villageId}/kick")
    private fun kick(
        @PathVariable villageId: Int,
        kickForm: VillageKickForm
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
        model: Model
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
        model: Model
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
        @PathVariable villageId: Int
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
        @PathVariable villageId: Int
    ): String {
        if (!creatorCoordinator.isCreator(WolfMansionUserInfoUtil.getUserInfo()?.username, villageId)) {
            return "redirect:/village/$villageId#bottom"
        }
        creatorCoordinator.extendEpilogue(villageId)
        return "redirect:/village/$villageId#bottom"
    }

    private fun setSettingsIndexModel(
        village: Village,
        charachip: Charachip,
        model: Model,
        form: VillageSettingForm? = null
    ) {
        model.addAttribute("content", VillageSettingsContent(village, charachip))
        model.addAttribute("skillListStr", Skill.getSkillListStr())
        model.addAttribute("nowYear", LocalDate.now().year)
        model.addAttribute(
            "settingsForm",
            form ?: VillageSettingForm(village)
        )
    }

    private fun merge(village: Village, form: VillageSettingForm): Village {
        val startDatetime = LocalDateTime.of(
            form.startYear!!,
            form.startMonth!!,
            form.startDay!!,
            form.startHour!!,
            form.startMinute!!
        )
        return village.copy(
            days = village.days.copy(
                list = village.days.list.map {
                    if (it.day == 0) it.copy(dayChangeDatetime = startDatetime)
                    else it.copy()
                }
            ),
            setting = village.setting.copy(
                personMin = form.startPersonMinNum!!,
                personMax = form.personMaxNum!!,
                dayChangeIntervalSeconds = form.dayChangeIntervalHours!! * 3600 + form.dayChangeIntervalMinutes!! * 60 + form.dayChangeIntervalSeconds!!,
                startDatetime = startDatetime,
                rule = village.setting.rule.copy(
                    isOpenVote = form.isOpenVote!!,
                    isAvailableSameWolfAttack = form.isAvailableSameWolfAttack!!,
                    isOpenSkillInGrave = form.isOpenSkillInGrave!!,
                    isVisibleGraveSpectateMessage = form.isVisibleGraveSpectateMessage!!,
                    isAvailableSecretSay = form.allowedSecretSayCode!! != CDef.AllowedSecretSay.なし.code(),
                    isAvailableSpectate = form.isAvailableSpectate!!,
                    isAvailableSuddenlyDeath = form.isAvailableSuddonlyDeath!!,
                    isAvailableCommit = form.isAvailableCommit!!,
                    isAvailableGuardSameTarget = form.isAvailableGuardSameTarget!!,
                    isAvailableAction = form.isAvailableAction!!,
                    isRandomOrganization = form.isRandomOrganization!!
                ),
                organize = VillageOrganize(
                    fixedOrganization = form.organization.orEmpty(),
                    randomOrganization = VillageRandomOrganize(
                        campAllocation = form.campAllocationList?.map {
                            VillageRandomOrganize.CampAllocation(
                                camp = Camp(CDef.Camp.codeOf(it.campCode)),
                                min = it.minNum!!,
                                max = it.maxNum,
                                allocation = it.allocation!!
                            )
                        } ?: emptyList(),
                        skillAllocation = form.campAllocationList?.flatMap { it.skillAllocation!! }?.map {
                            VillageRandomOrganize.SkillAllocation(
                                skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                                min = it.minNum!!,
                                max = it.maxNum,
                                allocation = it.allocation!!
                            )
                        } ?: emptyList()
                    )
                ),
                joinPassword = form.joinPassword,
                sayRestriction = SayRestriction(
                    normalSayRestriction = form.sayRestrictList!!.filter { it.restrict!! }.map {
                        SayRestriction.NormalSayRestriction(
                            skill = Skill(CDef.Skill.codeOf(it.skillCode)),
                            messageType = MessageType(CDef.MessageType.通常発言),
                            count = it.count!!,
                            length = it.length!!
                        )
                    },
                    skillSayRestriction = (form.skillSayRestrictList!! + form.rpSayRestrictList!!).filter { it.restrict!! }
                        .map {
                            SayRestriction.SkillSayRestriction(
                                messageType = MessageType(CDef.MessageType.codeOf(it.messageTypeCode)),
                                count = it.count!!,
                                length = it.length!!
                            )
                        }
                )
            )
        )
    }
}