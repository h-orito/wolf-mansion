package com.ort.app.api

import com.ort.app.api.request.VillageGetAnchorMessageForm
import com.ort.app.api.request.VillageGetMessageListForm
import com.ort.app.api.view.VillageAnchorMessageContent
import com.ort.app.api.view.VillageLatestMessageDatetimeContent
import com.ort.app.api.view.VillageMessageListContent
import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.CommitService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageApplicationService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.vote.Votes
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.time.format.DateTimeFormatter

@Controller
class VillageMessageController(
    private val villageService: VillageApplicationService,
    private val charaService: CharaService,
    private val playerService: PlayerService,
    private val voteService: VoteApplicationService,
    private val commitService: CommitService,
    private val messageService: MessageService,
    private val daychangeCoordinator: DaychangeCoordinator
) {
    // 発言取得
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private fun getDayMessageList(
        @Validated form: VillageGetMessageListForm,
        result: BindingResult
    ): VillageMessageListContent {
        if (result.hasErrors()) throw WolfMansionBusinessException("bad request.")
        val village = villageService.findVillage(form.villageId!!, excludeGone = false)
            ?: throw WolfMansionBusinessException("village not found.")
        // 最終アクセス日時を更新
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }?.also { villageService.updateLastAccessDatetime(it) }
        // 更新時間が過ぎていたら日付更新
        daychangeCoordinator.changeDayIfNeeded(village)
        // 発言取得
        val query = form.toMessageQuery(village)
        val messages = messageService.findMeesages(village, myself, query)
        val charas = charaService.findCharas(village.setting.charachipId)
        val players = playerService.findPlayers(village.id)
        val votes = if (VillageMessageListContent.isDispSuddenlyDeathWarnMessage(village, query.day)) {
            voteService.findVotes(village.id).filterByDay(village.latestDay())
        } else Votes(emptyList())
        val commits = if (VillageMessageListContent.isDispCommitMessage(village, query.day)) {
            commitService.findCommits(village.id).filterByDay(village.latestDay())
        } else Commits(emptyList())
        return VillageMessageListContent(messages, village, myself, charas, players, votes, commits, query.day)
    }

    // 最終発言時間取得
    @GetMapping("/village/getLatestMessageDatetime")
    @ResponseBody
    private fun getLatestMessageDatetime(
        @Validated form: VillageGetMessageListForm,
        result: BindingResult
    ): VillageLatestMessageDatetimeContent {
        if (result.hasErrors()) throw WolfMansionBusinessException("bad request.")
        val village = villageService.findVillage(form.villageId!!, excludeGone = false)
            ?: throw WolfMansionBusinessException("village not found.")
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        val datetime = messageService.findLatestMessageDatetime(village, myself, form.toMessageQuery(village))
        return VillageLatestMessageDatetimeContent(
            datetime?.format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss")) ?: "0"
        )
    }

    // アンカー発言取得
    @GetMapping("/village/getAnchorMessage")
    @ResponseBody
    private fun getAnchorMessage(
        @Validated form: VillageGetAnchorMessageForm,
        result: BindingResult
    ): VillageAnchorMessageContent {
        if (result.hasErrors()) throw WolfMansionBusinessException("bad request.")
        val village = villageService.findVillage(form.villageId!!, excludeGone = false)
            ?: throw WolfMansionBusinessException("village not found.")
        // 最終アクセス日時を更新
        val myself = WolfMansionUserInfoUtil.getUserInfo()?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        // 発言取得
        val message = messageService.findMessage(village, myself, form.messageType!!, form.messageNumber!!)
        val player = message?.fromParticipantId?.let {
            playerService.findPlayer(village.allParticipants().member(it).playerId)
        }
        val charas = charaService.findCharas(village.setting.charachipId)
        return VillageAnchorMessageContent(message, village, player, charas)
    }
}