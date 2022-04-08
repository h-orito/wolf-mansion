package com.ort.app.domain.service

import com.ort.app.domain.model.chara.FaceType
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.randomkeyword.RandomKeyword
import com.ort.app.domain.model.randomkeyword.RandomKeywords
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.service.message.say.MessageTypeDomainService
import com.ort.app.domain.service.message.system.AttackMessageDomainService
import com.ort.app.domain.service.message.system.CoronerMessageDomainService
import com.ort.app.domain.service.message.system.DivineMessageDomainService
import com.ort.app.domain.service.message.system.FoxMessageDomainService
import com.ort.app.domain.service.message.system.GuruMessageDomainService
import com.ort.app.domain.service.message.system.InvestigateMessageDomainService
import com.ort.app.domain.service.message.system.LoversMessageDomainService
import com.ort.app.domain.service.message.system.PrivateAbilityMessageDomainService
import com.ort.app.domain.service.message.system.PrivateSystemMessageDomainService
import com.ort.app.domain.service.message.system.PsychicMessageDomainService
import com.ort.app.domain.service.message.system.WiseMessageDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Component
import java.util.*
import java.util.regex.Pattern

@Component
class MessageDomainService(
    private val sayDomainService: SayDomainService,
    private val psychicMessageDomainService: PsychicMessageDomainService,
    private val guruMessageDomainService: GuruMessageDomainService,
    private val attackMessageDomainService: AttackMessageDomainService,
    private val coronerMessageDomainService: CoronerMessageDomainService,
    private val divineMessageDomainService: DivineMessageDomainService,
    private val wiseMessageDomainService: WiseMessageDomainService,
    private val investigateMessageDomainService: InvestigateMessageDomainService,
    private val loversMessageDomainService: LoversMessageDomainService,
    private val foxMessageDomainService: FoxMessageDomainService,
    private val privateAbilityMessageDomainService: PrivateAbilityMessageDomainService,
    private val privateSystemMessageDomainService: PrivateSystemMessageDomainService
) {

    companion object {
        const val diceRegex = "\\[\\[(\\d)d(\\d{1,5})$"
        const val fortuneRegex = "\\[\\[fortune$"
        const val orRegex = "(?!\\[\\[fortune)\\[\\[([^\\]]*or.*?)$"
        const val whoRegex = "(?!\\[\\[allwho)(\\[\\[who)$"
        const val allwhoRegex = "\\[\\[allwho$"
    }

    fun setViewableQuery(village: Village, myself: VillageParticipant?, query: MessageQuery) {
        val availableMessageTypes = getViewableMessageTypeList(village, myself, query)
        query.setAvailable(availableMessageTypes, myself, village)
    }

    fun replaceRandomMessageIfNeeded(
        message: Message,
        participants: VillageParticipants,
        randomKeywords: RandomKeywords
    ): Message {
        if (message.content.isConvertDisable) return message
        val text = message.content.text
        val replacedText = text.split("\n").map { line ->
            line.split("]]").map { m ->
                var replaced = replaceDiceMessage(m)
                replaced = replaceFortuneMessage(replaced)
                replaced = replaceOrMessage(replaced)
                replaced = replaceWhoMessage(replaced, participants)
                replaced = replaceAllwhoMessage(replaced, participants)
                randomKeywords.list.forEach { randomKeyword ->
                    replaced = replaceUserDefinedRandomMessage(replaced, randomKeyword)
                }
                replaced
            }.joinToString("]]")
        }.joinToString(separator = "\n")

        return message.copy(content = message.content.copy(text = replacedText))
    }

    fun createLeaveMessage(participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = 0,
            message = "${participant.charaName.name}は村を去った。"
        )
    }

    fun createChangeNameMessage(day: Int, before: VillageParticipant, after: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = day,
            message = "名前を変更しました。\n${before.name()} → ${after.name()}"
        )
    }

    fun createSkillRequestMessage(participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = 0,
            messageType = MessageType(CDef.MessageType.非公開システムメッセージ),
            message = "${participant.name()}が第一希望役職を${participant.requestSkill!!.first.name}に、" +
                    "第二希望役職を${participant.requestSkill.second.name}に設定しました。"
        )
    }

    fun createAutoChangeRequestSkillMessage(
        before: VillageParticipant,
        after: VillageParticipant
    ): Message {
        val from = "${before.requestSkill!!.first.name}/${before.requestSkill.second.name}"
        val to = "${after.requestSkill!!.first.name}/${after.requestSkill.second.name}"
        return Message.ofSystemMessage(
            day = 1,
            messageType = MessageType(CDef.MessageType.非公開システムメッセージ),
            message = "${after.name()}の役職希望を自動変更しました。\n$from → $to"
        )
    }

    fun createOrganizationMessage(
        village: Village,
        text: String
    ): Message {
        val type =
            if (village.setting.rule.isRandomOrganization) MessageType(CDef.MessageType.非公開システムメッセージ) else MessageType(
                CDef.MessageType.公開システムメッセージ
            )
        return Message.ofSystemMessage(
            day = 1,
            messageType = type,
            message = text
        )
    }

    fun createSkillAssignedMessage(village: Village): Message {
        val message = village.participants.list.joinToString(separator = "\n") {
            "${it.name()}の役職は${it.skill!!.name}になりました。（希望役職：${it.requestSkill!!.first.name}/${it.requestSkill.second.name}）"
        }
        return Message.ofSystemMessage(
            day = 1,
            messageType = MessageType(CDef.MessageType.非公開システムメッセージ),
            message = message
        )
    }

    fun createRoomAssignedMessage(village: Village): Message {
        val message = village.participants.sortedByRoomNumber().list.joinToString(
            prefix = "人狼の痕跡を残すため、村人たちはそれぞれ別の部屋で夜を明かすことにした。\n\n",
            separator = "\n"
        ) { "${it.name()}には、部屋番号${it.room!!.number}が割り当てられた。" }
        return Message.ofSystemMessage(
            day = 1,
            message = message
        )
    }

    fun createRoomReAssignedMessage(village: Village, participant: VillageParticipant): Message {
        val message = village.participants.sortedByRoomNumber().list.joinToString(
            prefix = """
                ${participant.name()}は突然「フルーツバスケット！」と叫んだ。
                なんと、全員の部屋がシャッフルされてしまった。
                
            """.trimIndent(),
            separator = "\n"
        ) { "${it.name()}は、部屋番号${it.room!!.number}に移動した。" }
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = message
        )
    }

    fun createOpenSkillMessage(village: Village, text: String): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text
        )
    }

    fun createCreatorMessage(village: Village, text: String, isConvertDisable: Boolean): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text,
            messageType = MessageType(CDef.MessageType.村建て発言),
            isConvertDisable = isConvertDisable
        )
    }

    fun createModifySettingMessage(): Message {
        return Message.ofSystemMessage(
            day = 0,
            message = "村の設定が変更されました。"
        )
    }

    fun createExtendPrologueMessage(): Message {
        return Message.ofSystemMessage(
            day = 0,
            message = "まだ村人たちは揃っていないようだ。"
        )
    }

    fun createVillageStartMessage(): Message {
        return Message.ofSystemMessage(
            day = 1,
            message = """
                さあ、自らの姿を鏡に映してみよう。
                そこに映るのはただの村人か、それとも血に飢えた人狼か。
                
                例え人狼でも、多人数で立ち向かえば怖くはない。
                問題は、だれが人狼なのかという事だ。
                占い師の能力を持つ人間ならば、それを見破れるだろう。                
            """.trimIndent()
        )
    }

    fun createParticipateSystemMessage(
        village: Village,
        participant: VillageParticipant,
        spectator: Boolean
    ): Message {
        val message = if (spectator) {
            "（見学）${village.spectators.count}人目、${participant.name()}。"
        } else {
            "${village.participants.count}人目、${participant.name()}。"
        }
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = message
        )
    }

    fun createAbilitySetMessage(village: Village, text: String, messageType: MessageType): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text,
            messageType = messageType
        )
    }

    // 個人ごとの能力行使結果
    fun createPrivateAbilityMessage(
        village: Village,
        myself: VillageParticipant,
        text: String,
        messageType: MessageType
    ): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text,
            messageType = messageType,
            participantId = myself.id
        )
    }

    // 個人ごとでない能力行使結果
    fun createPublicAbilityMessage(village: Village, text: String, messageType: MessageType): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text,
            messageType = messageType
        )
    }

    fun createSuddenlyDeathMessage(village: Village, participant: VillageParticipant): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${participant.name()}は突然死した。"
        )
    }

    fun createEachVoteMessage(village: Village, text: String, messageType: MessageType): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text,
            messageType = messageType
        )
    }

    fun createExecuteMessage(village: Village, text: String, messageType: MessageType): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text,
            messageType = messageType
        )
    }

    fun createMiserableMessage(village: Village, text: String): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = text
        )
    }

    fun createAttackMessage(
        village: Village,
        attacker: VillageParticipant,
        text: String,
        faceType: FaceType,
        messageType: MessageType
    ): Message {
        return createSayMessage(
            village = village,
            myself = attacker,
            target = null,
            messageContent = MessageContent.invoke(
                messageType = messageType.code,
                text = text,
                faceCode = faceType.code,
                isConvertDisable = true
            )
        )
    }

    fun createSayMessages(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant?,
        messageContent: MessageContent,
        shouldDakuten: Boolean = false
    ): Message {
        return Message.ofSayMessage(
            day = village.latestDay(),
            participantId = myself.id,
            targetParticipantId = target?.id,
            messageContent = if (shouldDakuten) {
                messageContent.copy(
                    text = messageContent.text.map { "${it}゛" }
                        .joinToString(separator = "", prefix = "[[large]][[b]]", postfix = "[[/b]][[/large]]")
                )
            } else messageContent
        )
    }

    fun createSayMessage(
        village: Village,
        myself: VillageParticipant,
        target: VillageParticipant?,
        messageContent: MessageContent,
        shouldDakuten: Boolean = false
    ): Message {
        return Message.ofSayMessage(
            day = village.latestDay(),
            participantId = myself.id,
            targetParticipantId = target?.id,
            messageContent = if (shouldDakuten) {
                messageContent.copy(
                    text = messageContent.text.map { "${it}゛" }
                        .joinToString(separator = "", prefix = "[[large]][[b]]", postfix = "[[/b]][[/large]]")
                )
            } else messageContent
        )
    }

    fun createJoinMessage(
        village: Village,
        participant: VillageParticipant,
        spectator: Boolean,
        joinMessage: String
    ): Message {
        return Message.ofSayMessage(
            day = village.latestDay(),
            participantId = participant.id,
            messageContent = MessageContent.invoke(
                messageType = if (spectator) CDef.MessageType.見学発言.code() else CDef.MessageType.通常発言.code(),
                text = joinMessage,
                faceCode = CDef.FaceType.通常.code(),
                isConvertDisable = false
            )
        )
    }

    // [[2d6]]の変換
    private fun replaceDiceMessage(mes: String): String {
        val diceMatcher = Pattern.compile(diceRegex).matcher(mes)
        if (!diceMatcher.find()) return mes
        // Randomクラスのインスタンス化
        val rnd = Random()
        val diceNum = diceMatcher.group(1).toInt()
        val diceSize = diceMatcher.group(2).toInt()
        var diceStr = ""
        repeat(diceNum) {
            val num = if (diceSize <= 0) 0 else rnd.nextInt(diceSize) + 1
            diceStr += "($num)"
        }
        return mes.replaceFirst(diceRegex.toRegex(), diceStr + diceMatcher.group(0))
    }

    // [[fortune]]の変換
    private fun replaceFortuneMessage(mes: String): String {
        val fortuneMatcher = Pattern.compile(fortuneRegex).matcher(mes)
        if (!fortuneMatcher.find()) return mes
        // Randomクラスのインスタンス化
        val rnd = Random()
        return mes.replace(fortuneRegex.toRegex(), rnd.nextInt(101).toString() + fortuneMatcher.group(0))
    }

    // [[AorBorC]]の変換
    private fun replaceOrMessage(mes: String): String {
        val orMatcher = Pattern.compile(orRegex).matcher(mes)
        if (!orMatcher.find()) return mes
        val matchString = orMatcher.group(1)
        val selected = matchString.split("or").random()
        return mes.replace(orRegex.toRegex(), selected + orMatcher.group(0))
    }

    // [[who]]の変換
    private fun replaceWhoMessage(mes: String, participants: VillageParticipants): String {
        val whoMatcher = Pattern.compile(whoRegex).matcher(mes)
        if (!whoMatcher.find()) return mes
        val selected = participants.filterNotSpectator().filterAlive().list.random()
        return mes.replace(whoRegex.toRegex(), selected.charaName.name + whoMatcher.group(0))
    }

    // [[allwho]]の変換
    private fun replaceAllwhoMessage(mes: String, participants: VillageParticipants): String {
        val allwhoMatcher = Pattern.compile(allwhoRegex).matcher(mes)
        if (!allwhoMatcher.find()) return mes
        val selected = participants.list.random()
        return mes.replace(allwhoRegex.toRegex(), selected.charaName.name + allwhoMatcher.group(0))
    }

    // ユーザ定義キーワードの変換
    private fun replaceUserDefinedRandomMessage(mes: String, keyword: RandomKeyword): String {
        val regex = "\\[\\[${keyword.keyword}$"
        val matcher = Pattern.compile(regex).matcher(mes)
        if (!matcher.find()) return mes
        return mes.replace(regex.toRegex(), keyword.contents.random().message + matcher.group(0))
    }

    private fun getViewableMessageTypeList(
        village: Village,
        myself: VillageParticipant?,
        query: MessageQuery
    ): List<MessageType> {
        // 村が終了していたり管理人だったら全て見られる
        if (myself?.isAdmin() == true
            || village.status.isSettled()
            || village.status.isCanceled()
        ) {
            return CDef.MessageType.listAll().map { it.toModel() }
        }
        val list = MessageType.everyoneViewableTypeList.toMutableList()
        // 梟は追加で見られる
        if (myself?.skill?.toCdef() == CDef.Skill.梟) {
            list += MessageType.owlViewableSayTypeList
        }
        // あとは権限に応じて追加
        list += (MessageType.sayTypeList + MessageType.commonSystemTypeList).filter {
            isViewable(village, myself, it, query.day)
        }
        return list.distinct().map { it.toModel() }
    }

    fun isViewable(
        village: Village,
        myself: VillageParticipant?,
        messageType: CDef.MessageType,
        day: Int
    ): Boolean {
        return if (messageType == CDef.MessageType.村建て発言) true
        else detectMessageTypeDomainService(messageType).isViewable(village, myself, day)
    }

    private fun detectMessageTypeDomainService(messageType: CDef.MessageType): MessageTypeDomainService {
        if (MessageType(messageType).isSayType()) return sayDomainService.detectSayTypeService(messageType)
        return when (messageType) {
            CDef.MessageType.白黒霊視結果 -> psychicMessageDomainService
            CDef.MessageType.役職霊視結果 -> guruMessageDomainService
            CDef.MessageType.検死結果 -> coronerMessageDomainService
            CDef.MessageType.襲撃結果 -> attackMessageDomainService
            CDef.MessageType.足音調査結果 -> investigateMessageDomainService
            CDef.MessageType.白黒占い結果 -> divineMessageDomainService
            CDef.MessageType.役職占い結果 -> wiseMessageDomainService
            CDef.MessageType.妖狐メッセージ -> foxMessageDomainService
            CDef.MessageType.恋人メッセージ -> loversMessageDomainService
            CDef.MessageType.能力行使メッセージ -> privateAbilityMessageDomainService
            CDef.MessageType.非公開システムメッセージ -> privateSystemMessageDomainService
            else -> throw IllegalStateException("service not found.")
        }
    }
}