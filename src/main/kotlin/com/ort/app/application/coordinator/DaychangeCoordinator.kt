package com.ort.app.application.coordinator

import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.CommitService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.TweetService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.daychange.DaychangeDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DaychangeCoordinator(
    private val villageService: VillageService,
    private val voteService: VoteApplicationService,
    private val abilityService: AbilityService,
    private val commitService: CommitService,
    private val playerService: PlayerService,
    private val footstepService: FootstepApplicationService,
    private val daychangeDomainService: DaychangeDomainService,
    private val charaService: CharaService,
    private val messageService: MessageService,
    private val tweetService: TweetService
) {

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun changeDayIfNeeded(village: Village) {
        val votes = voteService.findVotes(village.id)
        val abilities = abilityService.findAbilities(village.id)
        val commits = commitService.findCommits(village.id)
        val players = playerService.findPlayers(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        val charas = charaService.findCharas(village.setting.charachipId)

        var daychange = Daychange(village, abilities, votes, footsteps, players)
        daychange = daychangeDomainService.leaveParticipantIfNeeded(daychange).let {
            updateIfNeeded(daychange, it)
            it.copy(
                village = it.village.copy(
                    participants = it.village.participants.filterNotGone(),
                    spectators = it.village.spectators.filterNotGone()
                )
            )
        }
        daychange = daychangeDomainService.cancelOrExtendPrologueIfNeeded(daychange).let {
            updateIfNeeded(daychange, it)
            it
        }
        if (daychange.village.status.isCanceled()) return
        daychangeDomainService.changeDayIfNeeded(daychange, commits, charas).also {
            updateIfNeeded(daychange, it)
        }
    }

    private fun updateIfNeeded(current: Daychange, changed: Daychange) {
        if (changed.isSame(current)) return
        update(current, changed)
    }

    private fun update(current: Daychange, changed: Daychange) {
        villageService.updateDaychangeDifference(current.village, changed.village)
        voteService.updateDaychangeDifference(changed.village.id, current.votes, changed.votes)
        abilityService.updateDaychangeDifference(changed.village, current.abilities, changed.abilities)
        playerService.updateDaychangeDifference(current.players, changed.players)
        footstepService.updateDaychangeDifference(changed.village.id, current.footsteps, changed.footsteps)
        messageService.updateDaychangeDifference(changed.village, current.messages, changed.messages)
        tweetService.tweetDaychange(changed.village.id, changed.tweets)
    }
}