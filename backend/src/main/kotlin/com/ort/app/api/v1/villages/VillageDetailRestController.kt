package com.ort.app.api.v1.villages

import com.ort.app.api.response.message.MessagesView
import com.ort.app.api.response.myself.MyselfView
import com.ort.app.api.response.village.VillageFootstepView
import com.ort.app.api.response.village.VillageFootstepsView
import com.ort.app.api.response.village.VillageParticipantView
import com.ort.app.api.response.village.VillageParticipantsView
import com.ort.app.api.response.village.VillageView
import com.ort.app.application.coordinator.CreatorCoordinator
import com.ort.app.application.coordinator.VillageCoordinator
import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.SpoilerDomainService
import com.ort.app.domain.service.footstep.FootstepRevealDomainService
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageDetailRestController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val playerService: PlayerService,
    private val messageService: MessageService,
    private val footstepService: FootstepApplicationService,
    private val abilityService: AbilityService,
    private val voteService: VoteApplicationService,
    private val villageCoordinator: VillageCoordinator,
    private val creatorCoordinator: CreatorCoordinator,
    private val spoilerDomainService: SpoilerDomainService,
    private val footstepRevealDomainService: FootstepRevealDomainService,
) {

    @GetMapping("/{villageId}")
    @Operation(
        summary = "村詳細取得",
        description = "村ヘッダ + 参加者一覧 (隠蔽済み) + 日付一覧。`/messages` `/footsteps` `/myself` と組み合わせて使う。",
    )
    fun get(
        @PathVariable villageId: Int,
    ): VillageView {
        val ctx = loadContext(villageId)
        val participants = buildParticipants(ctx)
        // 旧 Thymeleaf 系 (`CreatorController`) と新 REST creator endpoints の認可判定
        // (`VillageContextLoader.loadVillageAndRequireCreator`) はどちらも
        // `CreatorCoordinator.isCreator` を使う = Player ID=1 (管理者) は全村 creator 扱い。
        // 表示用 `isCreator` を同じ判定で揃え、UI と API 認可の見え方を一致させる。
        val isCreator = ctx.user?.let { creatorCoordinator.isCreator(it.username, ctx.village.id) } ?: false
        return VillageView(
            village = ctx.village,
            participants = participants,
            isCreator = isCreator,
            isParticipating = ctx.myself != null,
        )
    }

    @GetMapping("/{villageId}/messages")
    @Operation(
        summary = "発言一覧取得",
        description = "指定された日の閲覧可能な発言を返す。閲覧権限は閲覧者の参加状況 + 役職 + 村ステータスで決まる。",
    )
    fun messages(
        @PathVariable villageId: Int,
        @Parameter(description = "何日目か。未指定なら最新日。", required = false)
        @RequestParam(required = false) day: Int?,
    ): MessagesView {
        val ctx = loadContext(villageId)
        val targetDay = day ?: ctx.village.latestDay()
        val query = MessageQuery(
            village = ctx.village,
            day = targetDay,
            pageSize = null,
            pageNum = null,
            fromParticipantIds = emptyList(),
            toParticipantIds = emptyList(),
            requestTypes = emptyList(),
            keywords = null,
            isPaging = false,
            isDispLatest = true,
        )
        val messages = messageService.findMeesages(ctx.village, ctx.myself, ctx.player, query)
        return MessagesView(messages)
    }

    @GetMapping("/{villageId}/footsteps")
    @Operation(
        summary = "足音一覧取得",
        description = "進行中は registerChara / chara を隠し roomNumbers のみ公開 (墓下開示村で dead / 見学の viewer には例外的に全公開)。エピローグ / 終了では全公開、募集中 / 廃村は空リスト。",
    )
    fun footsteps(
        @PathVariable villageId: Int,
    ): VillageFootstepsView {
        val ctx = loadContext(villageId)
        // 進行 (進行中) と決着 (エピローグ / 終了) 以外は足音そのものが存在し得ないので空で返す。
        // 廃村はプロローグ中にキャンセルされた村なので登録足音なし。
        if (!ctx.village.status.isProgress() && !ctx.village.status.isSettled()) {
            return VillageFootstepsView(list = emptyList())
        }
        val footsteps = footstepService.findFootsteps(villageId)
        val charaById: Map<Int, Chara> = ctx.charachips.charas().list.associateBy { it.id }
        val views = footsteps.list
            .sortedWith(compareBy({ it.day }, { it.roomNumbers }))
            .mapNotNull { footstep ->
                val registerChara = charaById[footstep.registerCharaId] ?: return@mapNotNull null
                val chara = charaById[footstep.charaId] ?: return@mapNotNull null
                VillageFootstepView(
                    footstep = footstep,
                    registerChara = registerChara,
                    chara = chara,
                    shouldRevealOwner = footstepRevealDomainService.shouldRevealOwner(
                        village = ctx.village,
                        myself = ctx.myself,
                        footstep = footstep,
                    ),
                )
            }
        return VillageFootstepsView(list = views)
    }

    @GetMapping("/{villageId}/myself")
    @Operation(
        summary = "自分視点の参加者情報",
        description = "ログイン中ユーザがこの村に参加していれば 200 + body、未参加なら 204 No Content。" +
                "当日の能力 / 投票 / コミット状態と役職別の入力仕様を含む。",
    )
    fun myself(
        @PathVariable villageId: Int,
    ): ResponseEntity<MyselfView> {
        val ctx = loadContext(villageId)
        val myself = ctx.myself ?: return ResponseEntity.noContent().build()
        val situation = villageCoordinator.findMyselfActionSituation(
            village = ctx.village,
            myself = myself,
            votes = voteService.findVotes(ctx.village.id),
            abilities = abilityService.findAbilities(ctx.village.id),
            footsteps = footstepService.findFootsteps(ctx.village.id),
            charachips = ctx.charachips,
            day = ctx.village.latestDay(),
        )
        return ResponseEntity.ok(MyselfView(myself, situation))
    }

    private fun loadContext(villageId: Int): VillageDetailContext {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val user = WolfMansionUserInfoUtil.getUserInfo()
        val player = user?.let { playerService.findPlayer(it.username) }
        val myself = user?.let { villageService.findVillageParticipant(village.id, it.username) }
        val charachips = village.setting.chara.let {
            charaService.findCharachips(it.charachipIds, it.isOriginalCharachip)
        }
        val players = playerService.findPlayers(village.id)
        return VillageDetailContext(village, user, player, myself, charachips, players)
    }

    private fun buildParticipants(ctx: VillageDetailContext): VillageParticipantsView {
        val charaById = ctx.charachips.charas().list.associateBy { it.id }
        val playerById = ctx.players.list.associateBy { it.id }
        val isSpoilerOpen = spoilerDomainService.isViewableSpoilerContent(ctx.village, ctx.myself)
        val sorted = ctx.village.allParticipants().sortedByRoomNumber().list
        val views = sorted.mapNotNull { participant ->
            val chara = charaById[participant.charaId] ?: return@mapNotNull null
            val playerName = playerById[participant.playerId]?.name
            VillageParticipantView(
                participant = participant,
                chara = chara,
                playerName = playerName,
                shouldHideSkill = shouldHideSkill(ctx, participant, isSpoilerOpen),
                shouldHidePlayer = shouldHidePlayer(ctx, participant, isSpoilerOpen),
                shouldHideAccess = shouldHideAccess(ctx, participant, isSpoilerOpen),
            )
        }
        return VillageParticipantsView(
            list = views,
            count = ctx.village.participants.count,
            spectatorCount = ctx.village.spectators.count,
        )
    }

    private fun shouldHideSkill(
        ctx: VillageDetailContext,
        participant: VillageParticipant,
        isSpoilerOpen: Boolean,
    ): Boolean {
        if (isSpoilerOpen) return false
        // 自分の役職は隠さない
        if (ctx.myself != null && ctx.myself.id == participant.id) return false
        return true
    }

    private fun shouldHidePlayer(
        ctx: VillageDetailContext,
        participant: VillageParticipant,
        isSpoilerOpen: Boolean,
    ): Boolean {
        if (isSpoilerOpen) return false
        // 自分のプレイヤー名は隠さない
        if (ctx.myself != null && ctx.myself.id == participant.id) return false
        return true
    }

    private fun shouldHideAccess(
        ctx: VillageDetailContext,
        participant: VillageParticipant,
        isSpoilerOpen: Boolean,
    ): Boolean {
        if (isSpoilerOpen) return false
        // 自分のアクセス時刻は隠さない
        if (ctx.myself != null && ctx.myself.id == participant.id) return false
        return true
    }

    private data class VillageDetailContext(
        val village: Village,
        val user: com.ort.app.fw.security.UserInfo?,
        val player: Player?,
        val myself: VillageParticipant?,
        val charachips: Charachips,
        val players: com.ort.app.domain.model.player.Players,
    )
}
