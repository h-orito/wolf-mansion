package com.ort.app.api

import com.ort.app.api.request.NewVillageForm
import com.ort.app.api.request.validator.NewVillageFormValidator
import com.ort.app.api.view.NewVillageDivertContent
import com.ort.app.api.view.OptionContent
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.fw.exception.WolfMansionBadRequestException
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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
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
        if (result.hasErrors() || player == null) {
            setIndexModel(villageForm, model)
            return "new-village"
        }
        val isOriginal = villageForm.shouldOriginalImage!!
        val charachips = charaService.findCharachips(villageForm.characterSetId!!, isOriginal)
        if (!isOriginal && charachips.list.size != villageForm.characterSetId!!.size) {
            setIndexModel(villageForm, model)
            return "new-village"
        }
        try {
            villageCoordinator.assertCreateVillage(player, villageForm.personMaxNum!!, charachips, isOriginal)
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            setIndexModel(villageForm, model)
            return "new-village"
        }
        if (!isOriginal) {
            model.addAttribute("characterSetName", charachips.list.joinToString(separator = "、") { it.name })
            val dummyChara = charachips.list.flatMap { it.charas.list }.first { it.id == villageForm.dummyCharaId!! }
            model.addAttribute("dummyCharaName", dummyChara.name)
            model.addAttribute("characterImgUrl", dummyChara.defaultImage().url)
            model.addAttribute("characterImgWidth", dummyChara.size.width)
            model.addAttribute("characterImgHeight", dummyChara.size.height)
        } else {
            model.addAttribute("characterImgHeight", 60)
        }

        // 時間と日時の表示
        model.addAttribute("startDateTime", mapStartDateTime(villageForm))
        model.addAttribute("interval", mapInterval(villageForm))
        return "new-village-confirm"
    }

    @PostMapping("/api/new-village/confirm")
    @ResponseBody
    private fun apiNewVillageConfirm(
        @RequestBody @Validated @ModelAttribute("villageForm") villageForm: NewVillageForm,
    ) {
        val player = WolfMansionUserInfoUtil.getUserInfo()?.let {
            playerService.findPlayer(it.username)
        } ?: throw WolfMansionBadRequestException("player not found.")
        val isOriginal = villageForm.shouldOriginalImage!!
        val charachips = charaService.findCharachips(villageForm.characterSetId!!, isOriginal)
        if (!isOriginal && charachips.list.size != villageForm.characterSetId!!.size) {
            throw WolfMansionBadRequestException("charachip not found.")
        }
        villageCoordinator.assertCreateVillage(player, villageForm.personMaxNum!!, charachips, isOriginal)
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
        if (result.hasErrors() || player == null) {
            setIndexModel(villageForm, model)
            return "new-village"
        }
        val isOriginal = villageForm.shouldOriginalImage!!
        val charachips = charaService.findCharachips(villageForm.characterSetId!!, isOriginal)
        if (!isOriginal && charachips.list.size != villageForm.characterSetId!!.size) {
            setIndexModel(villageForm, model)
            return "new-village"
        }
        val village = try {
            villageCoordinator.assertCreateVillage(player, villageForm.personMaxNum!!, charachips, isOriginal)
            villageCoordinator.registerVillage(
                villageForm.toVillage(player),
                villageForm.dummyCharaName!!,
                villageForm.dummyCharaShortName!!,
                villageForm.dummyCharaImageFile,
                villageForm.dummyJoinMessage!!,
                villageForm.dummyDay1Message
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("errorMessage", e.message)
            setIndexModel(villageForm, model)
            return "new-village"
        }
        return "redirect:/village/${village.id}#bottom"
    }

    @PostMapping("/api/new-village/create")
    @ResponseBody
    private fun apiCreateVillage(
        @RequestBody @Validated @ModelAttribute("villageForm") villageForm: NewVillageForm,
    ): NewVillageResponse {
        val player = WolfMansionUserInfoUtil.getUserInfo()?.let {
            playerService.findPlayer(it.username)
        } ?: throw WolfMansionBadRequestException("player not found.")
        val isOriginal = villageForm.shouldOriginalImage!!
        val charachips = charaService.findCharachips(villageForm.characterSetId!!, isOriginal)
        if (!isOriginal && charachips.list.size != villageForm.characterSetId!!.size) {
            throw WolfMansionBadRequestException("charachip not found.")
        }
        villageCoordinator.assertCreateVillage(player, villageForm.personMaxNum!!, charachips, isOriginal)
        val village = villageCoordinator.registerVillage(
            villageForm.toVillage(player),
            villageForm.dummyCharaName!!,
            villageForm.dummyCharaShortName!!,
            villageForm.dummyCharaImageFile,
            villageForm.dummyJoinMessage!!,
            villageForm.dummyDay1Message
        )
        return NewVillageResponse(village.id)
    }

    data class NewVillageResponse(val villageId: Int)

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
            query = VillageQuery(
                statuses = VillageStatus.notProgressStatusLsit.map { VillageStatus(it) }
            )
        ).list.map { NewVillageDivertContent(it.id, it.id.toString().padStart(4, '0'), it.name) }
        model.addAttribute("villageList", finishedVillages)
        model.addAttribute("skillListStr", Skills.getSkillListStr())
        model.addAttribute("nowYear", LocalDateTime.now().year)
        val charachips = charaService.findCharachips().list.map {
            OptionContent(name = "${it.name}（${it.designer!!.name}様作）", value = it.id.toString())
        }
        model.addAttribute("characterSetList", charachips)
    }
}