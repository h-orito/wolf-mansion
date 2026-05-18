package com.ort.app.domain.service.footstep

import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

/**
 * 足音 DTO 構築時の "出した人" 開示判定。
 *
 * - 終了 / エピローグ: 全員に対し全公開
 * - 募集中: そもそも未登録 (呼び出し側で空リストを返す前提なので念のため false)
 * - 進行中: 自分の足音は常に開示、同じ "say チャンネル" を共有する陣営なら開示、それ以外は隠す
 */
@Service
class FootstepRevealDomainService {

    fun shouldRevealOwner(
        village: Village,
        myself: VillageParticipant?,
        footstep: Footstep,
    ): Boolean {
        if (village.status.isSettled()) return true
        if (village.status.isPrologue()) return false
        // 進行中
        myself ?: return false
        if (myself.charaId == footstep.registerCharaId) return true
        val owner = village.allParticipants().list
            .firstOrNull { it.charaId == footstep.registerCharaId }
            ?: return false
        return isSameTeam(myself, owner)
    }

    private fun isSameTeam(viewer: VillageParticipant, owner: VillageParticipant): Boolean {
        if (viewer.isViewableWerewolfSay() && owner.isViewableWerewolfSay()) return true
        if (viewer.isViewableSympathizeSay() && owner.isViewableSympathizeSay()) return true
        if (viewer.isViewableTelepathy() && owner.isViewableTelepathy()) return true
        if (viewer.isViewableLoversSay() && owner.isViewableLoversSay()) return true
        return false
    }
}
