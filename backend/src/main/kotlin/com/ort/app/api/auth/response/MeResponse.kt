package com.ort.app.api.auth.response

import com.ort.app.fw.security.jwt.JwtPrincipal

/**
 * 現在のログインプレイヤーの最小情報。login / refresh / me で共通して返す。
 */
data class MeResponse(
    val playerId: Int,
    val name: String,
    val authorities: List<String>,
) {
    constructor(principal: JwtPrincipal) : this(
        playerId = principal.playerId,
        name = principal.name,
        authorities = principal.authorities,
    )
}
