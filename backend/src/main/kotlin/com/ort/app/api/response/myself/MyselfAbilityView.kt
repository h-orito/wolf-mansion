package com.ort.app.api.response.myself

import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "自分の能力状態 (役職別の入力仕様 + 当日の選択状態 + 履歴)")
data class MyselfAbilityView(
    @field:Schema(description = "能力を行使できる状態か (進行中 + 生存中 + 行使可能な日)")
    val canUseAbility: Boolean,
    @field:Schema(description = "能力種別コード (役職に能力なしなら null)")
    val typeCode: String?,
    @field:Schema(description = "能力種別名 (役職に能力なしなら null)")
    val typeName: String?,
    @field:Schema(description = "対象候補のキャラ ID 一覧 (襲撃希望の場合は空、襲撃可能対象は別 GET で取る)")
    val targetCharaIds: List<Int>,
    @field:Schema(description = "捜査対象の足音候補")
    val targetFootsteps: List<String>,
    @field:Schema(description = "選択中の襲撃者キャラ ID (襲撃希望のみ)")
    val attackerCharaId: Int?,
    @field:Schema(description = "選択中の対象キャラ ID")
    val targetCharaId: Int?,
    @field:Schema(description = "選択中の捜査対象足音 (捜査のみ)")
    val targetFootstep: String?,
    @field:Schema(description = "選択中の足音 (徘徊・襲撃希望など)")
    val footstep: String?,
    @field:Schema(description = "対象なし (no-target) を許容するか")
    val isAvailableNoTarget: Boolean,
    @field:Schema(description = "襲撃者候補のキャラ ID 一覧 (襲撃希望のみ)")
    val attackerCharaIds: List<Int>,
    @field:Schema(description = "能力行使履歴")
    val skillHistoryList: List<String>,
    @field:Schema(description = "対象指定の前置きメッセージ")
    val targetPrefixMessage: String?,
    @field:Schema(description = "対象指定の後置きメッセージ")
    val targetSuffixMessage: String?,
    @field:Schema(description = "対象指定と同時に足音を選ぶ能力か (狩人・人狼の襲撃希望など)")
    val isTargetingAndFootstep: Boolean,
) {
    constructor(situation: ParticipantAbilitySituation) : this(
        canUseAbility = situation.canUseAbility,
        typeCode = situation.type?.code,
        typeName = situation.type?.name,
        targetCharaIds = situation.targetList.map { it.charaId },
        targetFootsteps = situation.targetFootstepList,
        attackerCharaId = situation.attacker?.charaId,
        targetCharaId = situation.target?.charaId,
        targetFootstep = situation.targetFootstep,
        footstep = situation.footstep,
        isAvailableNoTarget = situation.isAvailableNoTarget,
        attackerCharaIds = situation.attackerList.map { it.charaId },
        skillHistoryList = situation.skillHistoryList,
        targetPrefixMessage = situation.targetPrefix,
        targetSuffixMessage = situation.targetSuffix,
        isTargetingAndFootstep = situation.isTargetingAndFootstep,
    )
}
