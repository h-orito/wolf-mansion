package com.ort.app.api.response.myself

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.situation.participant.ParticipantSayMessageTypeSituation
import com.ort.app.domain.model.situation.participant.ParticipantSaySituation
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 自分が今出せる発言の種別・表情差分・秘話宛先・制限情報をまとめて返す。
 *
 * 旧 Thymeleaf 版 `VillageFormContent.VillageSayFormContent` に対応 (発言フォーム表示用)。
 * `availableMessageTypes` には各種別ごとの残回数 / 文字数制限を埋めており、フロントは
 * 種別切替時にここから引いて UI を更新する。
 *
 * `availableMessageTypes` が空のとき (= 発言不可) は SayForm を出さない判定にも使える。
 */
@Schema(description = "自分視点の発言フォーム情報 (種別 / 表情差分 / 秘話宛先 / 制限)")
data class MyselfSayView(
    @field:Schema(description = "発言可能か (= availableMessageTypes が非空 + 村のステータスが発言可能)")
    val isAvailableSay: Boolean,
    @field:Schema(description = "選択可能な発言種別の一覧 (順序は domain 既定: 恋人/念話/囁き/共鳴/独り言/通常/呻き/見学/秘話/アクション)")
    val availableMessageTypes: List<MyselfSayMessageTypeView>,
    @field:Schema(description = "デフォルト選択する発言種別コード (発言不可なら null)")
    val defaultMessageTypeCode: String?,
    @field:Schema(description = "選択可能な表情差分 (発言時のキャラ画像切替用)")
    val selectableFaceTypes: List<MyselfFaceTypeView>,
    @field:Schema(description = "秘話の宛先候補。秘話が選べない村ステータス / 役職では空配列")
    val secretSayTargets: List<MyselfSecretSayTargetView>,
) {
    constructor(situation: ParticipantSaySituation, charachips: Charachips) : this(
        isAvailableSay = situation.isAvailableSay,
        availableMessageTypes = situation.selectableMessageTypeList.map { MyselfSayMessageTypeView(it) },
        defaultMessageTypeCode = situation.defaultMessageType?.code,
        selectableFaceTypes = situation.selectableCharaImageList.map {
            MyselfFaceTypeView(
                code = it.faceType.code,
                name = it.faceType.name,
                url = it.url,
                isDisplay = it.isDisplay,
            )
        },
        secretSayTargets = situation.selectableMessageTypeList
            .firstOrNull { it.messageType.code == CDef.MessageType.秘話.code() }
            ?.targetList
            ?.map { MyselfSecretSayTargetView(it, charachips) }
            ?: emptyList(),
    )
}

@Schema(description = "発言種別 1 件 (制限情報込み)")
data class MyselfSayMessageTypeView(
    @field:Schema(description = "種別コード (CDef.MessageType)")
    val code: String,
    @field:Schema(description = "種別表示名")
    val name: String,
    @field:Schema(description = "発言制限がかかっているか")
    val isRestricted: Boolean,
    @field:Schema(description = "1 日あたりの最大回数 (制限なしなら null)")
    val maxCount: Int?,
    @field:Schema(description = "残り回数 (制限なしなら null)")
    val remainingCount: Int?,
    @field:Schema(description = "1 発言あたりの最大文字数")
    val maxLength: Int,
    @field:Schema(description = "1 発言あたりの最大行数")
    val maxLine: Int,
) {
    constructor(situation: ParticipantSayMessageTypeSituation) : this(
        code = situation.messageType.code,
        name = situation.messageType.name,
        isRestricted = situation.restrict.isRestricted,
        maxCount = situation.restrict.maxCount,
        remainingCount = situation.restrict.remainingCount,
        maxLength = situation.restrict.maxLength,
        maxLine = situation.restrict.maxLine,
    )
}

@Schema(description = "秘話の宛先候補 1 件")
data class MyselfSecretSayTargetView(
    @field:Schema(description = "キャラ ID")
    val charaId: Int,
    @field:Schema(description = "表示名 ([部屋番号略称] 名前)")
    val name: String,
    @field:Schema(description = "キャラ画像 URL (デフォルト顔)")
    val imageUrl: String,
) {
    constructor(participant: VillageParticipant, charachips: Charachips) : this(
        charaId = participant.charaId,
        name = participant.name(),
        imageUrl = charachips.chara(participant.charaId).defaultImage().url,
    )
}
