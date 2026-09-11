package com.ort.app.api.village.response

import com.ort.app.domain.model.village.Village

/**
 * 村作成のレスポンス。raw `Village` には入村パスワード等のマスク対象が含まれるため、
 * 遷移に必要な村 ID のみ返す (REST API 設計方針: マスクが要る場合は Response DTO)。
 */
data class VillageCreateResponse(
    val id: Int,
) {
    constructor(village: Village) : this(id = village.id)
}
