package com.ort.app.api.village.response

import com.ort.app.api.view.village.VillageParticipantView
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import com.ort.app.domain.model.situation.participant.ParticipantAdminSituation
import com.ort.app.domain.model.situation.participant.ParticipantCommitSituation
import com.ort.app.domain.model.situation.participant.ParticipantCreatorSituation
import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import com.ort.app.domain.model.situation.participant.ParticipantSayMessageTypeSituation
import com.ort.app.domain.model.situation.participant.ParticipantSayRestrictSituation
import com.ort.app.domain.model.situation.participant.ParticipantSkillRequestSituation
import com.ort.app.domain.model.village.Village
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 参加者本人の状態 (ログインユーザー固有の capability)。認証必須 API でのみ返す。
 *
 * ドメインモデルをそのまま返すのが原則。VillageParticipant を含むフィールドのみ
 * charaId に変換する薄い View で包む。参加者の名前・画像解決はフロントエンドが
 * VillageDetailView.participants を使って行う。
 */
data class ParticipantSituationView(
    /** 参加している場合の自分自身 (未参加は null) */
    val myself: VillageParticipantView?,
    val participate: ParticipateView,
    val skillRequest: ParticipantSkillRequestSituation,
    val commit: ParticipantCommitSituation,
    val say: SayView,
    val rp: ParticipantRpSituation,
    val ability: AbilityView,
    val vote: VoteView,
    val admin: ParticipantAdminSituation,
    val creator: ParticipantCreatorSituation,
) {
    constructor(situation: ParticipantSituation, village: Village, charachips: Charachips) : this(
        myself =
            situation.participate.myself?.let {
                VillageParticipantView(it, charachips.chara(it.charaId), shouldHidePrivate = false, includeNotification = true)
            },
        participate = ParticipateView(situation),
        skillRequest = situation.skillRequest,
        commit = situation.commit,
        say = SayView(situation.say),
        rp = situation.rp,
        ability = AbilityView(situation.ability, village),
        vote = VoteView(situation.vote),
        admin = situation.admin,
        creator = situation.creator,
    )

    @Schema(name = "ParticipantSituationViewParticipate")
    data class ParticipateView(
        val isParticipating: Boolean,
        val isAvailableParticipate: Boolean,
        val isAvailableSpectate: Boolean,
        val isAvailableSwitchParticipate: Boolean,
        val isAvailableLeave: Boolean,
        val selectableCharachipList: List<Charachip>,
        val selectableCharaList: List<Chara>,
    ) {
        constructor(situation: ParticipantSituation) : this(
            isParticipating = situation.participate.isParticipating,
            isAvailableParticipate = situation.participate.isAvailableParticipate,
            isAvailableSpectate = situation.participate.isAvailableSpectate,
            isAvailableSwitchParticipate = situation.participate.isAvailableSwitchParticipate,
            isAvailableLeave = situation.participate.isAvailableLeave,
            selectableCharachipList = situation.participate.selectableCharachipList,
            selectableCharaList = situation.participate.selectableCharaList,
        )
    }

    @Schema(name = "ParticipantSituationViewSay")
    data class SayView(
        val isAvailableSay: Boolean,
        val defaultMessageType: MessageType?,
        val selectableMessageTypeList: List<SayMessageTypeView>,
        val selectableCharaImageList: List<CharaImage>,
    ) {
        constructor(say: com.ort.app.domain.model.situation.participant.ParticipantSaySituation) : this(
            isAvailableSay = say.isAvailableSay,
            defaultMessageType = say.defaultMessageType,
            selectableMessageTypeList = say.selectableMessageTypeList.map { SayMessageTypeView(it) },
            selectableCharaImageList = say.selectableCharaImageList,
        )
    }

    @Schema(name = "ParticipantSituationViewSayMessageType")
    data class SayMessageTypeView(
        val messageType: MessageType,
        val restrict: ParticipantSayRestrictSituation,
        val targetCharaIds: List<Int>,
    ) {
        constructor(situation: ParticipantSayMessageTypeSituation) : this(
            messageType = situation.messageType,
            restrict = situation.restrict,
            targetCharaIds = situation.targetList.map { it.charaId },
        )
    }

    @Schema(name = "ParticipantSituationViewAbility")
    data class AbilityView(
        val canUseAbility: Boolean,
        val targetFootstepList: List<String>,
        val targetFootstep: String?,
        val footstep: String?,
        val targetingMessage: String?,
        val targetPrefix: String?,
        val targetSuffix: String?,
        val isAvailableNoTarget: Boolean,
        val isTargetingAndFootstep: Boolean,
        val skillHistoryList: List<String>,
        val targetCharaIds: List<Int>,
        val attackerCharaIds: List<Int>,
        val attackerCharaId: Int?,
        val targetCharaId: Int?,
        val wolfCharaIds: List<Int>,
        @get:com.fasterxml.jackson.annotation.JsonProperty("cMadmanCharaIds")
        val cMadmanCharaIds: List<Int>,
        val foxCharaIds: List<Int>,
        val lovers: List<LoverRelation>,
        val masonsCharaIds: List<Int>,
        val listenMasonsCharaIds: List<Int>,
    ) {
        constructor(ability: ParticipantAbilitySituation, village: Village) : this(
            canUseAbility = ability.canUseAbility,
            targetFootstepList = ability.targetFootstepList,
            targetFootstep = ability.targetFootstep,
            footstep = ability.footstep,
            targetingMessage = ability.targetingMessage,
            targetPrefix = ability.targetPrefix,
            targetSuffix = ability.targetSuffix,
            isAvailableNoTarget = ability.isAvailableNoTarget,
            isTargetingAndFootstep = ability.isTargetingAndFootstep,
            skillHistoryList = ability.skillHistoryList,
            targetCharaIds = ability.targetList.map { it.charaId },
            attackerCharaIds = ability.attackerList.map { it.charaId },
            attackerCharaId = ability.attacker?.charaId,
            targetCharaId = ability.target?.charaId,
            wolfCharaIds = ability.wolfList.map { it.charaId },
            cMadmanCharaIds = ability.cMadmanList.map { it.charaId },
            foxCharaIds = ability.foxList.map { it.charaId },
            lovers = mapLovers(village, ability.loversList),
            masonsCharaIds = ability.masonsList.map { it.charaId },
            listenMasonsCharaIds = ability.listenMasonsList.map { it.charaId },
        )

        companion object {
            private fun mapLovers(
                village: Village,
                loversList: List<com.ort.app.domain.model.village.participant.VillageParticipant>,
            ): List<LoverRelation> =
                loversList.flatMap { lover ->
                    lover.status.loverIdList.map { targetId ->
                        LoverRelation(
                            fromCharaId = lover.charaId,
                            toCharaId = village.participants.member(targetId).charaId,
                        )
                    }
                }
        }
    }

    @Schema(name = "ParticipantSituationViewLoverRelation")
    data class LoverRelation(
        val fromCharaId: Int,
        val toCharaId: Int,
    )

    @Schema(name = "ParticipantSituationViewVote")
    data class VoteView(
        val canVote: Boolean,
        val targetCharaIds: List<Int>,
        val targetCharaId: Int?,
    ) {
        constructor(vote: com.ort.app.domain.model.situation.participant.ParticipantVoteSituation) : this(
            canVote = vote.canVote,
            targetCharaIds = vote.targetList.map { it.charaId },
            targetCharaId = vote.target?.charaId,
        )
    }
}
