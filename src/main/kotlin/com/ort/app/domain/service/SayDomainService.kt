package com.ort.app.domain.service

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.chara.FaceType
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.situation.participant.ParticipantSayMessageTypeSituation
import com.ort.app.domain.model.situation.participant.ParticipantSayRestrictSituation
import com.ort.app.domain.model.situation.participant.ParticipantSaySituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.setting.SayRestriction
import com.ort.app.domain.service.message.say.*
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class SayDomainService(
    private val normalSayDomainService: NormalSayDomainService,
    private val graveSayDomainService: GraveSayDomainService,
    private val monologueSayDomainService: MonologueSayDomainService,
    private val spectateSayDomainService: SpectateSayDomainService,
    private val werewolfSayDomainService: WerewolfSayDomainService,
    private val sympathizeSayDomainService: SympathizeSayDomainService,
    private val telepathyDomainService: TelepathyDomainService,
    private val loversSayDomainService: LoversSayDomainService,
    private val secretSayDomainService: SecretSayDomainService,
    private val actionSayDomainService: ActionSayDomainService,
) {

    private val defaultMessageTypeOrder = listOf(
        CDef.MessageType.恋人発言,
        CDef.MessageType.念話,
        CDef.MessageType.人狼の囁き,
        CDef.MessageType.共鳴発言,
        CDef.MessageType.独り言,
        CDef.MessageType.通常発言,
        CDef.MessageType.死者の呻き,
        CDef.MessageType.見学発言
    )

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        charachips: Charachips,
        day: Int,
        latestDayMessageCountMap: Map<CDef.MessageType, Int>?,
        creatorPlayerId: Int
    ): ParticipantSaySituation {
        val selectableMessageTypeList =
            getSelectableMessageTypeList(village, myself, player, latestDayMessageCountMap, day, creatorPlayerId)
        return ParticipantSaySituation(
            isAvailableSay = isAvailableSay(village, myself, day),
            selectableMessageTypeList = selectableMessageTypeList,
            selectableFaceTypeList = getSelectableFaceTypeList(myself, charachips),
            defaultMessageType = detectDefaultMessageType(
                isAvailableSay(village, myself, day),
                selectableMessageTypeList
            )
        )
    }

    fun assertSay(
        village: Village,
        myself: VillageParticipant,
        player: Player,
        chara: Chara,
        latestDayMessageCountMap: Map<CDef.MessageType, Int>,
        messageContent: MessageContent
    ) {
        if (!isAvailableSay(village, myself, village.latestDay())) throw WolfMansionBusinessException("発言できません")
        // 発言種別ごとのチェック
        if (!detectSayTypeService(messageContent.type.toCdef()).isSayable(village, myself, player)) {
            throw WolfMansionBusinessException("不正な発言種別です")
        }
        // 表情種別チェック
        if (!isSelectableFaceType(chara, messageContent)) throw WolfMansionBusinessException("不正な表情種別です")
        // 発言回数、長さ、行数チェック
        assertMessageRestrict(village, myself, messageContent, latestDayMessageCountMap)
    }

    private fun isAvailableSay(village: Village, myself: VillageParticipant?, day: Int): Boolean {
        myself ?: return false
        return village.canSay(day) && myself.isAvailableSay(village.status.isEpilogue())
    }

    private fun getSelectableMessageTypeList(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        latestDayMessageCountMap: Map<CDef.MessageType, Int>?,
        day: Int,
        creatorPlayerId: Int
    ): List<ParticipantSayMessageTypeSituation> {
        if (!isAvailableSay(village, myself, day)) return listOf()
        return MessageType.sayTypeList
            .filter { detectSayTypeService(it).isSayable(village, myself!!, player) }
            .map {
                val restrict =
                    if (village.status.isProgress()) village.setting.sayRestriction.restrict(myself!!, it)
                    else null
                ParticipantSayMessageTypeSituation(
                    messageType = MessageType(it),
                    restrict = ParticipantSayRestrictSituation(
                        isRestricted = restrict != null,
                        maxCount = restrict?.count,
                        remainingCount = restrict?.remainingCount(
                            village.status.toCdef(),
                            latestDayMessageCountMap
                        ),
                        maxLength = restrict?.length ?: MessageContent.defaultLengthMax,
                        maxLine = MessageContent.defaultLineMax
                    ),
                    targetList = getSecretSayTargetList(it, village, creatorPlayerId, myself!!, player!!)
                )
            }
    }

    private fun getSelectableFaceTypeList(myself: VillageParticipant?, charachips: Charachips): List<FaceType> {
        myself ?: return listOf()
        return charachips.list
            .flatMap { it.charas.list }
            .first { it.id == myself.charaId }.images.list
            .filter { it.isDisplay }
            .map { it.faceType }
    }

    private fun detectDefaultMessageType(
        availableSay: Boolean,
        selectableMessageTypeList: List<ParticipantSayMessageTypeSituation>
    ): MessageType? {
        if (!availableSay || selectableMessageTypeList.isEmpty()) return null
        val selectableMessageTypeCdefList = selectableMessageTypeList.map { it.messageType.toCdef() }

        return defaultMessageTypeOrder.firstOrNull { cdefMessageType ->
            selectableMessageTypeCdefList.contains(cdefMessageType)
        }?.let { MessageType(it) }
    }

    fun detectSayTypeService(type: CDef.MessageType): SayTypeDomainService {
        return when (type) {
            CDef.MessageType.通常発言 -> normalSayDomainService
            CDef.MessageType.共鳴発言 -> sympathizeSayDomainService
            CDef.MessageType.人狼の囁き -> werewolfSayDomainService
            CDef.MessageType.死者の呻き -> graveSayDomainService
            CDef.MessageType.独り言 -> monologueSayDomainService
            CDef.MessageType.見学発言 -> spectateSayDomainService
            CDef.MessageType.恋人発言 -> loversSayDomainService
            CDef.MessageType.念話 -> telepathyDomainService
            CDef.MessageType.秘話 -> secretSayDomainService
            CDef.MessageType.アクション -> actionSayDomainService
            else -> throw IllegalStateException("service not found.")
        }
    }

    private fun getSecretSayTargetList(
        messageType: CDef.MessageType,
        village: Village,
        creatorPlayerId: Int,
        myself: VillageParticipant,
        player: Player,
    ): List<VillageParticipant> {
        if (messageType != CDef.MessageType.秘話) return emptyList()
        val base = village.allParticipants().sortedByRoomNumber().list.filter { it.id != myself.id }
        // 管理者および村建ては全員
        return if (myself.isAdmin() || village.isCreator(player)) base
        // 全員秘話可能
        else if (village.setting.rule.secretSayRange.canSayAll()) base
        // 村建てとのみ
        else if (village.setting.rule.secretSayRange.canOnlyCreator()) base.filter { it.playerId == creatorPlayerId }
        // 秘話なし
        else emptyList()
    }

    private fun isSelectableFaceType(chara: Chara, messageContent: MessageContent): Boolean =
        // アクションは表情不要
        if (messageContent.type.toCdef() == CDef.MessageType.アクション) true
        else chara.images.findByFaceType(messageContent.faceTypeCode!!) != null

    private fun assertMessageRestrict(
        village: Village,
        myself: VillageParticipant,
        messageContent: MessageContent,
        latestDayMessageCountMap: Map<CDef.MessageType, Int>
    ) {
        if (!village.status.isProgress()) return
        if (myself.isAdmin()) return
        val restrict = village.findRestrict(myself, messageContent.type) ?: return
        // 回数
        if (remainingCount(
                village,
                restrict,
                latestDayMessageCountMap
            ) <= 0
        ) throw WolfMansionBusinessException("発言残り回数が0回です")
        // 行数
        val text = messageContent.text.replace("\r\n", "\n")
        val lineMax = if (messageContent.type.toCdef() == CDef.MessageType.村建て発言) 40 else 20
        if (text.split("\n").size > lineMax) throw WolfMansionBusinessException("行数オーバーです")
        // 文字数
        if (text.length !in 1..restrict.length) {
            throw WolfMansionBusinessException("1文字以上${restrict.length}文字以下である必要があります")
        }
    }

    private fun remainingCount(
        village: Village,
        restrict: SayRestriction.Restriction,
        latestDayMessageCountMap: Map<CDef.MessageType, Int>
    ): Int {
        if (village.status.isPrologue() || village.status.isEpilogue()) return restrict.count
        return restrict.count - latestDayMessageCountMap.getOrDefault(restrict.messageType.toCdef(), 0)
    }
}