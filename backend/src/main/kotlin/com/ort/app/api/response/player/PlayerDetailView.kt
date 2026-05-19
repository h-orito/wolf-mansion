package com.ort.app.api.response.player

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.player.ParticipateVillage
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRecords
import com.ort.app.domain.model.village.participant.VillageParticipant
import io.swagger.v3.oas.annotations.media.Schema

/**
 * プレイヤー詳細 (プロフィール + 戦績 + 参加履歴)。
 *
 * 旧 `/user/{userName}` (Thymeleaf) の置き換え。
 *
 * - 認証不要 (誰でも他人の戦績を閲覧できる)
 * - `isSelf=true` のときフロント側で Twitter / 自己紹介 / パスワード変更フォームを出す
 */
@Schema(description = "プレイヤー詳細 (戦績 + 参加履歴)")
data class PlayerDetailView(
    @field:Schema(description = "プレイヤー名")
    val name: String,
    @field:Schema(description = "Twitter ユーザ名 (未設定なら null)", nullable = true)
    val twitterUserName: String?,
    @field:Schema(description = "自己紹介 (未設定なら null)", nullable = true)
    val introduction: String?,
    @field:Schema(description = "閲覧者自身のページか")
    val isSelf: Boolean,
    @field:Schema(description = "総合戦績")
    val wholeStats: RecordView,
    @field:Schema(description = "陣営別戦績")
    val campStatsList: List<CampStatsView>,
    @field:Schema(description = "役職別戦績 (参加実績のあるもののみ)")
    val skillStatsList: List<SkillStatsView>,
    @field:Schema(description = "参加村一覧 (新着順)")
    val participateVillageList: List<ParticipateVillageView>,
    @field:Schema(description = "見学村一覧 (新着順)")
    val spectateVillageList: List<ParticipateVillageView>,
) {

    @Schema(description = "戦績サマリ")
    data class RecordView(
        @field:Schema(description = "参加数")
        val participateNum: Int,
        @field:Schema(description = "勝利数")
        val winNum: Int,
        @field:Schema(description = "勝率 (0.0 〜 1.0)")
        val winRate: Float,
    )

    @Schema(description = "陣営別戦績")
    data class CampStatsView(
        @field:Schema(description = "陣営名")
        val campName: String,
        @field:Schema(description = "戦績")
        val stats: RecordView,
    )

    @Schema(description = "役職別戦績")
    data class SkillStatsView(
        @field:Schema(description = "役職名")
        val skillName: String,
        @field:Schema(description = "戦績")
        val stats: RecordView,
    )

    @Schema(description = "参加村")
    data class ParticipateVillageView(
        @field:Schema(description = "村 ID")
        val villageId: Int,
        @field:Schema(description = "村名")
        val villageName: String,
        @field:Schema(description = "キャラ名")
        val characterName: String,
        @field:Schema(description = "キャラ画像 URL")
        val characterImgUrl: String,
        @field:Schema(description = "キャラ画像横幅 (px)")
        val characterImgWidth: Int,
        @field:Schema(description = "キャラ画像縦幅 (px)")
        val characterImgHeight: Int,
        @field:Schema(description = "役職名 (未確定なら空文字)")
        val skillName: String,
        @field:Schema(description = "生死表示 (例: 生存 / 1d 処刑死。見学は空文字)")
        val liveStatus: String,
        @field:Schema(description = "陣営名 (未確定なら空文字)")
        val campName: String,
        @field:Schema(description = "勝敗 (勝利 / 敗北 / 未確定なら空文字)")
        val winStatus: String,
    )

    companion object {
        fun of(
            player: Player,
            records: PlayerRecords,
            charas: Charas,
            originalCharas: Charas,
            isSelf: Boolean,
        ): PlayerDetailView {
            val (participates, spectates) = records.participateVillageList.partition { !it.participant.isSpectator }
            return PlayerDetailView(
                name = player.name,
                twitterUserName = player.twitterUserName,
                introduction = player.introduction,
                isSelf = isSelf,
                wholeStats = toRecordView(records.wholeRecord.participateCount, records.wholeRecord.winCount, records.wholeRecord.winRate),
                campStatsList = records.campRecordList.map {
                    CampStatsView(
                        campName = it.camp.name,
                        stats = toRecordView(it.record.participateCount, it.record.winCount, it.record.winRate),
                    )
                },
                skillStatsList = records.skillRecordList
                    .filter { it.record.participateCount > 0 }
                    .map {
                        SkillStatsView(
                            skillName = it.skill.name,
                            stats = toRecordView(it.record.participateCount, it.record.winCount, it.record.winRate),
                        )
                    },
                participateVillageList = participates.map { toParticipateVillageView(it, charas, originalCharas) },
                spectateVillageList = spectates.map { toParticipateVillageView(it, charas, originalCharas) },
            )
        }

        private fun toRecordView(participate: Int, win: Int, winRate: Float): RecordView =
            RecordView(participateNum = participate, winNum = win, winRate = winRate)

        private fun toParticipateVillageView(
            pv: ParticipateVillage,
            charas: Charas,
            originalCharas: Charas,
        ): ParticipateVillageView {
            // 古い村ではキャラ画像 / サイズ情報が削除されている可能性があるため null 許容にし、
            // 不一致時は表示用フォールバック ("不明" + 空 URL + 0px) を返してリスト全体の 500 を防ぐ。
            val chara = lookupChara(pv, charas, originalCharas)
            return ParticipateVillageView(
                villageId = pv.village.id,
                villageName = pv.village.name,
                characterName = pv.participant.charaName.name,
                characterImgUrl = chara?.defaultImage()?.url.orEmpty(),
                characterImgWidth = chara?.size?.width ?: 0,
                characterImgHeight = chara?.size?.height ?: 0,
                skillName = pv.participant.skill?.name.orEmpty(),
                liveStatus = liveStatusOf(pv.participant).orEmpty(),
                campName = pv.participant.camp?.name.orEmpty(),
                winStatus = pv.participant.isWin?.let { if (it) "勝利" else "敗北" }.orEmpty(),
            )
        }

        private fun lookupChara(pv: ParticipateVillage, charas: Charas, originalCharas: Charas): Chara? {
            val charaId = pv.participant.charaId
            val pool = if (pv.village.setting.chara.isOriginalCharachip) originalCharas else charas
            return pool.list.firstOrNull { it.id == charaId }
        }

        private fun liveStatusOf(participant: VillageParticipant): String? {
            if (participant.isSpectator) return null
            if (!participant.dead.isDead) return "生存"
            // isDead=true なら deadDay / reason は必ず set される (ドメイン制約) が、データ不整合への防御。
            val deadDay = participant.dead.deadDay ?: return "不明"
            val reason = participant.dead.reason?.name ?: return "${deadDay}d 不明"
            return if (reason.endsWith("死")) "${deadDay}d $reason"
            else "${deadDay}d ${reason}死"
        }
    }
}
