package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageAbilityForm
import com.ort.app.api.request.VillageCommitForm
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageGetAttackTargetListForm
import com.ort.app.api.request.VillageGetFootstepListForm
import com.ort.app.api.request.VillageVoteForm
import com.ort.app.api.view.VillageGetAttackTargetListContent
import com.ort.app.api.view.VillageGetFootstepListContent
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.VillageService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.interceptor.getRefererQueryString
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@Controller
class VillageAbilityController(
    private val villageControllerHelper: VillageControllerHelper,
    private val villageCoordinator: VillageCoordinator,
    private val villageService: VillageService
) {

    // 能力セットする
    @PostMapping("/village/{villageId}/setAbility")
    private fun setAbility(
        @PathVariable villageId: Int,  //
        @Validated @ModelAttribute("abilityForm") abilityForm: VillageAbilityForm,  //
        request: HttpServletRequest,  //
        result: BindingResult,  //
        model: Model
    ): String {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        if (result.hasErrors()) {
            villageControllerHelper.setIndexModel(village, village.latestDay(), model, VillageForms())
            return "village"
        }
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()
                ?.let { villageService.findVillageParticipant(village.id, it.username) }
        try {
            villageCoordinator.setAbility(
                village,
                myself,
                abilityForm.attackerCharaId,
                abilityForm.targetCharaId,
                abilityForm.footstep
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("abilityErrorMessage", e.message)
        }
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    // 投票セットする
    @PostMapping("/village/{villageId}/setVote")
    private fun setVote(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("voteForm") voteForm: VillageVoteForm,
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
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()
                ?.let { villageService.findVillageParticipant(village.id, it.username) }
        try {
            villageCoordinator.setVote(
                village,
                myself,
                voteForm.targetCharaId!!
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("voteErrorMessage", e.message)
        }
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    // コミットする
    @PostMapping("/village/{villageId}/commit")
    private fun setCommit(
        @PathVariable villageId: Int,
        @Validated @ModelAttribute("commitForm") commitForm: VillageCommitForm,
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
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()
                ?.let { villageService.findVillageParticipant(village.id, it.username) }
        try {
            villageCoordinator.setCommit(
                village,
                myself,
                commitForm.commit!!
            )
        } catch (e: WolfMansionBusinessException) {
            model.addAttribute("commitErrorMessage", e.message)
        }
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    // 襲撃対象候補取得
    @GetMapping("/village/getAttackTargetList")
    @ResponseBody
    private fun getAttackTargetList(
        @Validated form: VillageGetAttackTargetListForm,
        result: BindingResult
    ): VillageGetAttackTargetListContent {
        if (result.hasErrors()) throw WolfMansionBusinessException("invalid")
        val village = villageService.findVillage(form.villageId!!)
            ?: throw WolfMansionBusinessException("village not found.")
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()
                ?.let { villageService.findVillageParticipant(village.id, it.username) }
        val targets = villageCoordinator.getAttackableTargets(village, myself, form.charaId!!)
        return VillageGetAttackTargetListContent(targets)
    }

    // 足音候補取得
    @GetMapping("/village/getFootstepList")
    @ResponseBody
    private fun getFootstepList(
        @Validated form: VillageGetFootstepListForm,
        result: BindingResult
    ): VillageGetFootstepListContent {
        if (result.hasErrors()) throw WolfMansionBusinessException("invalid")
        val village = villageService.findVillage(form.villageId!!)
            ?: throw WolfMansionBusinessException("village not found.")
        val myself =
            WolfMansionUserInfoUtil.getUserInfo()
                ?.let { villageService.findVillageParticipant(village.id, it.username) }
        val footsteps =
            villageCoordinator.getSelectableFootstepList(village, myself, form.charaId, form.targetCharaId)
        return VillageGetFootstepListContent(footsteps)
    }
}