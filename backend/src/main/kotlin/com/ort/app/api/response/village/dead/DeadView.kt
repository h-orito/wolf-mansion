package com.ort.app.api.response.village.dead

import com.ort.app.domain.model.village.participant.dead.Dead
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 死亡情報。`VillageParticipant.dead` (= ドメインモデルの Dead) に対応する API view。
 *
 * 生存中の参加者では `VillageParticipantView.dead` 自体が null になるので、ここでは
 * 必ず「死亡している」状態を表す (= `isDead: Boolean` フィールドは持たない)。
 *
 * 進行中の村では「無惨死」 (襲撃 / 呪殺 / 罠死 / 爆死 / 雑魚) を区別すると役職推理に
 * 直結するため、`shouldMaskMiserable=true` のとき code / name を `MISERABLE` / `無惨`
 * の合成値に統一する (firewolf の `DeadView` と同方式)。
 * 突然 (SUDDON) / 処刑 (EXECUTE) / 後追 (SUICIDE) は公開して良い死因なので透過。
 */
@Schema(description = "死亡情報 (生存中は VillageParticipantView.dead 自体が null)")
data class DeadView(
    @field:Schema(
        description = "死亡理由コード。CDef.DeadReason のコード " +
                "(EXECUTE / SUDDON ※DBFlute の typo / SUICIDE 等)、または進行中の無惨死を " +
                "統一する API 専用の合成コード 'MISERABLE'。"
    )
    val code: String,
    @field:Schema(description = "死亡理由表示名。例: 処刑 / 襲撃 / 突然 / 無惨")
    val name: String,
    @field:Schema(description = "死亡日 (= 何日目に死亡したか)")
    val day: Int,
) {
    constructor(dead: Dead, shouldMaskMiserable: Boolean) : this(
        // 進行中の無惨死、または DB 不整合で reason=null になっているデータも
        // MISERABLE / 無惨 にフォールバック (旧 maskedReason の null ガード相当)。
        // 後者は理論上発生しないはずだが、isDead=true && reason=null のレコードが
        // 存在しても 500 にせず役職推理にも影響しない安全な値で返す。
        code = if (shouldMaskMiserable && dead.isMiserableDead()) MISERABLE_CODE
        else dead.reason?.code ?: MISERABLE_CODE,
        name = if (shouldMaskMiserable && dead.isMiserableDead()) MISERABLE_NAME
        else dead.reason?.name ?: MISERABLE_NAME,
        // 呼び出し側で `dead.isDead == true` を確認してから渡す前提なので
        // `deadDay` も非 null 想定。null だったら未死亡で本クラスを作っているので
        // バグなので明示的に !! で潰す。
        day = dead.deadDay!!,
    )

    companion object {
        private const val MISERABLE_CODE = "MISERABLE"
        private const val MISERABLE_NAME = "無惨"
    }
}
