package com.ort.app.domain.model.message

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef

data class MessageQuery(
    val village: Village,
    val day: Int,
    val pageSize: Int?,
    val pageNum: Int?,
    val onlyToMe: Boolean,
    val requestTypes: List<MessageType>,
    val participantIds: List<Int>,
    val keywords: String?,
    val isPaging: Boolean,
    val isDispLatest: Boolean,
    // 後からセットする
    var messageTypeList: List<MessageType> = emptyList(),
    var includeMonologue: Boolean = false,
    var includeSecret: Boolean = false,
    var includePrivateAbility: Boolean = false
) {
    fun setAvailable(
        availableMessageTypes: List<MessageType>,
        myself: VillageParticipant?,
        village: Village
    ) {
        var requestMessageTypes =
            if (requestTypes.isNotEmpty()) requestTypes
            else CDef.MessageType.listAll().map { it.toModel() }
        // 進行中で梟の場合、いずれかの梟が視認可能な発言が含まれていたら全部にする
        if (village.status.isProgress() && myself?.skill?.toCdef() == CDef.Skill.梟 &&
            requestMessageTypes.any { MessageType.owlViewableSayTypeList.contains(it.toCdef()) }
        ) {
            requestMessageTypes =
                (requestMessageTypes + MessageType.owlViewableSayTypeList.map { it.toModel() }).distinctBy { it.code }
        }
        messageTypeList = requestMessageTypes.filter { req -> availableMessageTypes.any { it.code == req.code } }
        includeMonologue = isIncludeMonologue(myself)
        includeSecret = isIncludeSecret(myself)
        includePrivateAbility = isIncludePrivateAbility(myself)
    }

    private fun isIncludeMonologue(myself: VillageParticipant?): Boolean {
        // 既に独り言が取得対象になっていたら不要
        if (messageTypeList.any { it.toCdef() == CDef.MessageType.独り言 }) return false
        // 自分が取得対象になっていなければ不要
        myself ?: return false
        if (participantIds.isNotEmpty() && !participantIds.contains(myself.id)) return false
        // 求めていなければ不要
        if (requestTypes.isNotEmpty() && requestTypes.none { it.toCdef() == CDef.MessageType.独り言 }) return false

        return true
    }

    private fun isIncludeSecret(myself: VillageParticipant?): Boolean {
        // 既に秘話が取得対象になっていたら不要
        if (messageTypeList.any { it.toCdef() == CDef.MessageType.秘話 }) return false
        // 自分が取得対象になっていなければ不要
        myself ?: return false
        if (participantIds.isNotEmpty() && !participantIds.contains(myself.id)) return false
        // 求めていなければ不要
        if (requestTypes.isNotEmpty() && requestTypes.none { it.toCdef() == CDef.MessageType.秘話 }) return false

        return true
    }

    private fun isIncludePrivateAbility(myself: VillageParticipant?): Boolean {
        myself ?: return false
        // 求めていなければ不要
        if (requestTypes.isNotEmpty() && requestTypes.none { it.toCdef() == CDef.MessageType.非公開システムメッセージ }) return false

        return true
    }
}