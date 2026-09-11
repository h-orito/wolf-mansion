package com.ort.app.api.view.village

import com.ort.app.api.view.player.PlayerView
import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantName
import com.ort.app.domain.model.village.participant.VillageParticipantNotificationCondition
import com.ort.app.domain.model.village.participant.VillageParticipantStatus
import com.ort.app.domain.model.village.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class VillageParticipantView(
    val id: Int,
    val charaName: VillageParticipantName,
    val chara: Chara,
    val room: Room?,
    val dead: DeadView,
    /** 部屋番号付き表示名 */
    val name: String,
    val shortName: String,
    val isSpectator: Boolean,
    /** 本人以外は null */
    val skill: SkillView?,
    /** 現在の勝敗判定陣営 (恋人化・狐憑依・説得等で変わりうる)。本人以外は null */
    val camp: Camp?,
    /** 状態ラベル (恋絆・狐憑き・狂気など)。エピローグ以降のみ公開、それ以外は null */
    val statuses: List<String>?,
    /** 簡易メモ (参加者一覧に表示される公開情報) */
    val memo: String?,
    /** Discord 通知設定。本人以外は null */
    val notification: VillageParticipantNotificationCondition?,
    /** エピローグ以降のみ公開 */
    val player: PlayerView?,
    /** エピローグ以降のみ公開 */
    val isWin: Boolean?,
    /** プロローグ中のみ公開 */
    val lastAccessDatetime: LocalDateTime?,
) {
    /** 参加者一覧向け（SSR 互換）。非公開フィールドは null */
    constructor(
        participant: VillageParticipant,
        participantIdToChara: Map<Int, Chara>,
    ) : this(
        id = participant.id,
        charaName = participant.charaName,
        chara = participantIdToChara[participant.id]!!,
        room = participant.room,
        dead = DeadView(participant.dead),
        name = participant.name(),
        shortName = participant.shortName(),
        isSpectator = participant.isSpectator,
        skill = null,
        camp = null,
        statuses = null,
        memo = participant.memo,
        notification = null,
        player = null,
        isWin = null,
        lastAccessDatetime = null,
    )

    /**
     * REST API 向け。
     * [shouldHidePrivate] = true で役職・陣営・通知設定をすべて隠す (進行中の公開一覧)。
     * [includeNotification] = true のとき通知設定を含める (本人向け API のみ)。
     */
    constructor(
        participant: VillageParticipant,
        chara: Chara,
        shouldHidePrivate: Boolean,
        includeNotification: Boolean = false,
        player: Player? = null,
        isPrologue: Boolean = false,
    ) : this(
        id = participant.id,
        charaName = participant.charaName,
        chara = chara,
        room = participant.room,
        dead = DeadView(participant.dead),
        name = participant.name(),
        shortName = participant.shortName(),
        isSpectator = participant.isSpectator,
        skill =
            if (shouldHidePrivate || participant.skill == null) {
                null
            } else {
                SkillView(participant.skill)
            },
        camp = if (shouldHidePrivate) null else participant.camp,
        statuses = if (shouldHidePrivate) null else mappingToStatuses(participant.status),
        memo = participant.memo,
        notification = if (includeNotification && !shouldHidePrivate) participant.notification else null,
        player = if (shouldHidePrivate || player == null) null else PlayerView(player),
        isWin = if (shouldHidePrivate) null else participant.isWin,
        lastAccessDatetime = if (isPrologue) participant.lastAccessDatetime else null,
    )

    companion object {
        /** 参加者一覧に表示する状態ラベル (旧 Thymeleaf 画面と同一の集合・順序) */
        private fun mappingToStatuses(status: VillageParticipantStatus): List<String> {
            val list = mutableListOf<String>()
            if (status.hasLover()) list.add("恋絆")
            if (status.isFoxPossessioned()) list.add("狐憑き")
            if (status.isInsaned()) list.add("狂気")
            if (status.isPersuaded()) list.add("信念")
            if (status.isDisrespectful()) list.add("不敬")
            if (status.hasCurseMark) list.add("呪縛符")
            if (status.hasCounterCurseMark) list.add("反呪符")
            return list
        }
    }

    @Schema(name = "VillageParticipantViewSkill")
    data class SkillView(
        val code: String,
        val name: String,
        /** 役職の説明文 (HTML) */
        val description: String,
        /** 役職変化履歴 (割り当て日昇順。転生・変化がなければ初期役職のみ) */
        val histories: List<SkillHistoryView>,
        /** 足音の調査能力を持つか */
        val hasInvestigateAbility: Boolean,
        /** 徘徊能力を持つか */
        val hasDisturbAbility: Boolean,
    ) {
        constructor(skill: Skill) : this(
            code = skill.code,
            name = skill.name,
            description = SkillDescriptions.get(skill.code),
            histories = skill.histories.list.map { SkillHistoryView(day = it.day, code = it.skill.code, name = it.skill.name) },
            hasInvestigateAbility = skill.hasInvestigateAbility(),
            hasDisturbAbility = skill.hasDisturbAbility(),
        )
    }

    @Schema(name = "VillageParticipantViewSkillHistory")
    data class SkillHistoryView(
        /** その役職が割り当てられた日 */
        val day: Int,
        val code: String,
        val name: String,
    )
}
