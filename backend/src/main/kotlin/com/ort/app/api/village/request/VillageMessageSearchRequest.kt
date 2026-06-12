package com.ort.app.api.village.request

import com.ort.app.api.request.VillageGetMessageListForm
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.village.Village

/**
 * メッセージ一覧の検索条件。種別の展開 (GRAVE_SPECTATE_SAY の合成種別など) や
 * 全員選択時の空リスト化は SSR と共通の [VillageGetMessageListForm] に委譲し、
 * 絞り込みロジックを二重に持たない。
 *
 * 絞り込み系 (participantIds / toParticipantIds / types / keywords) は発言抽出 UI が
 * 使う受け口で、一覧表示だけなら省略してよい。
 */
data class VillageMessageSearchRequest(
    /** 表示する日。省略時は最新日 */
    val day: Int? = null,
    /** 1 ページあたりの表示発言数 */
    val pageSize: Int? = null,
    /** 何ページ目か */
    val pageNum: Int? = null,
    /** ページングするか */
    val isPaging: Boolean? = null,
    /** 最新ページを表示するか */
    val isDispLatest: Boolean? = null,
    /** 発言者の参加者 ID (省略時は全員) */
    val participantIds: List<Int>? = null,
    /** 宛先の参加者 ID (省略時は全員) */
    val toParticipantIds: List<Int>? = null,
    /** メッセージ種別コード (省略時は閲覧可能な全種別) */
    val types: List<String>? = null,
    /** キーワード (スペース区切り) */
    val keywords: String? = null,
) {
    fun toQuery(village: Village): MessageQuery =
        VillageGetMessageListForm(
            villageId = village.id,
            day = day,
            pageSize = pageSize,
            pageNum = pageNum,
            toParticipantIds = toParticipantIds?.joinToString(","),
            types = types?.joinToString(","),
            participantIds = participantIds?.joinToString(","),
            keywords = keywords,
            isPaging = isPaging,
            isDispLatest = isDispLatest,
        ).toMessageQuery(village)
}
