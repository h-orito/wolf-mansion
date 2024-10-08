package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageListForm
import com.ort.app.api.request.VillageMessageForm
import com.ort.app.api.view.VillageListContent
import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.skill.toModel
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class VillageController(
    private val villageControllerHelper: VillageControllerHelper,
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val daychangeCoordinator: DaychangeCoordinator,
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
        if (village.setting.tags.list.any { it.toCdef() == CDef.VillageTagItem.R18 }) {
            model.addAttribute("noAd", true)
        }
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
        if (village.setting.tags.list.any { it.toCdef() == CDef.VillageTagItem.R18 }) {
            model.addAttribute("noAd", true)
        }
        return "village"
    }

    // 村メッセージ表示
    @GetMapping("/village/{villageId}/message")
    private fun villageMessage(
        @PathVariable villageId: Int,
        @ModelAttribute("form") form: VillageMessageForm,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        // いったん表示して後からAPIでメッセージ取得しにくるので、そのまま渡す
        villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
        if (village.setting.tags.list.any { it.toCdef() == CDef.VillageTagItem.R18 }) {
            model.addAttribute("noAd", true)
        }
        return "village-message"
    }

    // 村切り抜き画面
    @GetMapping("/village/{villageId}/scrap")
    private fun scrap(
        @PathVariable villageId: Int,
        @ModelAttribute("form") form: VillageMessageForm,
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
//        if (!village.status.isSettled()) {
//            throw WolfMansionBusinessException("village not settled.")
//        }
        // いったん表示して後からAPIでメッセージ取得しにくるので、そのまま渡す
        villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
        if (village.setting.tags.list.any { it.toCdef() == CDef.VillageTagItem.R18 }) {
            model.addAttribute("noAd", true)
        }
        return "scrap"
    }

    // 最終アクセス時間更新、日付更新
    @PostMapping("/village/{villageId}/update")
    @ResponseBody
    private fun villageUpdate(
        @PathVariable villageId: Int
    ): VillageUpdateResponse {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionBusinessException("village not found.")
        // 最終アクセス日時を更新
        val user = WolfMansionUserInfoUtil.getUserInfo()
        val myself = user?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }?.also { villageService.updateLastAccessDatetime(it) }
        // 更新時間が過ぎていたら日付更新
        daychangeCoordinator.changeDayIfNeeded(village)

        return VillageUpdateResponse(
            login = myself != null,
            latestDay = village.latestDay()
        )
    }

    data class VillageUpdateResponse(
        val login: Boolean,
        val latestDay: Int,
    )
}