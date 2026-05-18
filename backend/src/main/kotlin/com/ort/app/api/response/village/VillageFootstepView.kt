package com.ort.app.api.response.village

import com.ort.app.api.response.chara.CharaView
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.footstep.Footstep
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 足音 (隠蔽済み)。
 *
 * 進行中は他人の足音は registerChara / chara を null にして経路 (roomNumbers) のみを返す。
 * エピローグ / 終了では全員に全公開。
 */
@Schema(description = "足音 (隠蔽済み)")
data class VillageFootstepView(
    @field:Schema(description = "セットした日 (= 鳴った日の前日)")
    val day: Int,
    @field:Schema(description = "足音を登録したキャラ (隠蔽時 null)")
    val registerChara: CharaView?,
    @field:Schema(description = "足音の主として偽装されたキャラ (隠蔽時 null)")
    val chara: CharaView?,
    @field:Schema(description = "経路 (カンマ区切りの部屋番号、または 'なし')")
    val roomNumbers: String,
) {
    constructor(
        footstep: Footstep,
        registerChara: Chara,
        chara: Chara,
        shouldRevealOwner: Boolean,
    ) : this(
        day = footstep.day,
        registerChara = if (shouldRevealOwner) CharaView(registerChara) else null,
        chara = if (shouldRevealOwner) CharaView(chara) else null,
        roomNumbers = footstep.roomNumbers,
    )
}
