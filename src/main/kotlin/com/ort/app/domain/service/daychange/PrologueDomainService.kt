package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.MessageContent
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.domain.service.ability.AbilityDomainService
import com.ort.app.domain.service.room.RoomDomainService
import com.ort.app.domain.service.skill.SkillAssignDomainService
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PrologueDomainService(
    private val messageDomainService: MessageDomainService,
    private val skillAssignDomainService: SkillAssignDomainService,
    private val roomDomainService: RoomDomainService,
    private val abilityDomainService: AbilityDomainService
) {

    fun leaveParticipantIfNeeded(daychange: Daychange): Daychange {
        // 24時間アクセスしていない人を退村させる
        val threshold = LocalDateTime.now().minusDays(1L)
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        village.allParticipants()
            .filterNotDummy(village.dummyParticipant())
            .list.filter {
                it.lastAccessDatetime.isBefore(threshold)
            }.forEach {
                village = village.leaveParticipant(it.id)
                messages = messages.add(messageDomainService.createLeaveMessage(it))
            }

        return daychange.copy(
            village = village,
            messages = messages
        )
    }

    fun cancelOrExtendPrologueIfNeeded(daychange: Daychange): Daychange {
        val village = daychange.village
        if (!shouldChangeDay(village)) return daychange
        if (village.setting.personMin <= village.participants.list.size) return daychange

        // 一人でも参加していたら延長、なかったら廃村
        return if (village.participants.list.size > 1) {
            daychange.copy(
                village = village.extendPrologue(),
                messages = daychange.messages.add(messageDomainService.createExtendPrologueMessage())
            )
        } else {
            daychange.copy(village = village.cancel())
        }
    }

    fun changeDayIfNeeded(daychange: Daychange, charas: Charas): Daychange {
        if (!shouldChangeDay(daychange.village)) return daychange
        return startVillage(daychange.copy(village = daychange.village.addNewDay()), charas)
    }

    private fun shouldChangeDay(village: Village): Boolean =
        !LocalDateTime.now().isBefore(village.days.latestDay().dayChangeDatetime)

    private fun startVillage(beforeDaychange: Daychange, charas: Charas): Daychange {
        var daychange = beforeDaychange.copy()
        // 開始メッセージ
        daychange = daychange.copy(messages = daychange.messages.add(messageDomainService.createVillageStartMessage()))
        // 役職割り当て
        daychange = skillAssignDomainService.assign(daychange)
        // 部屋割り当て
        daychange = roomDomainService.assign(daychange)
        // 自動設定変更
        daychange = updateVillageSettingsIfNeeded(daychange)
        // 絶対人狼や梟のメッセージ
        daychange = abilityDomainService.addOpenSkillMessages(daychange)
        // 村ステータス更新
        daychange = daychange.copy(village = daychange.village.toProgress())
        // 投票、能力行使のデフォルト設定
        daychange = abilityDomainService.addDefaultAbilities(daychange)
        // ダミーキャラの発言
        daychange = addDummyCharaMessage(daychange, charas)
        // ツイート
        daychange = addStartTweet(daychange)

        return daychange
    }

    private fun updateVillageSettingsIfNeeded(daychange: Daychange): Daychange {
        var village = daychange.village.copy()
        var messages = daychange.messages.copy()

        // 襲撃役が2名以下の場合は連続襲撃ありにする
        if (!village.setting.rule.isAvailableSameWolfAttack && village.participants.list.count { it.skill!!.hasAttackAbility() } < 3) {
            village = village.copy(
                setting = village.setting.copy(
                    rule = village.setting.rule.copy(
                        isAvailableSameWolfAttack = true
                    )
                )
            )
            messages = messages.add(
                Message.ofSystemMessage(
                    day = village.latestDay(),
                    message = "人狼の人数が3名より少ないため、同一人狼による連続襲撃を「可能」に変更します。"
                )
            )
        }

        return daychange.copy(village = village, messages = messages)
    }

    private fun addDummyCharaMessage(daychange: Daychange, charas: Charas): Daychange {
        val dummyChara = charas.chara(daychange.village.dummyParticipant().charaId)
        if (dummyChara.defaultFirstdayMessage.isNullOrEmpty()) return daychange
        return daychange.copy(
            messages = daychange.messages.add(
                messageDomainService.createSayMessage(
                    village = daychange.village,
                    myself = daychange.village.dummyParticipant(),
                    target = null,
                    messageContent = MessageContent.invoke(
                        messageType = CDef.MessageType.通常発言.code(),
                        text = dummyChara.defaultFirstdayMessage,
                        faceCode = CDef.FaceType.通常.code(),
                        isConvertDisable = true
                    )
                )
            )
        )
    }

    private fun addStartTweet(daychange: Daychange): Daychange {
        if (!daychange.village.setting.joinPassword.isNullOrEmpty()) return daychange
        return daychange.copy(tweets = daychange.tweets + "${daychange.village.name}が開始されました。")
    }
}