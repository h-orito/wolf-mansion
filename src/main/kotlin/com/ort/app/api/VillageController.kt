package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageListForm
import com.ort.app.api.view.VillageListContent
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable

@Controller
class VillageController(
    private val villageControllerHelper: VillageControllerHelper,
    private val villageService: VillageService,
    private val charaService: CharaService
) {
    // 村一覧初期表示
    @GetMapping("/village-list")
    private fun villageListIndex(
        @ModelAttribute("form") form: VillageListForm,
        model: Model
    ): String {
        val villages = villageService.findVillages(
            query = VillageQuery(
                charachipIds = form.charachipIds ?: emptyList(),
                skills = form.skillCodes?.let { skillCodes ->
                    skillCodes.map { code -> CDef.Skill.codeOf(code).toModel() }
                } ?: emptyList(),
                isRandomOrg = form.random
            )
        )
        val charachips = charaService.findCharachips()
        val skills = Skills.all().filterNotSomeone()
        model.addAttribute("content", VillageListContent(villages, charachips, skills))
        return "village-list"
    }

    // 村最新日付初期表示
    @GetMapping("/village/{villageId}")
    private fun villageIndex(
        @PathVariable villageId: Int,
        model: Model
    ): String {
        // 最新の日付を表示
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
        return "village"
    }

    // 村日付初期表示
    @GetMapping("/village/{villageId}/day/{day}")
    private fun villageDayIndex(
        @PathVariable villageId: Int,
        @PathVariable day: Int,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        villageControllerHelper.setIndexModel(village, day, model, VillageForms())
        return "village"
    }
}