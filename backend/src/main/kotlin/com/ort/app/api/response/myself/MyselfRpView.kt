package com.ort.app.api.response.myself

import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import io.swagger.v3.oas.annotations.media.Schema

/**
 * RP 系 (キャラ名 / メモ / 表情差分) 操作の可否。
 *
 * - キャラ名変更: 募集中 + チップ仕様 + 自分が変更可能ステータス を全て満たすときのみ true。
 * - メモ変更: 募集中 + 自分が変更可能ステータス。
 * - 表情差分追加: オリジナルキャラチップ村 + 表情差分追加可能状態。
 *
 * NOTE: 表情差分の "編集" (name / display の更新) は API レベルでは所有者検証のみで通すため、
 *       本フィールド (`canAddFaceType`) は UI の表示制御用途。所有チェックを通れば backend は
 *       受理する点に留意 (旧 Thymeleaf も同様の挙動)。
 */
@Schema(description = "RP 系操作の可否")
data class MyselfRpView(
    @field:Schema(description = "キャラ名 (name + shortName) を変更できるか")
    val isAvailableChangeName: Boolean,
    @field:Schema(description = "簡易メモを変更できるか")
    val isAvailableMemo: Boolean,
    @field:Schema(description = "表情差分の追加 (および編集 UI 表示) が可能か。オリジナルキャラチップ村でのみ true")
    val canAddFaceType: Boolean,
) {
    constructor(situation: ParticipantRpSituation) : this(
        isAvailableChangeName = situation.isAvailableChangeName,
        isAvailableMemo = situation.isAvailableMemo,
        // ParticipantRpSituation.canAddImage は "画像追加可能か" の意。表情差分編集 (PUT) は
        // 所有者検証のみで通すため、UI の追加 / 編集パネル表示判定としてそのまま使う。
        canAddFaceType = situation.canAddImage,
    )
}
