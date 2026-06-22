package com.ort.app.api.view.village

import com.ort.app.domain.model.camp.Camp
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipantName
import com.ort.app.domain.model.village.participant.VillageParticipantNotificationCondition
import com.ort.app.domain.model.village.room.Room
import io.swagger.v3.oas.annotations.media.Schema

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
    /** Discord 通知設定。本人以外は null */
    val notification: VillageParticipantNotificationCondition?,
) {
    /** 参加者一覧向け。非公開フィールドは null */
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
        notification = null,
    )

    /** 本人向け。shouldHidePrivate = false のとき役職・通知設定を返す */
    constructor(
        participant: VillageParticipant,
        chara: Chara,
        shouldHidePrivate: Boolean,
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
        notification =
            if (shouldHidePrivate) {
                null
            } else {
                participant.notification
            },
    )

    @Schema(name = "VillageParticipantViewSkill")
    data class SkillView(
        val code: String,
        val name: String,
        /** 役職の説明文 (HTML) */
        val description: String,
        /** 足音の調査能力を持つか */
        val hasInvestigateAbility: Boolean,
        /** 徘徊能力を持つか */
        val hasDisturbAbility: Boolean,
    ) {
        constructor(skill: Skill) : this(
            code = skill.code,
            name = skill.name,
            description = SkillDescriptions.get(skill.code),
            hasInvestigateAbility = skill.hasInvestigateAbility(),
            hasDisturbAbility = skill.hasDisturbAbility(),
        )
    }
}
