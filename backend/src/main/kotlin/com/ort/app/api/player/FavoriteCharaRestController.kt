package com.ort.app.api.player

import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FavoriteCharaService
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.fw.exception.WolfMansionAuthException
import com.ort.app.fw.security.jwt.JwtPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * お気に入りキャラの REST。ログインプレイヤー本人のお気に入りのみ読み書きできる
 * (security 設定で認証必須チェーンに乗せている)。参加検討中のキャラを他者に見せないため公開 API は無い。
 */
@RestController
@RequestMapping("/api/v1/players/me/favorite-charas")
class FavoriteCharaRestController(
    private val favoriteCharaService: FavoriteCharaService,
    private val charaService: CharaService,
) {
    /** お気に入りキャラを、所属キャラチップの charas をお気に入りのみに絞った Charachips として返す。 */
    @Operation(operationId = "getMyFavoriteCharas")
    @GetMapping
    fun list(
        @AuthenticationPrincipal principal: JwtPrincipal?,
    ): Charachips = favoriteCharaService.findFavoriteCharachips(resolvePlayerId(principal))

    @Operation(operationId = "addMyFavoriteChara")
    @PutMapping("/{charaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun add(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable charaId: Int,
    ) {
        charaService.findChara(charaId, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "chara not found")
        favoriteCharaService.addFavoriteChara(resolvePlayerId(principal), charaId)
    }

    @Operation(operationId = "deleteMyFavoriteChara")
    @DeleteMapping("/{charaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @AuthenticationPrincipal principal: JwtPrincipal?,
        @PathVariable charaId: Int,
    ) {
        favoriteCharaService.deleteFavoriteChara(resolvePlayerId(principal), charaId)
    }

    private fun resolvePlayerId(principal: JwtPrincipal?): Int {
        // principal は filter chain の authenticated() で保証済み (到達時は非 null)。防御的に確認する
        principal ?: throw WolfMansionAuthException("ログインしてください")
        return principal.playerId
    }
}
