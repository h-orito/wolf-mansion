package com.ort.app.api

import com.ort.app.api.request.NewVillageForm
import com.ort.app.api.request.validator.NewVillageFormValidator
import com.ort.app.api.view.NewVillageDivertContent
import com.ort.app.api.view.OptionContent
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.VillageStatus
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
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class NewVillageController(
    private val newVillageFormValidator: NewVillageFormValidator,
    private val villageCoordinator: VillageCoordinator,
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val playerService: PlayerService
) {

    @InitBinder("villageForm")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(newVillageFormValidator)
    }

    // 新規村作成初期表示
    @RequestMapping("/new-village") // 戻る場合もあるのでpostもok
    private fun newVillageIndex(form: NewVillageForm, model: Model): String {
        setIndexModel(form, model)
        return "new-village"
    }

    // 新規村作成_流用
    @PostMapping("/new-village/divert/{villageId}")
    private fun divert(
        @PathVariable villageId: Int,
        form: NewVillageForm,
        model: Model
    ): String {
        setIndexModel(form, model)
        val village = villageService.findVillage(villageId)
            ?: throw IllegalStateException("village not found.")
        form.override(village)
        model.addAttribute("villageForm", form)
        return "new-village"
    }

    // 新規村作成確認画面
    @PostMapping("/new-village/confirm")
    private fun newVillageConfirm(
        @Validated @ModelAttribute("villageForm") villageForm: NewVillageForm,
        result: BindingResult,
        model: Model
    ): String {
        val player = WolfMansionUserInfoUtil.getUserInfo()?.let {
            playerService.findPlayer(it.username)
        }
        val charachip = charaService.findCharachip(villageForm.characterSetId!!)
        if (result.hasErrors() || player == null || charachip == null) {
            setIndexModel(villageForm, model)
            return "new-village"
        }
        try {
            villageCoordinator.assertCreateVillage(player, villageForm.personMaxNum!!, charachip)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            setIndexModel(villageForm, model)
            return "new-village"
        }
        model.addAttribute("characterSetName", charachip.name)
        val dummyChara = charachip.charas.chara(villageForm.dummyCharaId!!)
        model.addAttribute("dummyCharaName", dummyChara.name)
        model.addAttribute("characterImgUrl", dummyChara.defaultImage().url)
        model.addAttribute("characterImgWidth", dummyChara.size.width)
        model.addAttribute("characterImgHeight", dummyChara.size.height)

        // 時間と日時の表示
        model.addAttribute("startDateTime", mapStartDateTime(villageForm))
        model.addAttribute("interval", mapInterval(villageForm))
        return "new-village-confirm"
    }

    // 新規村作成
    @PostMapping("/new-village/create")
    private fun createVillage(
        @Validated @ModelAttribute("villageForm") villageForm: NewVillageForm,
        result: BindingResult,
        model: Model
    ): String {
        val player = WolfMansionUserInfoUtil.getUserInfo()?.let {
            playerService.findPlayer(it.username)
        }
        val charachip = charaService.findCharachip(villageForm.characterSetId!!)
        if (result.hasErrors() || player == null || charachip == null) {
            setIndexModel(villageForm, model)
            return "new-village"
        }
        val village = try {
            villageCoordinator.assertCreateVillage(player, villageForm.personMaxNum!!, charachip)
            villageCoordinator.registerVillage(villageForm.toVillage(player), villageForm.dummyJoinMessage!!)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            setIndexModel(villageForm, model)
            return "new-village"
        }
        return "redirect:/village/${village.id}#bottom"
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private fun mapInterval(villageForm: NewVillageForm): String {
        val hour = String.format("%02d時間", villageForm.dayChangeIntervalHours)
        val minute = String.format("%02d分", villageForm.dayChangeIntervalMinutes)
        val second = String.format("%02d秒", villageForm.dayChangeIntervalSeconds)
        return hour + minute + second
    }

    private fun mapStartDateTime(villageForm: NewVillageForm): String {
        return LocalDateTime.of(
            villageForm.startYear!!, villageForm.startMonth!!, villageForm.startDay!!,
            villageForm.startHour!!, villageForm.startMinute!!, 0
        ).format(DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm"))
    }

    private fun setIndexModel(form: NewVillageForm, model: Model) {
        form.initialize()
        model.addAttribute("villageForm", form)

        val finishedVillages = villageService.findVillages(
            (VillageStatus.settledStatusList + VillageStatus.finishedStatusList).distinct().map { VillageStatus(it) }
        ).list.map { NewVillageDivertContent(it.id, it.id.toString().padStart(4, '0'), it.name) }
        model.addAttribute("villageList", finishedVillages)
        model.addAttribute("skillListStr", Skill.getSkillListStr())
        model.addAttribute("nowYear", LocalDateTime.now().year)
        val charachips = charaService.findCharachips().list.map {
            OptionContent(name = "${it.name}（${it.designer.name}様作）", value = it.id.toString())
        }
        model.addAttribute("characterSetList", charachips)
    }
}