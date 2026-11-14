package com.ort.app.application.coordinator

import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.CommitService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.NotificationService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.toModel
import com.ort.app.domain.model.vote.Votes
import com.ort.app.domain.service.daychange.DaychangeDomainService
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions

/**
 * 村ポーリング (/update) は終了・廃村の村でも呼ばれるため、日付更新の判定に入る前に
 * 投票・能力などを読まずに戻ることを確認する。
 */
internal class DaychangeCoordinatorFinishedVillageTest {
    private val villageService: VillageService = mock(VillageService::class.java)
    private val voteService: VoteApplicationService = mock(VoteApplicationService::class.java)
    private val abilityService: AbilityService = mock(AbilityService::class.java)
    private val commitService: CommitService = mock(CommitService::class.java)
    private val playerService: PlayerService = mock(PlayerService::class.java)
    private val footstepService: FootstepApplicationService = mock(FootstepApplicationService::class.java)

    // 受け取った Daychange をそのまま返す (日付更新が起きない) ドメインサービス
    private val daychangeDomainService: DaychangeDomainService =
        mock(DaychangeDomainService::class.java) { it.arguments.firstOrNull() }
    private val charaService: CharaService = mock(CharaService::class.java)
    private val messageService: MessageService = mock(MessageService::class.java)
    private val notificationService: NotificationService = mock(NotificationService::class.java)

    private val coordinator =
        DaychangeCoordinator(
            villageService,
            voteService,
            abilityService,
            commitService,
            playerService,
            footstepService,
            daychangeDomainService,
            charaService,
            messageService,
            notificationService,
        )

    private fun village(status: CDef.VillageStatus): Village = createDay1Village().copy(status = status.toModel())

    @Test
    @DisplayName("終了・廃村の村は、投票・能力・コミット・プレイヤー・足音を読まずに戻る")
    fun finishedVillageReturnsEarly() {
        listOf(CDef.VillageStatus.終了, CDef.VillageStatus.廃村).forEach {
            coordinator.changeDayIfNeeded(village(it))
        }
        verifyNoInteractions(
            villageService,
            voteService,
            abilityService,
            commitService,
            playerService,
            footstepService,
            daychangeDomainService,
            charaService,
            messageService,
            notificationService,
        )
    }

    @Test
    @DisplayName("エピローグの村は終了への日付更新があるため、従来どおり判定に進む")
    fun epilogueVillageStillChecksDaychange() {
        stubLoads()
        val epilogue = village(CDef.VillageStatus.エピローグ)

        coordinator.changeDayIfNeeded(epilogue)

        verify(voteService).findVotes(epilogue.id)
        verify(abilityService).findAbilities(epilogue.id)
        // 日付更新が起きていないので更新処理には進まない
        verify(villageService, never()).updateDaychangeDifference(epilogue, epilogue)
    }

    @Test
    @DisplayName("進行中の村は従来どおり判定に進む")
    fun progressVillageStillChecksDaychange() {
        stubLoads()
        val progress = village(CDef.VillageStatus.進行中)

        coordinator.changeDayIfNeeded(progress)

        verify(voteService).findVotes(progress.id)
        verify(commitService).findCommits(progress.id)
    }

    private fun stubLoads() {
        doReturn(Votes(emptyList())).`when`(voteService).findVotes(anyInt())
        doReturn(Abilities(emptyList())).`when`(abilityService).findAbilities(anyInt())
        doReturn(Commits(emptyList())).`when`(commitService).findCommits(anyInt())
        doReturn(Players(emptyList())).`when`(playerService).findPlayers(anyInt())
        doReturn(Footsteps(emptyList())).`when`(footstepService).findFootsteps(anyInt())
    }
}
