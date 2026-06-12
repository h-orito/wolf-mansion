package com.ort.app.api.village

import com.ort.app.api.VillageMessageController
import com.ort.app.api.view.VillageAnchorMessageContent
import com.ort.app.api.view.VillageAnchorMessagesContent
import com.ort.app.api.view.VillageLatestMessageDatetimeContent
import com.ort.app.api.view.VillageMessageListContent
import com.ort.app.api.view.VillageParticipantsContent
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
import com.ort.app.fw.exception.WolfMansionBusinessException
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

/**
 * 村のメッセージ REST。可視範囲はサーバ側で決定する (囁きは人狼のみ、墓下は死者のみ等)。
 * 認証不要 (匿名は公開発言のみ) だが、ログインしていれば視点を可視判定に反映する。
 * レスポンスはテンプレート向けに整形済みの既存 View をそのまま使う (マスク済みの正本)。
 */
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
    /** 日別の発言一覧 (ページング + アナウンス素材付き)。 */
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

    /** 最新発言日時 (新着検知用)。可視範囲・絞り込みを反映する。 */
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

    /** 単一アンカー発言。閲覧できない発言は message が null。 */
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

    /** 複数アンカー発言 (`anchors=n123_w45` 形式)。通知のパーマリンクページが使う。 */
    @Operation(operationId = "getVillageAnchorMessages")
    @GetMapping("/messages/anchors")
    fun anchorMessages(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable id: Int,
        @RequestParam anchors: String,
    ): VillageAnchorMessagesContent {
        val village = findVillageOrThrow(id)
        val (_, myself, myselfPlayer) = resolveViewer(principal, village)
        val parsed = VillageMessageController.Anchors.of(anchors)
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

    /** 参加者の正体一覧。エピローグ以降 (settled) のみ公開する。 */
    @Operation(operationId = "getVillageParticipants")
    @GetMapping("/participants")
    fun participants(
        @PathVariable id: Int,
    ): VillageParticipantsContent {
        val village = findVillageOrThrow(id)
        if (!village.status.isSettled()) {
            throw WolfMansionBusinessException("エピローグ以降の村のみ参照できます")
        }
        val players = playerService.findPlayers(id)
        return VillageParticipantsContent(village, players)
    }

    private fun findVillageOrThrow(id: Int): Village =
        villageService.findVillage(id, excludeGone = false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "village not found")

    /**
     * ビューア情報。[VillageMessageListContent] が SSR の [UserInfo] を要求するため、
     * JWT principal から最小の互換オブジェクトを作る (ログイン有無の判定にしか使われない)。
     */
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
}
