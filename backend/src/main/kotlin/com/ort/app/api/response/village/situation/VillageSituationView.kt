package com.ort.app.api.response.village.situation

import com.ort.app.domain.model.situation.village.VillageFootstepSituation
import com.ort.app.domain.model.situation.village.VillageVoteSituation
import com.ort.app.domain.model.situation.village.VillageWholeSituation
import com.ort.app.domain.model.village.Village
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 旧 Thymeleaf `situation.html` の "状況 / 投票 / 足音" タブ相当のサマリ。
 *
 * - `whole`: 各日の死亡 (突然 / 処刑 / 無惨 / 後追) と復活、能力履歴 (spoiler open 時のみ非空)
 * - `vote`: 参加者 × 各日の投票テーブル (黒箱日は除外済み、`maxVoteCount` は表のカラム数算出に使う)
 * - `dayFootsteps`: 日別足音 (= `VillageFootstepSituation`)。
 *   登録単位リストは `/footsteps` を、日別文字列はこちらを参照する。
 *
 * `day` パラメータで「現在の表示日」を渡し、それより未来の whole / vote 行は backend 側で
 * 切らずにそのまま返す (旧画面は client 側で「現在表示中の day まで」描画する設計)。
 */
@Schema(description = "村の状況サマリ (whole / vote / dayFootsteps)")
data class VillageSituationView(
    @field:Schema(description = "各日の死亡・復活・能力")
    val whole: List<VillageSituationDayView>,
    @field:Schema(description = "投票テーブル")
    val vote: VillageSituationVoteView,
    @field:Schema(description = "日別足音 (進行中は他者足音が空文字 or 隠匿される可能性あり)")
    val dayFootsteps: List<VillageDayFootstepView>,
) {
    constructor(
        village: Village,
        whole: VillageWholeSituation,
        vote: VillageVoteSituation,
        footstep: VillageFootstepSituation,
    ) : this(
        whole = whole.list.map { detail ->
            VillageSituationDayView(
                day = detail.day,
                suddenlyDeath = detail.suddenlyDeath.list.map { it.shortNameWhen(detail.day - 1) },
                executed = detail.executed.list.map { it.shortNameWhen(detail.day - 1) },
                miserable = detail.miserable.list.map { it.shortNameWhen(detail.day - 1) },
                revival = detail.revival.list.map { it.shortNameWhen(detail.day - 1) },
                suicide = detail.suicide.list.map { it.shortNameWhen(detail.day - 1) },
                ability = detail.ability,
            )
        },
        vote = VillageSituationVoteView(village, vote),
        dayFootsteps = footstep.list.map { VillageDayFootstepView(day = it.day, footstep = it.footstep) },
    )
}

@Schema(description = "ある 1 日の死亡・復活・能力サマリ")
data class VillageSituationDayView(
    @field:Schema(description = "何日目か")
    val day: Int,
    @field:Schema(description = "突然死した参加者の表示名 (= 部屋番号+略称) 一覧")
    val suddenlyDeath: List<String>,
    @field:Schema(description = "処刑された参加者の表示名一覧")
    val executed: List<String>,
    @field:Schema(description = "無惨死 (襲撃 / 呪殺 / 罠死 / 爆死 / 雑魚 等) の表示名一覧")
    val miserable: List<String>,
    @field:Schema(description = "復活した参加者の表示名一覧")
    val revival: List<String>,
    @field:Schema(description = "後追した参加者の表示名一覧")
    val suicide: List<String>,
    @field:Schema(description = "能力履歴 (spoiler 非公開時は空配列)")
    val ability: List<String>,
)

@Schema(description = "投票テーブル")
data class VillageSituationVoteView(
    @field:Schema(description = "参加者ごとの行 (退村済 / 見学 / dummy も含む。表側で除外しない)")
    val list: List<VillageSituationVoteMemberView>,
    @field:Schema(
        description = "投票の最大カラム数 (= 表示すべき日数)。" +
                "domain の `convertToVillageSituation` が黒箱日除外後の vote を返すため、" +
                "ここでは行ごとの vote 数の最大を取る (= 旧画面 `VillageVoteContent.maxVoteCount` 互換)。" +
                "現状 frontend は `list[].voteList[].day` の最大値から自前で列数を決めるため未使用だが、" +
                "外部連携 / 別 UI で日列数を即座に知りたいケースのために残置する",
    )
    val maxVoteCount: Int,
) {
    constructor(village: Village, situation: VillageVoteSituation) : this(
        list = situation.list.map { memberVotes ->
            VillageSituationVoteMemberView(
                // 投票者の行ヘッダは複数日の投票をまとめるため、特定の日に固定できない。
                // 旧 Thymeleaf 実装 (`VillageMemberVoteContent`) も `shortName()` (= 最新部屋番号)
                // を使っていたため踏襲。target 側はセルごとに `shortNameWhen(vote.day)`
                // (投票日時点の部屋番号) を使うので、見た目上は「投票日の target」と
                // 「現在の投票者」が混在するが、これは旧画面と同じ挙動。
                participantId = memberVotes.participant.id,
                charaShortName = memberVotes.participant.shortName(),
                voteList = memberVotes.voteList.map { vote ->
                    VillageSituationVoteCellView(
                        day = vote.day,
                        targetCharaShortName = village.participants.chara(vote.targetCharaId).shortNameWhen(vote.day),
                    )
                },
            )
        },
        maxVoteCount = situation.list.maxOfOrNull { it.voteList.size } ?: 0,
    )
}

@Schema(description = "投票テーブルの 1 行 (1 参加者)")
data class VillageSituationVoteMemberView(
    @field:Schema(description = "参加者 ID")
    val participantId: Int,
    @field:Schema(description = "表示名 (= 部屋番号+略称)")
    val charaShortName: String,
    @field:Schema(description = "投票履歴 (黒箱日は domain 側で除外済み)")
    val voteList: List<VillageSituationVoteCellView>,
)

@Schema(description = "投票テーブルの 1 セル (1 日の投票先)")
data class VillageSituationVoteCellView(
    @field:Schema(description = "投票が行われた日")
    val day: Int,
    @field:Schema(description = "投票先の表示名")
    val targetCharaShortName: String,
)

@Schema(description = "日別足音 (旧画面の足音タブ用)")
data class VillageDayFootstepView(
    @field:Schema(description = "何日目か")
    val day: Int,
    @field:Schema(description = "足音 (改行区切りの整形済み文字列)")
    val footstep: String,
)
