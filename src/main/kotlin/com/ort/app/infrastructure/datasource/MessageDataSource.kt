package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.message.*
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.cbean.MessageCB
import com.ort.dbflute.exbhv.MessageBhv
import com.ort.dbflute.exbhv.MessageSendtoBhv
import com.ort.dbflute.exentity.MessageSendto
import org.dbflute.cbean.result.PagingResultBean
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.regex.Pattern
import com.ort.dbflute.exentity.Message as DbMessage

@Repository
class MessageDataSource(
    private val messageBhv: MessageBhv,
    private val messageSendtoBhv: MessageSendtoBhv
) : MessageRepository {

    companion object {
        val patternMessageTypeMap = mapOf(
            Pattern.compile("^(?![\\+=\\?@\\-\\*a_])(\\d{1,5})") to CDef.MessageType.通常発言,
            Pattern.compile("^\\+(\\d{1,5})") to CDef.MessageType.死者の呻き,
            Pattern.compile("^=(\\d{1,5})") to CDef.MessageType.共鳴発言,
            Pattern.compile("^\\?(\\d{1,5})") to CDef.MessageType.恋人発言,
            Pattern.compile("^@(\\d{1,5})") to CDef.MessageType.見学発言,
            Pattern.compile("^-(\\d{1,5})") to CDef.MessageType.独り言,
            Pattern.compile("^\\*(\\d{1,5})") to CDef.MessageType.人狼の囁き,
            Pattern.compile("^a(\\d{1,5})") to CDef.MessageType.アクション,
            Pattern.compile("^_(\\d{1,5})") to CDef.MessageType.念話
        )
    }

    override fun findMessages(
        village: Village,
        myself: VillageParticipant?,
        query: MessageQuery
    ): Messages {
        if (query.messageTypeList.isEmpty() && !query.includeMonologue && !query.includeSecret && !query.includePrivateAbility) {
            return Messages(listOf())
        }
        val messagePage = messageBhv.selectPage {
            queryPaging(it, query)
            queryMessage(it, query, myself)
            if (query.isDispLatest) {
                it.query().addOrderBy_MessageDatetime_Desc()
                it.query().addOrderBy_MessageId_Desc()
            } else {
                it.query().addOrderBy_MessageDatetime_Asc()
                it.query().addOrderBy_MessageId_Asc()
            }
        }
        messageBhv.load(messagePage) { it.loadMessageSendto { } }
        return if (query.isDispLatest) mapMessagesWithLatest(village, messagePage)
        else mapMessagesWithPaging(village, messagePage)
    }

    override fun findMessage(
        village: Village,
        messageType: CDef.MessageType,
        messageNumber: Int
    ): Message? {
        val optMessage = messageBhv.selectEntity {
            it.query().setVillageId_Equal(village.id)
            it.query().setMessageNumber_Equal(messageNumber)
            it.query().setMessageTypeCode_Equal_AsMessageType(messageType)
        }
        if (!optMessage.isPresent) return null
        val message = optMessage.get()
        messageBhv.load(message) { it.loadMessageSendto { } }
        return mapMessage(village, message)
    }

    override fun findLatestMessageDatetime(myself: VillageParticipant?, query: MessageQuery): LocalDateTime? {
        if (query.messageTypeList.isEmpty() && !query.includeMonologue && !query.includeSecret && !query.includePrivateAbility) {
            return null
        }
        val datetime = messageBhv.selectScalar(LocalDateTime::class.java).max {
            it.specify().columnMessageDatetime()
            // query
            queryMessage(it, query, myself)
        }
        return datetime.orElse(null)
    }

    override fun findParticipantDayMessageCount(
        village: Village,
        day: Int,
        participant: VillageParticipant
    ): Map<CDef.MessageType, Int> {
        val list = messageBhv.selectList {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(day)
            it.query().setVillagePlayerId_Equal(participant.id)
        }
        if (day < 3) return mapToMessageTypeCount(list)
        // 3日目以降は襲撃メッセージがあるので、襲撃メッセージを除く
        val optFirstAttackMessage = messageBhv.selectEntity {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(day)
            it.query().setMessageTypeCode_Equal_人狼の囁き()
            it.query().addOrderBy_RegisterDatetime_Asc()
            it.fetchFirst(1)
        }
        return if (!optFirstAttackMessage.isPresent) mapToMessageTypeCount(list)
        else mapToMessageTypeCount(list.filterNot { it.messageId == optFirstAttackMessage.get().messageId })
    }

    override fun registerMessage(village: Village, message: Message): Message {
        val id = insertMessage(village, message)
        return findMessageById(id, village)
    }

    private fun findMessageById(id: Int, village: Village): Message {
        val message = messageBhv.selectEntityWithDeletedCheck {
            it.query().setMessageId_Equal(id)
        }
        messageBhv.load(message) { it.loadMessageSendto { } }
        return mapMessage(village, message)
    }

    private fun mapMessagesWithPaging(village: Village, messagePage: PagingResultBean<DbMessage>): Messages =
        Messages(
            list = messagePage.map { mapMessage(village, it) },
            allPageCount = messagePage.allPageCount,
            isExistPrePage = messagePage.existsPreviousPage(),
            isExistNextPage = messagePage.existsNextPage(),
            currentPageNum = messagePage.currentPageNumber,
            isLatest = false
        )

    private fun mapMessagesWithLatest(village: Village, messagePage: PagingResultBean<DbMessage>): Messages =
        Messages(
            list = messagePage.reversed().map { mapMessage(village, it) },
            allPageCount = messagePage.allPageCount,
            isExistPrePage = messagePage.existsNextPage(), // 逆順にしているので
            isExistNextPage = false, // 最新なので次はなし
            currentPageNum = messagePage.allPageCount, // 必ず最終ページ
            isLatest = true
        )

    private fun mapMessage(village: Village, message: DbMessage): Message =
        Message(
            fromParticipantId = message.villagePlayerId,
            fromCharacterName = message.charaName?.let {
                val roomNumber = village.allParticipants().member(message.villagePlayerId!!).roomNumberWhen(message.day)
                message.name(roomNumber)
            },
            toParticipantId = message.toVillagePlayerId,
            toCharacterName = message.toCharaName?.let {
                val roomNumber =
                    village.allParticipants().member(message.toVillagePlayerId!!).roomNumberWhen(message.day)
                message.targetName(roomNumber)
            },
            time = MessageTime(
                day = message.day,
                datetime = message.messageDatetime
            ),
            content = MessageContent(
                type = MessageType(message.messageTypeCodeAsMessageType),
                num = message.messageNumber,
                text = message.messageContent,
                faceTypeCode = message.faceTypeCode,
                isConvertDisable = message.isConvertDisable
            ),
            sendToParticipantIds = message.messageSendtoList.map { it.villagePlayerId }
        )

    private fun insertMessage(village: Village, message: Message): Int {
        val m = DbMessage()
        m.villageId = village.id
        m.day = message.time.day
        message.fromParticipantId?.let {
            val participant = village.allParticipants().member(it)
            m.villagePlayerId = participant.id
            m.charaName = participant.charaName.name
            m.charaShortName = participant.charaName.shortName
        }
        message.toParticipantId?.let {
            val participant = village.allParticipants().member(it)
            m.toVillagePlayerId = participant.id
            m.toCharaName = participant.charaName.name
            m.toCharaShortName = participant.charaName.shortName
        }
        m.messageTypeCodeAsMessageType = message.content.type.toCdef()
        m.messageContent = message.content.text
        m.messageDatetime = LocalDateTime.now()
        m.isConvertDisable = message.content.isConvertDisable
        m.faceTypeCode = message.content.faceTypeCode
        m.messageNumber = selectNextMessageNumber(village.id, message.content.type.toCdef())
        repeat(3) {
            try {
                messageBhv.insert(m)
                insertMessageSendTo(m)
                return m.messageId
            } catch (e: Exception) {
            }
        }
        throw WolfMansionBusinessException("混み合っているため発言に失敗しました")
    }

    private fun insertMessageSendTo(m: DbMessage) {
        val splitted = m.messageContent.split(">>")
        if (splitted.size <= 1) {
            return  // >>が含まれていない
        }
        splitted.forEach { str ->
            patternMessageTypeMap.forEach { (pattern: Pattern, messageType: CDef.MessageType) ->
                val matcher = pattern.matcher(str)
                if (matcher.find()) {
                    val number = matcher.group(1).toInt()
                    val optMessage =
                        messageBhv.selectEntity {
                            it.query().setVillageId_Equal(m.villageId)
                            it.query().setMessageTypeCode_Equal_AsMessageType(messageType)
                            it.query().setMessageNumber_Equal(number)
                        }
                    if (!optMessage.isPresent || optMessage.get().villagePlayerId == null) {
                        return@forEach
                    }
                    val sendTo = MessageSendto()
                    sendTo.messageId = m.messageId
                    sendTo.villagePlayerId = optMessage.get().villagePlayerId
                    val optEntity =
                        messageSendtoBhv.selectEntity {
                            it.query().setMessageId_Equal(sendTo.messageId)
                            it.query().setVillagePlayerId_Equal(sendTo.villagePlayerId)
                        }
                    if (!optEntity.isPresent) {
                        messageSendtoBhv.insert(sendTo)
                    }
                }
            }
        }
    }

    private fun selectNextMessageNumber(villageId: Int, type: CDef.MessageType): Int {
        val maxNessageNumber = messageBhv.selectScalar(Int::class.java).max {
            it.specify().columnMessageNumber()
            it.query().setVillageId_Equal(villageId)
            it.query().setMessageTypeCode_Equal_AsMessageType(type)
        }.orElse(0)
        return maxNessageNumber + 1
    }

    private fun queryPaging(cb: MessageCB, query: MessageQuery) {
        when {
            !query.isPaging -> cb.paging(100000, 1)
            query.isDispLatest -> cb.paging(query.pageSize!!, 1)
            query.pageNum != null && query.pageSize != null -> cb.paging(query.pageSize, query.pageNum)
            query.pageSize != null -> cb.paging(query.pageSize, 10000) // 存在しないページを検索して最新を取得させる
            else -> cb.paging(100000, 1)
        }
    }

    private fun queryMessage(
        cb: MessageCB,
        query: MessageQuery,
        myself: VillageParticipant?
    ) {
        cb.query().setVillageId_Equal(query.village.id)
        cb.query().setDay_Equal(query.day)
        queryMessageType(cb, query, myself)
        // 参加者で絞る
        if (query.participantIds.isNotEmpty()) {
            cb.orScopeQuery { orCB ->
                orCB.query().setVillagePlayerId_InScope(query.participantIds)
                orCB.query().setVillagePlayerId_IsNull()
            }
        }
        // キーワードで絞る
        if (!query.keywords.isNullOrEmpty()) {
            cb.query().setMessageContent_LikeSearch(query.keywords) { op ->
                op.splitByBlank().likeContain().asOrSplit()
            }
        }
        query.toParticipantId?.let { toParticipantId ->
            cb.orScopeQuery { orCB ->
                orCB.query().existsMessageSendto { sendToCB ->
                    sendToCB.query().setVillagePlayerId_Equal(toParticipantId)
                }
                orCB.query().setToVillagePlayerId_Equal(toParticipantId)
            }
        }
    }

    private fun queryMessageType(
        cb: MessageCB,
        query: MessageQuery,
        myself: VillageParticipant?
    ) {
        if (myself == null) {
            if (query.messageTypeList.isNotEmpty()) {
                cb.query().setMessageTypeCode_InScope_AsMessageType(query.messageTypeList.map { it.toCdef() })
            }
            return
        }
        if (query.messageTypeList.isNotEmpty()) {
            if (query.includeMonologue || query.includeSecret || query.includePrivateAbility) {
                cb.orScopeQuery { orCB ->
                    orCB.query().setMessageTypeCode_InScope_AsMessageType(query.messageTypeList.map { it.toCdef() })
                    queryMyself(orCB, query, myself)
                }
            } else {
                cb.query().setMessageTypeCode_InScope_AsMessageType(query.messageTypeList.map { it.toCdef() })
            }
        } else {
            if (query.includeMonologue || query.includeSecret || query.includePrivateAbility) {
                cb.orScopeQuery { orCB -> queryMyself(orCB, query, myself) }
            }
        }
    }

    private fun queryMyself(cb: MessageCB, query: MessageQuery, myself: VillageParticipant) {
        if (query.includeMonologue) {
            cb.orScopeQueryAndPart { andCB -> queryMyMonologue(andCB, myself.id) }
        }
        if (query.includeSecret) {
            cb.orScopeQueryAndPart { andCB -> queryMySecret(andCB, myself.id) }
            cb.orScopeQueryAndPart { andCB -> querySecretToMe(andCB, myself.id) }
        }
        if (query.includePrivateAbility) {
            cb.orScopeQueryAndPart { andCB -> queryPrivateAbility(andCB, myself.id) }
        }
    }

    private fun queryMyMonologue(cb: MessageCB, id: Int) {
        cb.query().setVillagePlayerId_Equal(id)
        cb.query().setMessageTypeCode_Equal_独り言()
    }

    private fun queryMySecret(cb: MessageCB, id: Int) {
        cb.query().setVillagePlayerId_Equal(id)
        cb.query().setMessageTypeCode_Equal_秘話()
    }

    private fun querySecretToMe(cb: MessageCB, id: Int) {
        cb.query().setToVillagePlayerId_Equal(id)
        cb.query().setMessageTypeCode_Equal_秘話()
    }

    private fun queryPrivateAbility(cb: MessageCB, id: Int) {
        cb.query().setVillagePlayerId_Equal(id)
        cb.query().setMessageTypeCode_InScope_AsMessageType(MessageType.personalSystemTypeList)
    }

    private fun mapToMessageTypeCount(list: List<DbMessage>): Map<CDef.MessageType, Int> =
        list.groupBy { it.messageTypeCodeAsMessageType }.mapValues { it.value.size }
}
