package com.ort.app.api

import com.ort.app.api.helper.VillageControllerHelper
import com.ort.app.api.request.VillageForms
import com.ort.app.api.request.VillageNotificationForm
import com.ort.app.application.service.NotificationService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.participant.VillageParticipantNotificationCondition
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.interceptor.getRefererQueryString
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException

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
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("myself not found.")

        villageService.registerNotification(
            myself.copy(
                notification = VillageParticipantNotificationCondition(
                    discordWebhookUrl = notificationForm.webhookUrl!!,
                    village = VillageParticipantNotificationCondition.VillageCondition(
                        start = notificationForm.villageStart ?: false,
                        dayChange = notificationForm.villageDaychange ?: false,
                        epilogue = notificationForm.villageEpilogue ?: false
                    ),
                    message = VillageParticipantNotificationCondition.MessageCondition(
                        secretSay = notificationForm.secretSay ?: false,
                        abilitySay = notificationForm.abilitySay ?: false,
                        anchor = notificationForm.anchorSay ?: false,
                        keywords = notificationForm.keyword?.trim()?.replace("　", " ")?.split(" ") ?: emptyList()
                    )
                )
            )
        )
        notificationService.notifyTest(notificationForm.webhookUrl, village.id)
        // 最新の日付を表示
        return "redirect:/village/$villageId${request.getRefererQueryString()}#bottom"
    }

    @PostMapping("/api/village/{villageId}/notification-setting")
    @ResponseBody
    private fun apiSaveNotification(
        @PathVariable villageId: Int,  //
        @Validated notificationForm: VillageNotificationForm,  //
    ) {
        val village = villageService.findVillage(villageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found. id: $villageId")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        } ?: throw WolfMansionBusinessException("myself not found.")

        villageService.registerNotification(
            myself.copy(
                notification = VillageParticipantNotificationCondition(
                    discordWebhookUrl = notificationForm.webhookUrl!!,
                    village = VillageParticipantNotificationCondition.VillageCondition(
                        start = notificationForm.villageStart ?: false,
                        dayChange = notificationForm.villageDaychange ?: false,
                        epilogue = notificationForm.villageEpilogue ?: false
                    ),
                    message = VillageParticipantNotificationCondition.MessageCondition(
                        secretSay = notificationForm.secretSay ?: false,
                        abilitySay = notificationForm.abilitySay ?: false,
                        anchor = notificationForm.anchorSay ?: false,
                        keywords = notificationForm.keyword?.trim()?.replace("　", " ")?.split(" ") ?: emptyList()
                    )
                )
            )
        )
        notificationService.notifyTest(notificationForm.webhookUrl, village.id)
    }
}