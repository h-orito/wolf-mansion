package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.message.MessageRepository
import com.ort.app.domain.model.message.MessageTime
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.message.Messages
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
            Pattern.compile("^(?![\\+=\\?@\\-\\*a])(\\d{1,5})") to CDef.MessageType.通常発言,
            Pattern.compile("\\+(\\d{1,5})") to CDef.MessageType.死者の呻き,
            Pattern.compile("=(\\d{1,5})") to CDef.MessageType.共鳴発言,
            Pattern.compile("\\?(\\d{1,5})") to CDef.MessageType.恋人発言,
            Pattern.compile("@(\\d{1,5})") to CDef.MessageType.見学発言,
            Pattern.compile("\\-(\\d{1,5})") to CDef.MessageType.独り言,
            Pattern.compile("\\*(\\d{1,5})") to CDef.MessageType.人狼の囁き,
            Pattern.compile("a(\\d{1,5})") to CDef.MessageType.アクション
        )
    }

    override fun findMessages(
        village: Village,
        myself: VillageParticipant?,
        query: MessageQuery
    ): Messages {
        val messagePage = messageBhv.selectPage {
            // paging
            if (query.pageNum != null && query.pageSize != null) {
                it.paging(query.pageSize, query.pageNum)
            } else if (query.pageSize != null) {
                it.paging(query.pageSize, 10000) // 存在しないページを検索して最新を取得させる
            } else {
                it.paging(100000, 1)
            }
            // query
            queryMessage(it, query, myself)
            it.query().addOrderBy_MessageDatetime_Asc()
            it.query().addOrderBy_MessageId_Asc()
        }
        return mapMessagesWithPaging(village, messagePage)
    }

    override fun findMessage(
        village: Village,
        messageType: CDef.MessageType,
        messageNumber: Int
    ): Message? {
        return messageBhv.selectEntity {
            it.query().setVillageId_Equal(village.id)
            it.query().setMessageNumber_Equal(messageNumber)
            it.query().setMessageTypeCode_Equal_AsMessageType(messageType)
        }.map { mapMessage(village, it) }.orElse(null)
    }

    override fun findLatestMessageDatetime(myself: VillageParticipant?, query: MessageQuery): LocalDateTime? {
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

    override fun registerMessage(village: Village, message: Message) = insertMessage(village, message)

    private fun mapMessagesWithPaging(village: Village, messagePage: PagingResultBean<DbMessage>): Messages =
        Messages(
            list = messagePage.map { mapMessage(village, it) },
            allPageCount = messagePage.allPageCount,
            isExistPrePage = messagePage.existsPreviousPage(),
            isExistNextPage = messagePage.existsNextPage(),
            currentPageNum = messagePage.currentPageNumber
        )

    private fun mapMessages(village: Village, list: List<DbMessage>): Messages =
        Messages(list = list.map { mapMessage(village, it) })

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
            )
        )

    private fun insertMessage(village: Village, message: Message) {
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
        m.faceTypeCodeAsFaceType = message.content.faceTypeCode?.let { CDef.FaceType.codeOf(it) }
        m.messageNumber = selectNextMessageNumber(village.id, message.content.type.toCdef())
        repeat(3) {
            try {
                messageBhv.insert(m)
                insertMessageSendTo(m)
                return
            } catch (e: Exception) {
            }
        }
        throw WolfMansionBusinessException("混み合っているため発言に失敗しました")
    }

    private fun insertMessageSendTo(m: DbMessage) {
        val splitted = m.messageContent.split("\\>\\>")
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

    private fun queryMessage(
        cb: MessageCB,
        query: MessageQuery,
        myself: VillageParticipant?
    ) {
        cb.query().setVillageId_Equal(query.village.id)
        cb.query().setDay_Equal(query.day)
        if (query.isAllViewable) {
            // 条件なし
        } else if (myself == null) {
            cb.query().setMessageTypeCode_InScope_AsMessageType(query.messageTypeList.map { it.toCdef() })
        } else {
            val personalMessageTypeList =
                query.personalMessageTypeList.map { it.toCdef() } + listOf(
                    CDef.MessageType.独り言,
                    CDef.MessageType.秘話
                )
            cb.orScopeQuery { orCB ->
                orCB.query().setMessageTypeCode_InScope_AsMessageType(query.messageTypeList.map { it.toCdef() })
                orCB.orScopeQueryAndPart { andCB ->
                    andCB.query()
                        .setMessageTypeCode_InScope_AsMessageType(personalMessageTypeList)
                    andCB.query().setVillagePlayerId_Equal(myself.id)
                }
                orCB.orScopeQueryAndPart { andCB ->
                    andCB.query().setMessageTypeCode_Equal_秘話()
                    andCB.query().setToVillagePlayerId_Equal(myself.id)
                }
            }
            if (query.onlyToMe) {
                cb.orScopeQuery { orCB ->
                    orCB.query().existsMessageSendto { sendToCB ->
                        sendToCB.query().setVillagePlayerId_Equal(myself.id)
                    }
                    orCB.query().setToVillagePlayerId_Equal(myself.id)
                }
            }
        }
    }

    private fun mapToMessageTypeCount(list: List<DbMessage>): Map<CDef.MessageType, Int> =
        list.groupBy { it.messageTypeCodeAsMessageType }.mapValues { it.value.size }
}