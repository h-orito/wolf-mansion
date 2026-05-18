package com.ort.app.api.v1.villages

import com.ort.app.api.response.message.MessagesView
import com.ort.app.api.response.myself.MyselfView
import com.ort.app.api.response.village.VillageFootstepView
import com.ort.app.api.response.village.VillageFootstepsView
import com.ort.app.api.response.village.VillageParticipantView
import com.ort.app.api.response.village.VillageParticipantsView
import com.ort.app.api.response.village.VillageView
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.MessageService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.chara.Chara
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
        return VillageView(
            village = ctx.village,
            participants = participants,
            isCreator = ctx.player?.let { ctx.village.isCreator(it) } ?: false,
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
        val charaById: Map<Int, Chara> = ctx.charas.associateBy { it.id }
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
        description = "ログイン中ユーザがこの村に参加していれば 200 + body、未参加なら 200 + null。",
    )
    fun myself(
        @PathVariable villageId: Int,
    ): ResponseEntity<MyselfView?> {
        val ctx = loadContext(villageId)
        val myself = ctx.myself ?: return ResponseEntity.ok(null)
        return ResponseEntity.ok(MyselfView(myself))
    }

    private fun loadContext(villageId: Int): VillageDetailContext {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val user = WolfMansionUserInfoUtil.getUserInfo()
        val player = user?.let { playerService.findPlayer(it.username) }
        val myself = user?.let { villageService.findVillageParticipant(village.id, it.username) }
        val charas = village.setting.chara.let {
            charaService.findCharachips(it.charachipIds, it.isOriginalCharachip).charas().list
        }
        val players = playerService.findPlayers(village.id)
        return VillageDetailContext(village, user, player, myself, charas, players)
    }

    private fun buildParticipants(ctx: VillageDetailContext): VillageParticipantsView {
        val charaById = ctx.charas.associateBy { it.id }
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
        val charas: List<Chara>,
        val players: com.ort.app.domain.model.player.Players,
    )
}
