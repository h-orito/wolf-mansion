package com.ort.app.domain.model.message

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.dbflute.allcommon.CDef

data class MessageQuery(
    val village: Village,
    val day: Int,
    val pageSize: Int?,
    val pageNum: Int?,
    val fromParticipantIds: List<Int>,
    val toParticipantIds: List<Int>,
    val requestTypes: List<MessageType>,
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

        // 進行中で梟の場合、いずれかの梟が視認可能な発言種別が含まれていたら
        if (village.status.isProgress() &&
            myself?.skill?.toCdef() == CDef.Skill.梟 &&
            requestMessageTypes.any { MessageType.owlViewableSayTypeList.contains(it.toCdef()) }
        ) {
            requestMessageTypes = if (fromParticipantIds.isNotEmpty()) {
                // 人で絞っている場合、梟が視認可能な発言について誰が発言したかわかってしまうため、一切見えなくする
                requestMessageTypes.filterNot { MessageType.owlViewableSayTypeList.contains(it.toCdef()) }
            } else {
                // 人で絞っていない場合は見られて良いが、どの発言種別だったかわかってしまうため、視認可能な発言種別全てを見えるようにする
                (requestMessageTypes + MessageType.owlViewableSayTypeList.map { it.toModel() }).distinctBy { it.code }
            }
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
        if (fromParticipantIds.isNotEmpty() && !fromParticipantIds.contains(myself.id)) return false
        // 求めていなければ不要
        if (requestTypes.isNotEmpty() && requestTypes.none { it.toCdef() == CDef.MessageType.独り言 }) return false

        return true
    }

    private fun isIncludeSecret(myself: VillageParticipant?): Boolean {
        // 既に秘話が取得対象になっていたら不要
        if (messageTypeList.any { it.toCdef() == CDef.MessageType.秘話 }) return false
        // 自分が取得対象になっていなければ不要
        myself ?: return false
        val containFromMyself = fromParticipantIds.isEmpty() || fromParticipantIds.contains(myself.id)
        val containToMyself = toParticipantIds.isEmpty() || toParticipantIds.contains(myself.id)
        if (!containFromMyself && !containToMyself) return false
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