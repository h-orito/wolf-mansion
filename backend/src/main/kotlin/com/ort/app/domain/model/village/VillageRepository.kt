package com.ort.app.domain.model.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.participant.VillageParticipant
import java.time.LocalDateTime

interface VillageRepository {
    // ----------------------------------------------------
    // village

    fun findLatestVillageId(statusList: List<VillageStatus>): Int

    fun findVillages(query: VillageQuery): Villages

    fun findVillage(
        id: Int,
        excludeGone: Boolean,
    ): Village?

    /**
     * 公開範囲の判定に使う村 (村一覧と同じ簡易な形)。村・村設定・日付と、退村者を除く参加者・見学者を読む。
     * 参加者・見学者は簡易版で、部屋・ステータス (恋人・狐憑きなど)・各種履歴・IP アドレス・通知設定は持たない。
     * 編成・発言制限・キャラチップ・タグは読まない。
     */
    fun findSimpleVillage(id: Int): Village?

    fun updateStatus(
        id: Int,
        status: VillageStatus,
    )

    fun extendDay(
        id: Int,
        day: Int,
        datetime: LocalDateTime,
    )

    fun shortenDay(
        id: Int,
        day: Int,
        datetime: LocalDateTime,
    )

    fun updateSetting(village: Village)

    fun updateDaychangeDifference(
        current: Village,
        changed: Village,
    )

    fun updateDummyCharaId(
        id: Int,
        charaId: Int,
    )

    // ----------------------------------------------------
    // participant

    fun findVillageParticipant(
        id: Int,
        excludeGone: Boolean,
    ): VillageParticipant?

    fun findVillageParticipant(
        villageId: Int,
        userName: String,
        excludeGone: Boolean,
    ): VillageParticipant?

    /**
     * 公開範囲の判定に使う参加者 (退村者は除く)。参加者本人とステータス (恋人・狐憑きなど) だけを読み、
     * 死亡・部屋・役職の履歴、IP アドレス、通知設定は読まない。
     */
    fun findVillageParticipantWithoutHistories(
        villageId: Int,
        userName: String,
    ): VillageParticipant?

    fun registerVillage(paramVillage: Village): Village

    fun participate(
        villageId: Int,
        playerId: Int,
        chara: Chara,
        charaName: String,
        charaShortName: String,
        firstRequestSkill: Skill,
        secondRequestSkill: Skill,
        spectator: Boolean,
    ): VillageParticipant

    fun switchParticipate(
        villageId: Int,
        participantId: Int,
        isSpectator: Boolean,
    ): VillageParticipant

    fun leave(participant: VillageParticipant)

    fun changeParticipantName(
        participant: VillageParticipant,
        name: String,
        shortName: String,
    )

    fun changeRequestSkill(
        participant: VillageParticipant,
        first: Skill,
        second: Skill,
    )

    fun changeMemo(
        participant: VillageParticipant,
        memo: String,
    )

    fun updateLastAccessDatetime(participant: VillageParticipant)

    fun addIpAddress(
        participant: VillageParticipant,
        ipAddress: String,
    )

    fun registerNotificationSetting(participant: VillageParticipant)
}
