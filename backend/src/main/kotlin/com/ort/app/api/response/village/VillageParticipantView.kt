package com.ort.app.api.response.village

import com.ort.app.api.response.chara.CharaView
import com.ort.app.api.response.skill.SkillView
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.dead.DeadReason
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
    /**
     * 簡易メモ。旧 Thymeleaf 画面 (situation.html) でも全参加者ぶん公開して
     * いたので踏襲。プレイヤー自由入力 (max 20 chars) のため意図せず戦略情報が
     * 露出するリスクは認識しているが、公開範囲ポリシーの変更 (自分のみ /
     * 同陣営のみ等) は memo を補助的に使っている既存ユーザへの影響が大きく、
     * 別途仕様検討する。
     */
    @field:Schema(description = "簡易メモ (RP 用、未設定なら null / 全員分公開、旧画面と同等)")
    val memo: String?,
    @field:Schema(description = "部屋番号 (未割当なら null)")
    val roomNumber: Int?,
    @field:Schema(description = "見学者か")
    val isSpectator: Boolean,
    @field:Schema(description = "死亡しているか")
    val isDead: Boolean,
    @field:Schema(
        description = "死亡理由コード。" +
                "進行中は無惨死 (襲撃 / 呪殺 / 罠死 / 爆死 / 雑魚) を null でマスクし、" +
                "公開して良い死因 (突然 / 処刑 / 後追) のみ返す。エピローグ以降は全公開。" +
                "生存中も null。"
    )
    val deadReasonCode: String?,
    @field:Schema(
        description = "死亡理由表示名 (例: 処刑 / 襲撃 / 突然)。" +
                "deadReasonCode と同じマスク方針で進行中の無惨死は null。生存中も null。"
    )
    val deadReasonName: String?,
    @field:Schema(description = "死亡日 (生存中は null)。マスク対象ではないので進行中も日付は出る")
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
        shouldMaskDeadReason: Boolean,
    ) : this(
        participant = participant,
        chara = chara,
        playerName = playerName,
        shouldHideSkill = shouldHideSkill,
        shouldHidePlayer = shouldHidePlayer,
        shouldHideAccess = shouldHideAccess,
        // 進行中の無惨死 (襲撃 / 呪殺 / 罠死 / 爆死 / 雑魚) は役職推理に直結する
        // ため code/name 両方を null にマスクする。突然 / 処刑 / 後追 は公開して
        // 良い死因なので透過。エピローグ以降 (isSpoilerOpen=true) は全公開。
        maskedReason = maskedReason(participant, shouldMaskDeadReason),
    )

    private constructor(
        participant: VillageParticipant,
        chara: Chara,
        playerName: String?,
        shouldHideSkill: Boolean,
        shouldHidePlayer: Boolean,
        shouldHideAccess: Boolean,
        maskedReason: DeadReason?,
    ) : this(
        id = participant.id,
        chara = CharaView(chara),
        name = participant.name(),
        memo = participant.memo,
        roomNumber = participant.room?.number,
        isSpectator = participant.isSpectator,
        isDead = participant.dead.isDead,
        deadReasonCode = maskedReason?.code,
        deadReasonName = maskedReason?.name,
        deadDay = participant.dead.deadDay,
        isGone = participant.isGone,
        isWin = participant.isWin,
        skill = if (shouldHideSkill) null else participant.skill?.let { SkillView(it) },
        campCode = if (shouldHideSkill) null else participant.camp?.code,
        playerName = if (shouldHidePlayer) null else playerName,
        lastAccessDatetime = if (shouldHideAccess) null else participant.lastAccessDatetime,
    )

    companion object {
        private fun maskedReason(
            participant: VillageParticipant,
            shouldMaskDeadReason: Boolean,
        ): DeadReason? {
            val reason = participant.dead.reason ?: return null
            return if (shouldMaskDeadReason && reason.isMiserable()) null else reason
        }
    }
}
