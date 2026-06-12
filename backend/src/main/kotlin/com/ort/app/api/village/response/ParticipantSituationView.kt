package com.ort.app.api.village.response

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.CharaImage
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.situation.participant.ParticipantSayMessageTypeSituation
import com.ort.app.domain.model.situation.participant.ParticipantSayRestrictSituation
import com.ort.app.domain.model.situation.participant.ParticipantSaySituation
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
        skillRequest = SkillRequestView(situation),
        commit =
            CommitView(
                isAvailableCommit = situation.commit.isAvailableCommit,
                isCommitting = situation.commit.isCommitting,
            ),
        say = SayView(situation.say),
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
        /** 入村フォームで選択できるキャラセット (空きキャラのみ) */
        val selectableCharachipList: List<ParticipateCharachipView>,
    ) {
        constructor(situation: ParticipantSituation) : this(
            isParticipating = situation.participate.isParticipating,
            isAvailableParticipate = situation.participate.isAvailableParticipate,
            isAvailableSpectate = situation.participate.isAvailableSpectate,
            isAvailableSwitchParticipate = situation.participate.isAvailableSwitchParticipate,
            isAvailableLeave = situation.participate.isAvailableLeave,
            selectableCharachipList =
                situation.participate.selectableCharachipList.map { charachip ->
                    ParticipateCharachipView(charachip, situation.participate.selectableCharaList)
                },
        )
    }

    @Schema(name = "ParticipantSituationViewParticipateCharachip")
    data class ParticipateCharachipView(
        val id: Int,
        val name: String,
        /** このキャラセットのうち選択できる (未使用の) キャラ */
        val charas: List<ParticipateCharaView>,
    ) {
        constructor(charachip: Charachip, selectableCharas: List<Chara>) : this(
            id = charachip.id,
            name = charachip.name,
            charas =
                charachip.charas.list
                    .filter { chara -> selectableCharas.any { it.id == chara.id } }
                    .map { ParticipateCharaView(it) },
        )
    }

    @Schema(name = "ParticipantSituationViewParticipateChara")
    data class ParticipateCharaView(
        val id: Int,
        val name: String,
        val shortName: String,
        val imageUrl: String,
        val imageWidth: Int,
        val imageHeight: Int,
    ) {
        constructor(chara: Chara) : this(
            id = chara.id,
            name = chara.name,
            shortName = chara.shortName,
            imageUrl = chara.defaultImage().url,
            imageWidth = chara.size.width,
            imageHeight = chara.size.height,
        )
    }

    @Schema(name = "ParticipantSituationViewSkillRequest")
    data class SkillRequestView(
        val isAvailableSkillRequest: Boolean,
        /** 希望できる役職 */
        val selectableSkillList: List<SkillRequestSkillView>,
        /** 現在の第 1 希望 (未参加は null) */
        val requestedSkillCode: String?,
        /** 現在の第 2 希望 (未参加は null) */
        val secondRequestedSkillCode: String?,
    ) {
        constructor(situation: ParticipantSituation) : this(
            isAvailableSkillRequest = situation.skillRequest.isAvailableSkillRequest,
            selectableSkillList =
                situation.skillRequest.selectableSkillList.map {
                    SkillRequestSkillView(code = it.code, name = it.name)
                },
            requestedSkillCode =
                situation.skillRequest.skillRequest
                    ?.first
                    ?.code,
            secondRequestedSkillCode =
                situation.skillRequest.skillRequest
                    ?.second
                    ?.code,
        )
    }

    @Schema(name = "ParticipantSituationViewSkillRequestSkill")
    data class SkillRequestSkillView(
        val code: String,
        val name: String,
    )

    @Schema(name = "ParticipantSituationViewCommit")
    data class CommitView(
        val isAvailableCommit: Boolean,
        val isCommitting: Boolean,
    )

    @Schema(name = "ParticipantSituationViewSay")
    data class SayView(
        val isAvailableSay: Boolean,
        /** 既定で選択する発言種別コード */
        val defaultMessageTypeCode: String?,
        /** 選択できる発言種別 (種別別の制限・秘話の宛先候補付き) */
        val selectableMessageTypeList: List<SayMessageTypeView>,
        /** 選択できる表情 (表示中の差分のみ) */
        val selectableCharaImageList: List<SayCharaImageView>,
    ) {
        constructor(say: ParticipantSaySituation) : this(
            isAvailableSay = say.isAvailableSay,
            defaultMessageTypeCode = say.defaultMessageType?.code,
            selectableMessageTypeList = say.selectableMessageTypeList.map { SayMessageTypeView(it) },
            selectableCharaImageList =
                say.selectableCharaImageList
                    .filter { it.isDisplay }
                    .map { SayCharaImageView(it) },
        )
    }

    @Schema(name = "ParticipantSituationViewSayMessageType")
    data class SayMessageTypeView(
        val messageTypeCode: String,
        val restrict: SayRestrictView,
        /** 宛先の候補 (秘話のみ非空) */
        val targetList: List<SayTargetView>,
    ) {
        constructor(situation: ParticipantSayMessageTypeSituation) : this(
            messageTypeCode = situation.messageType.code,
            restrict = SayRestrictView(situation.restrict),
            targetList = situation.targetList.map { SayTargetView(it) },
        )
    }

    @Schema(name = "ParticipantSituationViewSayRestrict")
    data class SayRestrictView(
        val isRestricted: Boolean,
        val maxCount: Int?,
        val remainingCount: Int?,
        val maxLength: Int,
        val maxLine: Int,
    ) {
        constructor(restrict: ParticipantSayRestrictSituation) : this(
            isRestricted = restrict.isRestricted,
            maxCount = restrict.maxCount,
            remainingCount = restrict.remainingCount,
            maxLength = restrict.maxLength,
            maxLine = restrict.maxLine,
        )
    }

    @Schema(name = "ParticipantSituationViewSayTarget")
    data class SayTargetView(
        val charaId: Int,
        val name: String,
    ) {
        constructor(participant: VillageParticipant) : this(
            charaId = participant.charaId,
            name = participant.name(),
        )
    }

    @Schema(name = "ParticipantSituationViewSayCharaImage")
    data class SayCharaImageView(
        val faceTypeCode: String,
        val faceTypeName: String,
        val url: String,
    ) {
        constructor(image: CharaImage) : this(
            faceTypeCode = image.faceType.code,
            faceTypeName = image.faceType.name,
            url = image.url,
        )
    }

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
