package com.ort.app.api.response.myself

import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import io.swagger.v3.oas.annotations.media.Schema

/**
 * RP 系 (キャラ名 / メモ / 表情差分) 操作の可否。
 *
 * - キャラ名変更: 募集中 + チップ仕様 + 自分が変更可能ステータス を全て満たすときのみ true。
 * - メモ変更: 募集中 + 自分が変更可能ステータス。
 * - 画像追加: オリジナルキャラチップ村 + 表情差分追加可能状態。
 */
@Schema(description = "RP 系操作の可否")
data class MyselfRpView(
    @field:Schema(description = "キャラ名 (name + shortName) を変更できるか")
    val isAvailableChangeName: Boolean,
    @field:Schema(description = "簡易メモを変更できるか")
    val isAvailableMemo: Boolean,
    @field:Schema(description = "表情差分の編集 / 追加が可能か (オリジナルキャラチップ村のみ true)")
    val canEditFaceType: Boolean,
) {
    constructor(situation: ParticipantRpSituation) : this(
        isAvailableChangeName = situation.isAvailableChangeName,
        isAvailableMemo = situation.isAvailableMemo,
        canEditFaceType = situation.canAddImage,
    )
}
