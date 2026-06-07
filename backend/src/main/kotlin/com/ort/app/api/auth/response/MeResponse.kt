package com.ort.app.api.auth.response

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.canCreateVillage
import com.ort.app.fw.security.jwt.JwtPrincipal

/**
 * 現在のログインプレイヤーのビュー。login / refresh / me で共通して返す。
 * `canCreateVillage` はプレイヤー由来の項目 (どの村にも参加していない時 true)。
 */
data class MeResponse(
    val playerId: Int,
    val name: String,
    val authorities: List<String>,
    /** 村を作成できるか。「村を建てる」導線の出し分けに使う */
    val canCreateVillage: Boolean,
) {
    constructor(
        principal: JwtPrincipal,
        player: Player?,
    ) : this(
        playerId = principal.playerId,
        name = principal.name,
        authorities = principal.authorities,
        canCreateVillage = player.canCreateVillage(),
    )
}
