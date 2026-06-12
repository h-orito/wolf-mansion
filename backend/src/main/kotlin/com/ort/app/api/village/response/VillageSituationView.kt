package com.ort.app.api.village.response

import com.ort.app.api.view.VillageContent
import com.ort.app.api.view.village.VillageMemberContent
import com.ort.app.api.view.village.VillageRoomAssignedRow
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.situation.VillageSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

/**
 * 村状況 (誰が取得しても同じ村全体の現況)。状況サマリの部屋割り / 参加者 / 投票 / 足音 /
 * 日別状況に対応する。
 *
 * 視点依存のスポイラーマスクは backend で適用済みのデータのみを返す:
 * - 部屋割りの役職名は [VillageRoomAssignedRow] が可視判定して null に倒す
 * - 足音・日別状況の能力欄・投票の隠蔽日は domain service が可視判定して整形する
 * フィールド構成はテンプレート向けの [VillageContent] の実フィールドを正本とする。
 */
data class VillageSituationView(
    /** 部屋割り行 (部屋なし村は null) */
    val roomAssignedRowList: List<VillageRoomAssignedRow>?,
    /** 部屋の横サイズ */
    val roomWidth: Int?,
    /** ステータス別の参加者一覧 */
    val memberList: List<VillageMemberContent>,
    /** 投票表 (3日目以降のみ) */
    val vote: VillageContent.VillageVoteContent?,
    /** 日別の足音 */
    val footstepList: List<VillageContent.VillageFootstepContent>,
    /** 日別状況 (突然死/処刑/犠牲/復活/後追/能力) */
    val situationList: List<VillageContent.VillageSituationContent>,
    /** このビューアにスポイラー (役職・能力欄など) を表示してよいか */
    val isViewableSpoilerContent: Boolean,
) {
    constructor(
        village: Village,
        day: Int,
        villageSituation: VillageSituation,
        charachips: Charachips,
        myself: VillageParticipant?,
        player: Player?,
        isViewableSpoilerContent: Boolean,
    ) : this(
        roomAssignedRowList =
            village.roomSize?.let { roomSize ->
                List(roomSize.height) { columnIndex ->
                    VillageRoomAssignedRow(village, day, columnIndex, charachips, myself, player)
                }
            },
        roomWidth = village.roomSize?.width,
        memberList =
            villageSituation.live.list.map {
                VillageMemberContent(
                    status = it.status,
                    statusMemberList = it.list.map { participant -> VillageMemberContent.VillageMemberDetailContent(participant) },
                )
            },
        vote = if (day > 2) VillageContent.VillageVoteContent(village, villageSituation) else null,
        footstepList = villageSituation.footstep.list.map { VillageContent.VillageFootstepContent(it.day, it.footstep) },
        situationList = villageSituation.whole.list.map { VillageContent.VillageSituationContent(it) },
        isViewableSpoilerContent = isViewableSpoilerContent,
    )
}
