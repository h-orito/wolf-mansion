package com.ort.app.domain.model.auth

/**
 * 認証に必要な最小情報。パスワードハッシュを含むため Player ドメインモデルとは分離する。
 */
data class PlayerAuth(
    val playerId: Int,
    val name: String,
    val passwordHash: String,
    val authorities: List<String>,
)
