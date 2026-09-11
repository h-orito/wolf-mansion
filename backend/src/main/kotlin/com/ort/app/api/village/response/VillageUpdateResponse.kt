package com.ort.app.api.village.response

/**
 * 村ポーリングの応答。クライアントは既知の最新日と比較して日付更新を検知する。
 * 日付更新の判定・実行はサーバ側 (ポーリング駆動の daychange) が行うため、
 * 本応答は検知に必要な最新日のみを返す。
 */
data class VillageUpdateResponse(
    val latestDay: Int,
)
