package com.ort.app.api.village

import com.ort.app.api.village.request.VillageNotificationRequest
import com.ort.app.application.service.NotificationService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.participant.VillageParticipantNotificationCondition
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/** Discord 通知設定の REST。保存後に webhook へテスト通知を送る (失敗しても保存は成立)。 */
@RestController
@RequestMapping("/api/v1/villages/{id}/notification-setting")
class VillageNotificationRestController(
    private val villageService: VillageService,
    private val notificationService: NotificationService,
) {
    /** 通知設定を保存する。 */
    @Operation(operationId = "saveVillageNotificationSetting")
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun save(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestBody @Valid request: VillageNotificationRequest,
    ) {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        val village =
            villageService.findVillage(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")
        val myself =
            villageService.findVillageParticipant(village.id, principal.name)
                ?: throw WolfMansionBusinessException("村に参加していません")
        val webhookUrl = request.webhookUrl!!

        villageService.registerNotification(
            myself.copy(
                notification =
                    VillageParticipantNotificationCondition(
                        discordWebhookUrl = webhookUrl,
                        village =
                            VillageParticipantNotificationCondition.VillageCondition(
                                start = request.villageStart ?: false,
                                dayChange = request.villageDaychange ?: false,
                                epilogue = request.villageEpilogue ?: false,
                            ),
                        message =
                            VillageParticipantNotificationCondition.MessageCondition(
                                secretSay = request.secretSay ?: false,
                                abilitySay = request.abilitySay ?: false,
                                anchor = request.anchorSay ?: false,
                                keywords =
                                    request.keyword
                                        ?.trim()
                                        ?.replace("　", " ")
                                        ?.split(" ")
                                        ?.filter { it.isNotEmpty() }
                                        ?: emptyList(),
                            ),
                    ),
            ),
        )
        notificationService.notifyTest(webhookUrl, village.id)
    }
}
