package com.ort.app.application.coordinator

import com.ort.app.application.service.*
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
    private val notificationService: NotificationService
) {

    @Transactional(rollbackFor = [Exception::class, WolfMansionBusinessException::class])
    fun changeDayIfNeeded(village: Village) {
        val votes = voteService.findVotes(village.id)
        val abilities = abilityService.findAbilities(village.id)
        val commits = commitService.findCommits(village.id)
        val players = playerService.findPlayers(village.id)
        val footsteps = footstepService.findFootsteps(village.id)
        val charas = village.setting.chara.let { charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas() }

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
            notifyIfNeeded(daychange, it)
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
        footstepService.updateDaychangeDifference(changed.village, current.footsteps, changed.footsteps)
        messageService.updateDaychangeDifference(changed.village, current.messages, changed.messages)
        notificationService.notifyDaychange(changed.village.id, changed.tweets)
    }

    private fun notifyIfNeeded(current: Daychange, changed: Daychange) {
        if (current.village.status.isPrologue() && changed.village.status.isProgress()) {
            notificationService.notifyVillageStartToCustomerIfNeeded(changed.village)
        } else if (current.village.status.isProgress() && changed.village.status.isEpilogue()) {
            notificationService.notifyVillageEpilogueToCustomerIfNeeded(changed.village)
        }
    }
}