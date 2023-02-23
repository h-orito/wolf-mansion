package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageNotificationForm
import com.ort.app.application.service.*
import com.ort.app.domain.model.village.participant.VillageParticipantNotificationCondition
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class VillageNotificationController(
    private val villageControllerHelper: VillageControllerHelper,
    // service
    private val villageService: VillageService,
    private val notificationService: NotificationService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    // 設定保存
    @PostMapping("/village/{villageId}/notification-setting")
    private fun saveNotification(
        @PathVariable villageId: Int,  //
        @Validated @ModelAttribute("notificationForm") notificationForm: VillageNotificationForm,  //
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
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("myself not found.")

        villageService.registerNotification(
            myself.copy(
                notification = VillageParticipantNotificationCondition(
                    discordWebhookUrl = notificationForm.webhookUrl!!,
                    village = VillageParticipantNotificationCondition.VillageCondition(
                        start = notificationForm.villageStart ?: false,
                        epilogue = notificationForm.villageEpilogue ?: false
                    ),
                    message = VillageParticipantNotificationCondition.MessageCondition(
                        secretSay = notificationForm.secretSay ?: false,
                        abilitySay = notificationForm.abilitySay ?: false,
                        anchor = false
                    )
                )
            )
        )
        notificationService.notifyTest(notificationForm.webhookUrl, village.id)
        // 最新の日付を表示
        return "redirect:/village/$villageId#bottom"
    }
}