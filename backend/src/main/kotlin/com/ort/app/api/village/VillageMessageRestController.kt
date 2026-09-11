package com.ort.app.api.village

import com.ort.app.api.view.VillageAnchorMessageContent
import com.ort.app.api.view.VillageAnchorMessagesContent
import com.ort.app.api.view.VillageLatestMessageDatetimeContent
import com.ort.app.api.view.VillageMessageListContent
import com.ort.app.api.village.request.VillageMessageSearchRequest
import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.CommitService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.security.UserInfo
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

@RestController
@RequestMapping("/api/v1/villages/{id}")
class VillageMessageRestController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val playerService: PlayerService,
    private val voteService: VoteApplicationService,
    private val commitService: CommitService,
    private val messageService: MessageService,
    private val abilityService: AbilityService,
) {
    @Operation(operationId = "getVillageMessages")
    @GetMapping("/messages")
    fun messages(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @ParameterObject request: VillageMessageSearchRequest,
    ): VillageMessageListContent {
        val village = findVillageOrThrow(id)
        val (user, myself, myselfPlayer) = resolveViewer(principal, village)
        val query = request.toQuery(village)
        val messages = messageService.findMeesages(village, myself, myselfPlayer, query)
        val charas =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas() }
        val players = playerService.findPlayers(village.id)
        val votes =
            if (VillageMessageListContent.isDispSuddenlyDeathWarnMessage(village, query.day)) {
                voteService.findVotes(village.id).filterByDay(village.latestDay())
            } else {
                Votes(emptyList())
            }
        val commits =
            if (VillageMessageListContent.isDispCommitMessage(village, query.day)) {
                commitService.findCommits(village.id).filterByDay(village.latestDay())
            } else {
                Commits(emptyList())
            }
        val abilities = abilityService.findAbilities(village.id).filterByDay(query.day - 1)
        return VillageMessageListContent(
            messages,
            village,
            user,
            myself,
            myselfPlayer,
            charas,
            players,
            votes,
            commits,
            abilities,
            query.day,
        )
    }

    @Operation(operationId = "getVillageLatestMessageDatetime")
    @GetMapping("/messages/latest-datetime")
    fun latestMessageDatetime(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @ParameterObject request: VillageMessageSearchRequest,
    ): VillageLatestMessageDatetimeContent {
        val village = findVillageOrThrow(id)
        val (_, myself, myselfPlayer) = resolveViewer(principal, village)
        val datetime =
            messageService.findLatestMessageDatetime(village, myself, myselfPlayer, request.toQuery(village))
        return VillageLatestMessageDatetimeContent(
            datetime?.format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss")) ?: "0",
        )
    }

    @Operation(operationId = "getVillageAnchorMessage")
    @GetMapping("/messages/anchor")
    fun anchorMessage(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam messageType: String,
        @RequestParam messageNumber: Int,
    ): VillageAnchorMessageContent {
        val village = findVillageOrThrow(id)
        val (_, myself, myselfPlayer) = resolveViewer(principal, village)
        val message = messageService.findMessage(village, myself, myselfPlayer, messageType, messageNumber)
        val fromPlayer =
            message?.fromParticipantId?.let {
                playerService.findPlayer(village.allParticipants().member(it).playerId)
            }
        val charas =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas() }
        val abilities = abilityService.findAbilities(village.id)
        return VillageAnchorMessageContent(message, village, fromPlayer, charas, abilities)
    }

    @Operation(operationId = "getVillageAnchorMessages")
    @GetMapping("/messages/anchors")
    fun anchorMessages(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam anchors: String,
    ): VillageAnchorMessagesContent {
        val village = findVillageOrThrow(id)
        val (_, myself, myselfPlayer) = resolveViewer(principal, village)
        val parsed = Anchors.of(anchors)
        val messages =
            parsed.list.mapNotNull {
                messageService.findMessage(village, myself, myselfPlayer, it.messageType, it.messageNumber)
            }
        val players = playerService.findPlayers(village.id)
        val charas =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas() }
        val abilities = abilityService.findAbilities(village.id)
        return VillageAnchorMessagesContent(messages, village, players, charas, abilities)
    }

    private fun findVillageOrThrow(id: Int): Village =
        villageService.findVillage(id, excludeGone = false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")

    private fun resolveViewer(
        principal: JwtPrincipal?,
        village: Village,
    ): Triple<UserInfo?, VillageParticipant?, Player?> {
        principal ?: return Triple(null, null, null)
        val myself = villageService.findVillageParticipant(village.id, principal.name)
        val player = playerService.findPlayer(principal.name)
        val user = UserInfo().apply { setUsername(principal.name) }
        return Triple(user, myself, player)
    }

    data class Anchors(
        val list: List<Anchor>,
    ) {
        data class Anchor(
            val messageType: String,
            val messageNumber: Int,
        )

        companion object {
            fun of(anchorStr: String): Anchors {
                val anchors =
                    anchorStr.split("_").mapNotNull {
                        if (it.isBlank()) return@mapNotNull null
                        val matcher = Pattern.compile("([nwmflgsMSca])(\\d{1,5})").matcher(it)
                        if (!matcher.find()) return@mapNotNull null
                        val type =
                            MessageDomainService.convertMessageUrlTypeToMessageType(matcher.group(1))?.code
                                ?: return@mapNotNull null
                        val number = matcher.group(2).toInt()
                        Anchor(type, number)
                    }
                return Anchors(anchors)
            }
        }
    }
}
