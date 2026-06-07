package com.ort.app.api.village.response

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.setting.VillageTags
import com.ort.dbflute.allcommon.CDef

/**
 * 村一覧用の軽量ビュー (firewolf の SimpleVillageView と同じ発想)。
 * 詳細画面用の重い ViewModel (参加者・日付・設定一式) は持たず、一覧表示に必要な**素の構造化データ**だけを返す。
 * 村番号の 0 埋めや参加人数の文字列化といった**表示整形はしない (画面側で行う)**。
 */
data class SimpleVillageView(
    /** 村ID (表示番号は画面側で 0 埋めする) */
    val id: Int,
    /** 村名 */
    val name: String,
    /** 状態の表示名 (募集中/進行中/エピローグ/終了/廃村) */
    val statusName: String,
    /** 募集中か (参加人数を `参加/定員` 形式で出すかの判定に使う) */
    val isPrologue: Boolean,
    /** 参加人数 */
    val participantCount: Int,
    /** 見学人数 */
    val spectatorCount: Int,
    /** 定員 (上限) */
    val maxPersonCount: Int,
    /** 一覧で出すタグ (年齢制限/歓迎区分)。色付けは画面側 */
    val tags: List<VillageTag>,
) {
    constructor(village: Village) : this(
        id = village.id,
        name = village.name,
        statusName = village.status.name,
        isPrologue = village.status.isPrologue(),
        participantCount = village.participants.count,
        spectatorCount = village.spectators.count,
        maxPersonCount = village.setting.personMax,
        tags = mapTags(village.setting.tags),
    )

    /** 一覧タグ。`level` は区分 (danger=年齢制限 / success=歓迎)。 */
    data class VillageTag(
        val level: String,
        val name: String,
    )

    companion object {
        private fun mapTags(tags: VillageTags): List<VillageTag> {
            val age =
                tags.list
                    .find { it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18 }
                    ?.let { listOf(VillageTag(level = "danger", name = it.name)) } ?: emptyList()
            val welcome =
                tags.list
                    .find { it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内 }
                    ?.let { listOf(VillageTag(level = "success", name = it.name)) } ?: emptyList()
            return age + welcome
        }
    }
}
