package com.ort.app.api.village.response

import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.village.participant.VillageParticipant
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 参加者本人の状態 (ログインユーザー固有の capability)。認証必須 API でのみ返す。
 * どの操作 UI を表示してよいかのフラグを担い、各操作の入力候補 (対象リスト等) は
 * その操作の API を実装する際に追加する。
 *
 * ネスト DTO はドメインの situation と単純名が衝突しないよう `@Schema` で命名する
 * (衝突すると SpringDoc がスキーマ定義を上書きしてしまう)。
 */
data class ParticipantSituationView(
    /** 参加している場合の自分自身 (未参加は null) */
    val myself: MyselfView?,
    val participate: ParticipateView,
    val skillRequest: SkillRequestView,
    val commit: CommitView,
    val say: SayView,
    val rp: RpView,
    val ability: AbilityView,
    val vote: VoteView,
    val admin: AdminView,
    val creator: CreatorView,
) {
    constructor(situation: ParticipantSituation) : this(
        myself = situation.participate.myself?.let { MyselfView(it) },
        participate = ParticipateView(situation),
        skillRequest = SkillRequestView(isAvailableSkillRequest = situation.skillRequest.isAvailableSkillRequest),
        commit =
            CommitView(
                isAvailableCommit = situation.commit.isAvailableCommit,
                isCommitting = situation.commit.isCommitting,
            ),
        say = SayView(isAvailableSay = situation.say.isAvailableSay),
        rp =
            RpView(
                isAvailableChangeName = situation.rp.isAvailableChangeName,
                isAvailableMemo = situation.rp.isAvailableMemo,
                canAddImage = situation.rp.canAddImage,
            ),
        ability = AbilityView(canUseAbility = situation.ability.canUseAbility),
        vote = VoteView(canVote = situation.vote.canVote),
        admin = AdminView(isAdmin = situation.admin.isAdmin),
        creator = CreatorView(situation),
    )

    @Schema(name = "ParticipantSituationViewMyself")
    data class MyselfView(
        val id: Int,
        val charaId: Int,
        /** 部屋番号付きの表示名 */
        val name: String,
        val shortName: String,
        val isDead: Boolean,
        val isSpectator: Boolean,
        /** Discord 通知キーワード (スペース区切り、未設定は null)。発言抽出のショートカットが使う */
        val notificationKeyword: String?,
    ) {
        constructor(myself: VillageParticipant) : this(
            id = myself.id,
            charaId = myself.charaId,
            name = myself.name(),
            shortName = myself.shortName(),
            isDead = myself.dead.isDead,
            isSpectator = myself.isSpectator,
            notificationKeyword =
                myself.notification
                    ?.message
                    ?.keywords
                    ?.takeIf { it.isNotEmpty() }
                    ?.joinToString(separator = " "),
        )
    }

    @Schema(name = "ParticipantSituationViewParticipate")
    data class ParticipateView(
        val isParticipating: Boolean,
        val isAvailableParticipate: Boolean,
        val isAvailableSpectate: Boolean,
        val isAvailableSwitchParticipate: Boolean,
        val isAvailableLeave: Boolean,
    ) {
        constructor(situation: ParticipantSituation) : this(
            isParticipating = situation.participate.isParticipating,
            isAvailableParticipate = situation.participate.isAvailableParticipate,
            isAvailableSpectate = situation.participate.isAvailableSpectate,
            isAvailableSwitchParticipate = situation.participate.isAvailableSwitchParticipate,
            isAvailableLeave = situation.participate.isAvailableLeave,
        )
    }

    @Schema(name = "ParticipantSituationViewSkillRequest")
    data class SkillRequestView(
        val isAvailableSkillRequest: Boolean,
    )

    @Schema(name = "ParticipantSituationViewCommit")
    data class CommitView(
        val isAvailableCommit: Boolean,
        val isCommitting: Boolean,
    )

    @Schema(name = "ParticipantSituationViewSay")
    data class SayView(
        val isAvailableSay: Boolean,
    )

    @Schema(name = "ParticipantSituationViewRp")
    data class RpView(
        val isAvailableChangeName: Boolean,
        val isAvailableMemo: Boolean,
        val canAddImage: Boolean,
    )

    @Schema(name = "ParticipantSituationViewAbility")
    data class AbilityView(
        val canUseAbility: Boolean,
    )

    @Schema(name = "ParticipantSituationViewVote")
    data class VoteView(
        val canVote: Boolean,
    )

    @Schema(name = "ParticipantSituationViewAdmin")
    data class AdminView(
        val isAdmin: Boolean,
    )

    @Schema(name = "ParticipantSituationViewCreator")
    data class CreatorView(
        val isCreator: Boolean,
        val isAvailableCreatorSay: Boolean,
        val isAvailableCancelVillage: Boolean,
        val isAvailableKick: Boolean,
        val isAvailableModifySetting: Boolean,
        val isAvailableExtendEpilogue: Boolean,
    ) {
        constructor(situation: ParticipantSituation) : this(
            isCreator = situation.creator.isCreator,
            isAvailableCreatorSay = situation.creator.isAvailableCreatorSay,
            isAvailableCancelVillage = situation.creator.isAvailableCancelVillage,
            isAvailableKick = situation.creator.isAvailableKick,
            isAvailableModifySetting = situation.creator.isAvailableModifySetting,
            isAvailableExtendEpilogue = situation.creator.isAvailableExtendEpilogue,
        )
    }
}
