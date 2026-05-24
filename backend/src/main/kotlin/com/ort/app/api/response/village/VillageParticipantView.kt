package com.ort.app.api.response.village

import com.ort.app.api.response.chara.CharaView
import com.ort.app.api.response.skill.SkillView
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.village.participant.VillageParticipant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村参加者 (隠蔽済み)")
data class VillageParticipantView(
    @field:Schema(description = "参加者 ID")
    val id: Int,
    @field:Schema(description = "キャラクター")
    val chara: CharaView,
    @field:Schema(description = "表示名 ([部屋番号略称] 名前)")
    val name: String,
    @field:Schema(description = "簡易メモ (RP 用、未設定なら null / 全員分公開)")
    val memo: String?,
    @field:Schema(description = "部屋番号 (未割当なら null)")
    val roomNumber: Int?,
    @field:Schema(description = "見学者か")
    val isSpectator: Boolean,
    @field:Schema(description = "死亡しているか")
    val isDead: Boolean,
    @field:Schema(description = "死亡理由コード (生存中は null)")
    val deadReasonCode: String?,
    @field:Schema(description = "死亡理由表示名 (例: 処刑 / 襲撃 / 突然、生存中は null)")
    val deadReasonName: String?,
    @field:Schema(description = "死亡日 (生存中は null)")
    val deadDay: Int?,
    @field:Schema(description = "退村済みか")
    val isGone: Boolean,
    @field:Schema(description = "勝利したか (確定前は null)")
    val isWin: Boolean?,
    @field:Schema(description = "役職 (進行中に他人の役職を見られないなら null)")
    val skill: SkillView?,
    @field:Schema(description = "勝敗判定陣営コード (役職と一緒に隠蔽)")
    val campCode: String?,
    @field:Schema(description = "プレイヤー名 (進行中に他人のプレイヤー名を見られないなら null)")
    val playerName: String?,
    @field:Schema(description = "最終アクセス日時 (他人のものは隠蔽されることがある)")
    val lastAccessDatetime: LocalDateTime?,
) {
    constructor(
        participant: VillageParticipant,
        chara: Chara,
        playerName: String?,
        shouldHideSkill: Boolean,
        shouldHidePlayer: Boolean,
        shouldHideAccess: Boolean,
    ) : this(
        id = participant.id,
        chara = CharaView(chara),
        name = participant.name(),
        memo = participant.memo,
        roomNumber = participant.room?.number,
        isSpectator = participant.isSpectator,
        isDead = participant.dead.isDead,
        deadReasonCode = participant.dead.reason?.code,
        deadReasonName = participant.dead.reason?.name,
        deadDay = participant.dead.deadDay,
        isGone = participant.isGone,
        isWin = participant.isWin,
        skill = if (shouldHideSkill) null else participant.skill?.let { SkillView(it) },
        campCode = if (shouldHideSkill) null else participant.camp?.code,
        playerName = if (shouldHidePlayer) null else playerName,
        lastAccessDatetime = if (shouldHideAccess) null else participant.lastAccessDatetime,
    )
}
