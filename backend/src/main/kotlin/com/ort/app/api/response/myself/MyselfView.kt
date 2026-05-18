package com.ort.app.api.response.myself

import com.ort.app.api.response.skill.SkillView
import com.ort.app.domain.model.village.participant.VillageParticipant
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 自分視点の参加者情報 (隠蔽なし)。
 *
 * 自分の情報は隠蔽不要なので、村全体ビュー (VillageView) とは別 endpoint で返す。
 * 未参加 (user は居るが参加していない / 未ログイン) の場合はそもそも 200 で `null` を返す想定。
 */
@Schema(description = "自分視点の参加者情報 (隠蔽なし)")
data class MyselfView(
    @field:Schema(description = "参加者 ID")
    val id: Int,
    @field:Schema(description = "キャラ ID")
    val charaId: Int,
    @field:Schema(description = "表示名 ([部屋番号略称] 名前)")
    val name: String,
    @field:Schema(description = "部屋番号 (未割当なら null)")
    val roomNumber: Int?,
    @field:Schema(description = "見学者か")
    val isSpectator: Boolean,
    @field:Schema(description = "死亡しているか")
    val isDead: Boolean,
    @field:Schema(description = "勝利したか (確定前は null)")
    val isWin: Boolean?,
    @field:Schema(description = "役職")
    val skill: SkillView?,
    @field:Schema(description = "陣営コード")
    val campCode: String?,
) {
    constructor(myself: VillageParticipant) : this(
        id = myself.id,
        charaId = myself.charaId,
        name = myself.name(),
        roomNumber = myself.room?.number,
        isSpectator = myself.isSpectator,
        isDead = myself.dead.isDead,
        isWin = myself.isWin,
        skill = myself.skill?.let { SkillView(it) },
        campCode = myself.camp?.code,
    )
}
