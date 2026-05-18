package com.ort.app.domain.service.footstep

import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

/**
 * 足音 DTO 構築時の "出した人" 開示判定。
 *
 * - 終了 / エピローグ: 全員に対し全公開
 * - 募集中 / 廃村: そもそも未登録 (呼び出し側で空リストを返す前提なので念のため false)
 * - 進行中: 自分の足音は常に開示、同じ "say チャンネル" を共有する陣営なら開示、それ以外は隠す
 *
 * "team共有" のルールは既存 Say の `isViewable*` セマンティクスに合わせる:
 *  - 人狼の囁き: `isViewableWerewolfSay()` 同士
 *  - 共鳴: `isViewableSympathizeSay()` 同士
 *  - 念話 (狐): `isViewableTelepathy()` 同士 — fox-possessioned も同じチャンネルに含むのは Say と同じ
 *  - 恋人: **per-pair** で判定する (`loverIdList` overlap)。Say では複数恋人グループが全部見える既存挙動だが、
 *         足音は推理ゲーム上ペア境界を尊重した方が公平なので per-pair に絞っている。
 *
 * NOTE: `playerId == 1` の admin 参加者は `isViewableWerewolfSay` 等が常に true になるため、
 *       admin がプレイヤー参加した村では全チームの足音が見えてしまう。これは既存 Say と同じ挙動。
 */
@Service
class FootstepRevealDomainService {

    fun shouldRevealOwner(
        village: Village,
        myself: VillageParticipant?,
        footstep: Footstep,
    ): Boolean {
        if (village.status.isSettled()) return true
        if (!village.status.isProgress()) return false
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
        if (isSameLoverPair(viewer, owner)) return true
        return false
    }

    /**
     * 恋絆 (loverIdList) で直接結ばれているか。耳年増 (`isViewableLoversSay` skill check) は
     * 恋人ではないため per-pair の対象外で、足音は隠したままにする。
     */
    private fun isSameLoverPair(viewer: VillageParticipant, owner: VillageParticipant): Boolean {
        return viewer.status.loverIdList.contains(owner.id) ||
                owner.status.loverIdList.contains(viewer.id)
    }
}
