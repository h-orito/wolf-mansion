package com.ort.app.api.v1.villages

import com.ort.app.api.response.message.MessagesView
import com.ort.app.api.response.myself.MyselfView
import com.ort.app.api.response.village.VillageFootstepView
import com.ort.app.api.response.village.VillageFootstepsView
import com.ort.app.api.response.village.VillageParticipantView
import com.ort.app.api.response.village.VillageParticipantsView
import com.ort.app.api.response.village.VillageView
import com.ort.app.api.response.village.situation.VillageSituationView
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
import com.ort.app.domain.model.message.MessageType
import com.ort.dbflute.allcommon.CDef
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.service.SpoilerDomainService
import com.ort.app.domain.service.footstep.FootstepRevealDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
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
        val players = playerService.findPlayers(ctx.village.id)
        val participants = buildParticipants(ctx, players)
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
        description = "指定された日の閲覧可能な発言を返す。閲覧権限は閲覧者の参加状況 + 役職 + 村ステータスで決まる。" +
            " filter / paging クエリを指定できる (`messageType` 等で絞り込み、`page` で順次表示)。",
    )
    fun messages(
        @PathVariable villageId: Int,
        @Parameter(description = "何日目か。未指定なら最新日。", required = false)
        @RequestParam(required = false) day: Int?,
        @Parameter(description = "1 ページあたりの件数 (1 以上)。指定するとページング ON。", required = false)
        @RequestParam(required = false) pageSize: Int?,
        @Parameter(description = "1 始まりのページ番号。pageSize 必須。", required = false)
        @RequestParam(required = false) pageNum: Int?,
        @Parameter(description = "絞り込み対象発言種別の code (複数指定で OR)。未指定なら全種別。", required = false)
        @RequestParam(required = false) messageType: List<String>?,
        @Parameter(description = "発言者 participantId (複数で OR)。", required = false)
        @RequestParam(required = false) fromParticipantId: List<Int>?,
        @Parameter(description = "宛先 participantId (複数で OR、秘話宛先で使用)。", required = false)
        @RequestParam(required = false) toParticipantId: List<Int>?,
        @Parameter(description = "本文キーワード (スペース区切りで OR)。", required = false)
        @RequestParam(required = false) keyword: String?,
    ): MessagesView {
        // ページング系の数値は DBFlute の `paging(pageSize, pageNum)` に渡る。
        // 0 / 負値だと実行時例外 or 全件取得などで挙動が崩れるので、ここで 400 に正規化。
        if (pageSize != null && pageSize < 1) {
            throw WolfMansionBusinessException("pageSize は 1 以上を指定してください")
        }
        if (pageNum != null && pageNum < 1) {
            throw WolfMansionBusinessException("pageNum は 1 以上を指定してください")
        }
        val ctx = loadContext(villageId)
        val targetDay = day ?: ctx.village.latestDay()
        // pageSize が指定されていればページング ON。pageNum 未指定なら 1 ページ目。
        val isPaging = pageSize != null
        // 旧 `VillageGetMessageListForm.typeMap` 互換: ユーザに見せている発言種別 (例:
        // `GRAVE_SPECTATE_SAY`, `PRIVATE_SYSTEM`) は内部では複数 CDef.MessageType を束ねている。
        // フィルタ未指定 (== messageType が null) のときは空リスト = 全種別扱いで通す。
        val requestTypes = messageType.orEmpty()
            .flatMap { MESSAGE_FILTER_TYPE_MAP[it] ?: emptyList() }
            .distinctBy { it.code() }
            .map { MessageType(it) }
        val query = MessageQuery(
            village = ctx.village,
            day = targetDay,
            pageSize = pageSize,
            pageNum = if (isPaging) pageNum ?: 1 else null,
            fromParticipantIds = fromParticipantId.orEmpty(),
            toParticipantIds = toParticipantId.orEmpty(),
            requestTypes = requestTypes,
            keywords = keyword?.takeIf { it.isNotBlank() },
            isPaging = isPaging,
            // ページング OFF または pageNum 未指定 (= 1 ページ目を求めている) を「最新を表示」扱いにする。
            // 旧 `VillageGetMessageListForm` ではこのフラグは JS 側から明示的に渡されていたが、
            // REST 化後は GET param を簡潔にするため pageNum の有無から自動で導出している。
            isDispLatest = !isPaging || pageNum == null,
        )
        val viewerPlayer = ctx.user?.let { playerService.findPlayer(it.username) }
        val messages = messageService.findMeesages(ctx.village, ctx.myself, viewerPlayer, query)
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

    @GetMapping("/{villageId}/situation")
    @Operation(
        summary = "状況サマリ取得",
        description = "旧 Thymeleaf 画面の '状況 / 投票 / 足音' タブ相当。各日の死亡・復活・能力履歴、" +
                "参加者ごとの投票テーブル、日別の足音まとめを返す。\n" +
                "- `whole.ability` は spoiler 開示状態 (= エピローグ以降 / 見学 / 開示村の死亡など) でのみ非空\n" +
                "- `vote` は黒箱日 (隠蔽能力対象) を domain 側で除外済み\n" +
                "- `dayFootsteps` の足音文字列は domain `FootstepDomainService.convertToSituation` の隠匿ロジックを通る",
    )
    fun situation(
        @PathVariable villageId: Int,
        @Parameter(description = "現在表示中の日。投票表 / 状況表の範囲決定は frontend 側で行うため、ここでは domain への day 引数 (= ability 履歴の起点) として渡す。未指定なら最新日。", required = false)
        @RequestParam(required = false) day: Int?,
    ): VillageSituationView {
        val ctx = loadContext(villageId)
        val targetDay = day ?: ctx.village.latestDay()
        val votes = voteService.findVotes(ctx.village.id)
        val abilities = abilityService.findAbilities(ctx.village.id)
        val footsteps = footstepService.findFootsteps(ctx.village.id)
        val situation = villageCoordinator.findVillageSituation(
            village = ctx.village,
            myself = ctx.myself,
            votes = votes,
            abilities = abilities,
            footsteps = footsteps,
            day = targetDay,
        )
        return VillageSituationView(
            village = ctx.village,
            whole = situation.whole,
            vote = situation.vote,
            footstep = situation.footstep,
        )
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
            username = ctx.user?.username,
            votes = voteService.findVotes(ctx.village.id),
            abilities = abilityService.findAbilities(ctx.village.id),
            footsteps = footstepService.findFootsteps(ctx.village.id),
            charachips = ctx.charachips,
            day = ctx.village.latestDay(),
        )
        return ResponseEntity.ok(MyselfView(myself, situation, ctx.charachips))
    }

    /**
     * 4 endpoint で共通して必要な最小コンテキストだけを構築する。
     * 全 endpoint で必須でない以下は呼び出し側で必要なときだけ fetch する:
     * - `playerService.findPlayer(username)` (viewer の Player、`messages` のみ)
     * - `playerService.findPlayers(villageId)` (村の全プレイヤー、`get`/`buildParticipants` のみ)
     */
    private fun loadContext(villageId: Int): VillageDetailContext {
        val village = villageService.findVillage(villageId, excludeGone = false)
            ?: throw WolfMansionRecordNotFoundException("village not found. id=$villageId")
        val user = WolfMansionUserInfoUtil.getUserInfo()
        val myself = user?.let { villageService.findVillageParticipant(village.id, it.username) }
        val charachips = village.setting.chara.let {
            charaService.findCharachips(it.charachipIds, it.isOriginalCharachip)
        }
        return VillageDetailContext(village, user, myself, charachips)
    }

    private fun buildParticipants(
        ctx: VillageDetailContext,
        players: Players,
    ): VillageParticipantsView {
        val charaById = ctx.charachips.charas().list.associateBy { it.id }
        val playerById = players.list.associateBy { it.id }
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
                // 進行中は無惨死 (襲撃 / 呪殺 / 罠死 / 爆死 / 雑魚) の区別を隠す。
                // 突然 / 処刑 / 後追 は公開して良いのでマスクしない。
                // 自分自身の死因も同じく隠す: PRIVATE_SYSTEM 系は admin のみが
                // 閲覧可能 (VillageParticipant.isViewablePrivateSystemMessage)
                // なので、一般プレイヤーには「自分が呪殺されたか襲撃されたか」を
                // API でもメッセージでも進行中は公開しない (無惨死として扱う) 方針。
                shouldMaskDeadReason = !isSpoilerOpen,
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
        val myself: VillageParticipant?,
        val charachips: Charachips,
    )

    companion object {
        // フィルタ UI 上で「1 項目」として見せる発言種別を、検索クエリ用の CDef.MessageType
        // (複数) に展開するためのマップ。旧 `VillageGetMessageListForm.typeMap` 互換。
        // 例: `GRAVE_SPECTATE_SAY` (墓下見学) は内部では 死者の呻き + 見学発言 の 2 種別を覆う。
        private val MESSAGE_FILTER_TYPE_MAP: Map<String, List<CDef.MessageType>> = mapOf(
            CDef.MessageType.通常発言.code() to listOf(CDef.MessageType.通常発言),
            CDef.MessageType.村建て発言.code() to listOf(CDef.MessageType.村建て発言),
            CDef.MessageType.人狼の囁き.code() to listOf(CDef.MessageType.人狼の囁き),
            CDef.MessageType.恋人発言.code() to listOf(CDef.MessageType.恋人発言),
            CDef.MessageType.念話.code() to listOf(CDef.MessageType.念話),
            CDef.MessageType.共鳴発言.code() to listOf(CDef.MessageType.共鳴発言),
            "GRAVE_SPECTATE_SAY" to listOf(CDef.MessageType.死者の呻き, CDef.MessageType.見学発言),
            CDef.MessageType.独り言.code() to listOf(CDef.MessageType.独り言),
            CDef.MessageType.秘話.code() to listOf(CDef.MessageType.秘話),
            CDef.MessageType.アクション.code() to listOf(CDef.MessageType.アクション),
            CDef.MessageType.公開システムメッセージ.code() to listOf(
                CDef.MessageType.公開システムメッセージ,
                CDef.MessageType.参加者一覧,
            ),
            CDef.MessageType.非公開システムメッセージ.code() to listOf(
                CDef.MessageType.非公開システムメッセージ,
                CDef.MessageType.白黒占い結果,
                CDef.MessageType.役職占い結果,
                CDef.MessageType.白黒霊視結果,
                CDef.MessageType.役職霊視結果,
                CDef.MessageType.検死結果,
                CDef.MessageType.襲撃結果,
                CDef.MessageType.足音調査結果,
                CDef.MessageType.恋人メッセージ,
                CDef.MessageType.妖狐メッセージ,
                CDef.MessageType.能力行使メッセージ,
            ),
        )
    }
}
