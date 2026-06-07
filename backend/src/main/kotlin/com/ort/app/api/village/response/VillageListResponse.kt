package com.ort.app.api.village.response

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.setting.VillageTags
import com.ort.dbflute.allcommon.CDef

/**
 * 村一覧 (`GET /api/v1/villages`) のレスポンス。
 * 一覧の整形ロジックは旧 `IndexContent.IndexVillage` を踏襲する (april ネタのデッドフィールドは持たない)。
 * トップページ・村一覧画面など複数画面で共有する (画面固有 API にはしない)。
 */
data class VillageListResponse(
    val villages: List<VillageSummary>,
) {
    constructor(villages: Villages) : this(villages = villages.list.map { VillageSummary(it) })

    data class VillageSummary(
        /** 村ID (遷移キー) */
        val villageId: Int,
        /** 村表示用番号 (4桁0埋め) */
        val villageNumber: String,
        /** 村名 */
        val villageName: String,
        /** 人数 (整形済み文字列) */
        val participateNum: String,
        /** 状態 (募集中/進行中/エピローグ/終了/廃村) */
        val status: String,
        /** タグ */
        val tags: List<Tag>,
    ) {
        constructor(village: Village) : this(
            villageId = village.id,
            villageNumber = village.id.toString().padStart(4, '0'),
            villageName = village.name,
            participateNum = convertToParticipateNumStr(village),
            status = village.status.name,
            tags = mapTags(village.setting.tags),
        )

        data class Tag(
            val level: String,
            val name: String,
        )

        companion object {
            private fun convertToParticipateNumStr(village: Village): String {
                val participateNum = village.participants.count
                val spectatorNum = village.spectators.count
                val spectatorStr = if (spectatorNum <= 0) "" else " ($spectatorNum)"
                return if (village.status.isPrologue()) {
                    val maxNum = village.setting.personMax
                    "$participateNum/$maxNum${spectatorStr}人"
                } else {
                    "$participateNum${spectatorStr}人"
                }
            }

            private fun mapTags(tags: VillageTags): List<Tag> {
                val age =
                    tags.list
                        .find { it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18 }
                        ?.let { listOf(Tag(level = "danger", name = it.name)) } ?: emptyList()
                val welcome =
                    tags.list
                        .find { it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内 }
                        ?.let { listOf(Tag(level = "success", name = it.name)) } ?: emptyList()
                return age + welcome
            }
        }
    }
}
