package com.ort.app.api.response.village

import com.ort.app.api.response.skill.SkillView
import com.ort.app.domain.model.village.Village
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "村詳細 (read-only)")
data class VillageView(
    @field:Schema(description = "村 ID")
    val id: Int,
    @field:Schema(description = "村番号 (4 桁ゼロ埋め)")
    val number: String,
    @field:Schema(description = "村名")
    val name: String,
    @field:Schema(description = "ステータスコード")
    val statusCode: String,
    @field:Schema(description = "ステータス表示名")
    val statusName: String,
    @field:Schema(description = "村建てプレイヤー名")
    val createPlayerName: String,
    @field:Schema(description = "募集開始日時")
    val createDatetime: LocalDateTime,
    @field:Schema(description = "現在時刻情報")
    val time: VillageTimeView,
    @field:Schema(description = "エピローグの日 (未到達なら null)")
    val epilogueDay: Int?,
    @field:Schema(description = "勝利陣営名 (エピローグ以降のみ、それ以前は null)")
    val winCampName: String?,
    @field:Schema(description = "部屋の横サイズ (プロローグ中は null)")
    val roomWidth: Int?,
    @field:Schema(description = "村設定")
    val settings: VillageSettingsView,
    @field:Schema(description = "日付一覧 (昇順)")
    val days: VillageDaysView,
    @field:Schema(description = "参加者一覧 (隠蔽済み)")
    val participants: VillageParticipantsView,
    @field:Schema(description = "村建てした本人がこの API を呼んでいるか")
    val isCreator: Boolean,
    @field:Schema(description = "閲覧者が参加 (見学含む) しているか")
    val isParticipating: Boolean,
    @field:Schema(
        description = "希望役職に指定できる役職一覧。" +
                "isSkillRequestAvailable=false (希望役職指定不可) の村では空リスト、" +
                "それ以外なら村ステータスによらず常に同じ非空のリストを返す。" +
                "実際の希望変更操作はプロローグ中のみ受け付けられる点に注意。"
    )
    val requestableSkills: List<SkillView>,
) {
    constructor(
        village: Village,
        participants: VillageParticipantsView,
        isCreator: Boolean,
        isParticipating: Boolean,
    ) : this(
        id = village.id,
        number = village.id.toString().padStart(4, '0'),
        name = village.name,
        statusCode = village.status.code,
        statusName = village.status.name,
        createPlayerName = village.createPlayerName,
        createDatetime = village.createDatetime,
        time = VillageTimeView(village),
        epilogueDay = village.epilogueDay,
        winCampName = village.winCamp?.name,
        roomWidth = village.roomSize?.width,
        settings = VillageSettingsView(village),
        days = VillageDaysView(village.days),
        participants = participants,
        isCreator = isCreator,
        isParticipating = isParticipating,
        requestableSkills = if (village.setting.rule.isPossibleSkillRequest) {
            village.allRequestableSkillList().map { SkillView(it) }
        } else emptyList(),
    )
}
