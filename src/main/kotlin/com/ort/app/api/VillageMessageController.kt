package com.ort.app.api

import com.ort.app.api.request.VillageGetAnchorMessageForm
import com.ort.app.api.request.VillageGetMessageListForm
import com.ort.app.api.view.VillageAnchorMessageContent
import com.ort.app.api.view.VillageLatestMessageDatetimeContent
import com.ort.app.api.view.VillageMessageListContent
import com.ort.app.api.view.VillageParticipantsContent
import com.ort.app.application.coordinator.DaychangeCoordinator
import com.ort.app.application.service.*
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.vote.Votes
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import java.time.format.DateTimeFormatter

@Controller
class VillageMessageController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val playerService: PlayerService,
    private val voteService: VoteApplicationService,
    private val commitService: CommitService,
    private val messageService: MessageService,
    private val abilityService: AbilityService,
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
        val user = WolfMansionUserInfoUtil.getUserInfo()
        val myself = user?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }?.also { villageService.updateLastAccessDatetime(it) }
        val myselfPlayer = user?.let { playerService.findPlayer(user.username) }
        // 更新時間が過ぎていたら日付更新
        daychangeCoordinator.changeDayIfNeeded(village)
        // 発言取得
        val query = form.toMessageQuery(village)
        val messages = messageService.findMeesages(village, myself, myselfPlayer, query)
        val charas =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas() }
        val players = playerService.findPlayers(village.id)
        val votes = if (VillageMessageListContent.isDispSuddenlyDeathWarnMessage(village, query.day)) {
            voteService.findVotes(village.id).filterByDay(village.latestDay())
        } else Votes(emptyList())
        val commits = if (VillageMessageListContent.isDispCommitMessage(village, query.day)) {
            commitService.findCommits(village.id).filterByDay(village.latestDay())
        } else Commits(emptyList())
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
            query.day
        )
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
        val user = WolfMansionUserInfoUtil.getUserInfo()
        val myself = user?.let {
            villageService.findVillageParticipant(village.id, it.username)
        }
        val player = user?.let { playerService.findPlayer(it.username) }
        val datetime = messageService.findLatestMessageDatetime(village, myself, player, form.toMessageQuery(village))
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
        val charas =
            village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas() }
        val abilities = abilityService.findAbilities(village.id)
        return VillageAnchorMessageContent(message, village, player, charas, abilities)
    }

    // 参加者一覧取得
    @GetMapping("/village/{villageId}/getParticipants")
    @ResponseBody
    private fun getParticipants(
        @PathVariable villageId: Int
    ): VillageParticipantsContent {
        val village = villageService.findVillage(villageId) ?: throw WolfMansionBusinessException("village not found.")
        if (!village.status.isSettled()) throw WolfMansionBusinessException("invalid village status.")
        val players = playerService.findPlayers(villageId)
        return VillageParticipantsContent(village, players)
    }
}