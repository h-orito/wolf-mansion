package com.ort.app.api.request

import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.allcommon.CDef
import javax.validation.constraints.NotNull

data class VillageGetMessageListForm(
    /** 村ID */
    @NotNull
    val villageId: Int? = null,

    /** 何日目か */
    val day: Int? = null,

    /** 1ページあたりの表示発言数 */
    val pageSize: Int? = null,

    /** 何ページ目か */
    val pageNum: Int? = null,

    /** 自分宛に絞るか */
    val onlyToMe: Boolean? = null,

    /** 種別カンマ区切り */
    val types: String? = null,

    /** 参加者IDカンマ区切り */
    val participantIds: String? = null,

    /** キーワードスペース区切り */
    val keywords: String? = null,

    /** ページングするか */
    val isPaging: Boolean? = null,

    /** 最新を表示するか */
    val isDispLatest: Boolean? = null
) {
    fun toMessageQuery(village: Village) = MessageQuery(
        village = village,
        day = day ?: village.latestDay(),
        pageSize = pageSize,
        pageNum = pageNum,
        onlyToMe = onlyToMe ?: false,
        requestTypes = mappingToTypes(),
        participantIds = mappingToParticipantIds(village),
        keywords = keywords,
        isPaging = isPaging ?: false,
        isDispLatest = isDispLatest ?: false
    )

    private fun mappingToTypes(): List<MessageType> {
        return types?.split(",")?.flatMap { typeMap.getOrDefault(it, emptyList()) } ?: emptyList()
    }

    private fun mappingToParticipantIds(village: Village): List<Int> {
        val ids = if (participantIds.isNullOrEmpty()) emptyList() else participantIds.split(",").map { it.toInt() }
        // 全員選択されている場合は空にすることで全員表示させる
        return if (village.allParticipants().filterNotGone().list.size == ids.size) emptyList()
        else ids
    }

    companion object {
        val typeMap = mapOf(
            CDef.MessageType.通常発言.code() to listOf(CDef.MessageType.通常発言.toModel()),
            CDef.MessageType.村建て発言.code() to listOf(CDef.MessageType.村建て発言.toModel()),
            CDef.MessageType.人狼の囁き.code() to listOf(CDef.MessageType.人狼の囁き.toModel()),
            CDef.MessageType.恋人発言.code() to listOf(CDef.MessageType.恋人発言.toModel()),
            CDef.MessageType.共鳴発言.code() to listOf(CDef.MessageType.共鳴発言.toModel()),
            "GRAVE_SPECTATE_SAY" to listOf(CDef.MessageType.死者の呻き.toModel(), CDef.MessageType.見学発言.toModel()),
            CDef.MessageType.独り言.code() to listOf(CDef.MessageType.独り言.toModel()),
            CDef.MessageType.秘話.code() to listOf(CDef.MessageType.秘話.toModel()),
            CDef.MessageType.アクション.code() to listOf(CDef.MessageType.アクション.toModel()),
            CDef.MessageType.公開システムメッセージ.code() to listOf(
                CDef.MessageType.公開システムメッセージ.toModel(),
                CDef.MessageType.参加者一覧.toModel(),
            ),
            CDef.MessageType.非公開システムメッセージ.code() to listOf(
                CDef.MessageType.非公開システムメッセージ.toModel(),
                CDef.MessageType.白黒占い結果.toModel(),
                CDef.MessageType.役職占い結果.toModel(),
                CDef.MessageType.白黒霊視結果.toModel(),
                CDef.MessageType.役職霊視結果.toModel(),
                CDef.MessageType.検死結果.toModel(),
                CDef.MessageType.襲撃結果.toModel(),
                CDef.MessageType.足音調査結果.toModel(),
                CDef.MessageType.恋人メッセージ.toModel(),
                CDef.MessageType.妖狐メッセージ.toModel()
            )
        )
    }
}