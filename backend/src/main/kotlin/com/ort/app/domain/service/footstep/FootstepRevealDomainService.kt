package com.ort.app.domain.service.footstep

import com.ort.app.domain.model.footstep.Footstep
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

/**
 * 村状況欄の足音 DTO 構築時の "出した人" 開示判定。
 *
 * 既存の `FootstepDomainService.convertToSituation` の挙動と一致させる:
 *  - エピローグ / 終了: 全員に対し全公開
 *  - 進行中、墓下開示村 (`isOpenSkillInGrave`) で自分が dead / 見学: その閲覧者にだけ全公開
 *  - それ以外 (進行中の alive 参加者、人狼を含む / 未参加閲覧者 / 募集中 / 廃村):
 *    匿名表示 (registerChara / chara を null、`roomNumbers` のみ公開)
 *
 * NOTE: 「自分が登録した足音を自分には見せる」「team共有」は **行わない**。
 * 既存挙動が全員匿名扱いであり、夢遊病者など本人にもどこを通ったか不明な役職がある
 * ため、村状況欄では一律で隠す。能力フォーム / 行使履歴で自分の登録足音を見たい場合は
 * 別 endpoint (Step 7 の ability 系) で扱う想定。
 */
@Service
class FootstepRevealDomainService {

    fun shouldRevealOwner(
        village: Village,
        myself: VillageParticipant?,
        // 現状は per-footstep の判定要素は無いが、将来「特定の役職が他人の足音を見られる」等の
        // 拡張時に footstep 単位の情報 (登録者など) を参照できるようにシグネチャを保持する。
        @Suppress("UNUSED_PARAMETER") footstep: Footstep,
    ): Boolean {
        // 1) settled (エピローグ / 終了) なら全員に対し全公開
        if (village.status.isSettled()) return true
        // 2) 進行中でも、墓下開示村で自分が dead / 見学なら全公開
        //    (既存 SpoilerDomainService.isViewableSpoilerContent の "myself 側" と整合)
        val isOpenSkillInGrave = village.setting.rule.isOpenSkillInGrave
        if (myself?.isViewableSpoilerContent(isOpenSkillInGrave) == true) return true
        // 3) それ以外は匿名 (自分の足音であっても、人狼であっても同じ)
        return false
    }
}
