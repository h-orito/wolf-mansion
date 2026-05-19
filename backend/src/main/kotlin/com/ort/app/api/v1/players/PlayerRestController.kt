package com.ort.app.api.v1.players

import com.ort.app.api.request.player.PlayerPasswordBody
import com.ort.app.api.request.player.PlayerProfileBody
import com.ort.app.api.response.player.MePlayerView
import com.ort.app.api.response.player.PlayerDetailView
import com.ort.app.api.response.player.PlayersView
import com.ort.app.application.coordinator.PlayerCoordinator
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * プレイヤー (= ユーザ) 系の REST API。
 *
 * 旧 `PlayerController` (Thymeleaf) の置き換え。
 *
 * - `GET /api/v1/players` 一覧 (ページング、認証不要)
 * - `GET /api/v1/players/me` 自プロフィール (認証必須)
 * - `PUT /api/v1/players/me/profile` 自プロフィール更新 (認証必須)
 * - `PUT /api/v1/players/me/password` 自パスワード変更 (認証必須)
 * - `GET /api/v1/players/{userName}` 詳細 (戦績 + 参加履歴、認証不要)
 *
 * 認証必須エンドポイントは未ログイン時に [WolfMansionBusinessException] を投げる
 * (HTTP 400)。旧 Thymeleaf 系の他 controller と同じ規約に揃えている。
 *
 * パスワード変更は現パスワード再入力を要求しない (旧実装に合わせる)。JWT cookie で
 * 既に認証されているユーザのみが叩ける前提。トレードオフ:
 * - 強み: ユーザビリティ重視、旧 Thymeleaf 実装からの行動互換
 * - 弱み: XSS で cookie を奪われた攻撃者がパスワードを書き換えてセッション奪取を持続できる
 *         (それでも HttpOnly cookie の前提があるので XSS が必要なため敷居は高い)
 * Step 12 / 13 で旧画面復元 → デザインモダナイズの過程でセキュリティ強化 (現パスワード要求 +
 * パスワード長制限緩和) を一括検討する見込み。
 */
@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "players", description = "プレイヤー")
class PlayerRestController(
    private val playerService: PlayerService,
    private val playerCoordinator: PlayerCoordinator,
    private val charaService: CharaService,
) {

    @GetMapping
    @Operation(
        summary = "プレイヤー一覧取得",
        description = "ページング付き。1 ページあたり 30 件。",
    )
    fun list(
        @Parameter(description = "ページ番号 (1 起点、未指定なら 1)")
        @RequestParam(required = false) pageNum: Int?,
    ): PlayersView {
        val players = playerService.findAllPlayers(pageSize = PAGE_SIZE, pageNum = pageNum ?: 1)
        return PlayersView(players)
    }

    @GetMapping("/me")
    @Operation(
        summary = "自プロフィール取得",
        description = "ログイン中のプレイヤーの基本情報を返す。未ログインなら 400。",
    )
    fun me(): MePlayerView {
        val player = currentPlayer()
        return MePlayerView(player)
    }

    @PutMapping("/me/profile")
    @Operation(
        summary = "自プロフィール更新",
        description = "Twitter ユーザ名 / 自己紹介を更新する。各フィールド null でクリア。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateProfile(@Valid @RequestBody body: PlayerProfileBody) {
        val player = currentPlayer()
        playerService.updatePlayerDetail(player.name, body.twitterUserName, body.introduction)
    }

    @PutMapping("/me/password")
    @Operation(
        summary = "自パスワード変更",
        description = "新しいパスワードと確認用パスワードが一致する場合のみ更新する。" +
                "現パスワードの再入力は要求しない (旧実装互換)。",
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changePassword(@Valid @RequestBody body: PlayerPasswordBody) {
        val player = currentPlayer()
        if (body.password != body.confirmPassword) {
            throw WolfMansionBusinessException("確認用パスワードが一致しません")
        }
        playerService.updatePassword(player.name, body.password)
    }

    @GetMapping("/{userName}")
    @Operation(
        summary = "プレイヤー詳細取得",
        description = "プロフィール + 戦績 + 参加 / 見学した村の履歴を返す。認証不要。" +
                "閲覧者自身のページなら `isSelf=true` を返す。",
    )
    fun detail(@PathVariable userName: String): PlayerDetailView {
        val player = playerService.findPlayer(userName)
            ?: throw WolfMansionRecordNotFoundException("player not found. name=$userName")
        val records = playerCoordinator.findPlayerRecords(player)

        val (originalVillages, normalVillages) = records.participateVillageList.partition {
            it.village.setting.chara.isOriginalCharachip
        }
        val originalCharas = charaService.findCharasByCharachipId(originalVillages.map { it.participant.charaId }, true)
        val charas = charaService.findCharasByCharachipId(normalVillages.map { it.participant.charaId }, false)

        val viewer = WolfMansionUserInfoUtil.getUserInfo()?.username
        val isSelf = viewer != null && viewer == player.name

        return PlayerDetailView.of(
            player = player,
            records = records,
            charas = charas,
            originalCharas = originalCharas,
            isSelf = isSelf,
        )
    }

    private fun currentPlayer() =
        WolfMansionUserInfoUtil.getUserInfo()?.username
            ?.let { playerService.findPlayer(it) }
            ?: throw WolfMansionBusinessException("ログインが必要です")

    companion object {
        private const val PAGE_SIZE = 30
    }
}
