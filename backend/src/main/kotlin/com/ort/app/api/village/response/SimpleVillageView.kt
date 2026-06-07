package com.ort.app.api.village.response

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.setting.VillageTag

/**
 * 村一覧用の軽量ビュー (firewolf の SimpleVillageView と同じ発想)。
 *
 * できる限りドメインの [Village] に近い**生データ**を返し、一覧では不要な部分 (参加者の明細・組分け・
 * 入村パスワード等の重い/非公開な情報) だけを削る。状態名・募集中判定・参加人数文字列・村番号の 0 埋めといった
 * **表示整形は一切しない (画面側の責務)**。状態は派生フィールドではなくドメインの [VillageStatus] をそのまま返す。
 */
data class SimpleVillageView(
    /** 村ID (表示番号への 0 埋めは画面側) */
    val id: Int,
    /** 村名 */
    val name: String,
    /** 状態 (ドメインの VillageStatus = code/name)。表示名や募集中判定は画面側で code/name から導出する */
    val status: VillageStatus,
    /** 参加人数 */
    val participantCount: Int,
    /** 見学人数 */
    val spectatorCount: Int,
    /** 設定のうち一覧表示に必要な最小限 (定員・タグ) */
    val setting: Setting,
) {
    /** 一覧表示に必要な村設定の抜粋。 */
    data class Setting(
        /** 最小人数 */
        val personMin: Int,
        /** 最大人数 (定員) */
        val personMax: Int,
        /** 村タグ (ドメインの VillageTag = code/name の生データ)。絞り込み・色付けは画面側 */
        val tags: List<VillageTag>,
    )

    constructor(village: Village) : this(
        id = village.id,
        name = village.name,
        status = village.status,
        participantCount = village.participants.count,
        spectatorCount = village.spectators.count,
        setting =
            Setting(
                personMin = village.setting.personMin,
                personMax = village.setting.personMax,
                tags = village.setting.tags.list,
            ),
    )
}
