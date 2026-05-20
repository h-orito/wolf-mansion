package com.ort.app.application.coordinator

import com.ort.app.application.service.NotificationService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.service.MessageDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class CreatorCoordinator(
    private val messageCoordinator: MessageCoordinator,
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val messageDomainService: MessageDomainService,
    private val notificationService: NotificationService
) {

    fun isCreator(userName: String?, villageId: Int): Boolean {
        userName ?: return false
        val player = playerService.findPlayer(userName) ?: return false
        return player.id == 1 ||
                (player.createProgressVillageIdList + player.createFinishedVillageIdList).contains(villageId)
    }

    fun kick(villageId: Int, charaId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        kick(village, charaId)
    }

    /** 呼び出し側で既に village を取得済みのオーバーロード (= 二重 SELECT を避ける)。 */
    fun kick(village: Village, charaId: Int) {
        val target = village.allParticipants(excludeDummy = true).chara(charaId)
        // 退村させる
        villageService.leave(target)
        // 退村メッセージを登録
        messageCoordinator.registerMessage(village.id, messageDomainService.createLeaveMessage(target))
    }

    fun say(villageId: Int, text: String, isConvertDisable: Boolean) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        say(village, text, isConvertDisable)
    }

    fun say(village: Village, text: String, isConvertDisable: Boolean) {
        val message = messageCoordinator.registerMessage(
            village.id,
            messageDomainService.createCreatorMessage(village, text, isConvertDisable)
        )
        // notification
        val players = playerService.findPlayers(village.id)
        notificationService.notifyReceiveMessageToCustomerIfNeeded(village, players, message)
    }

    fun cancel(villageId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        cancel(village)
    }

    fun cancel(village: Village) {
        if (!village.canCancel()) throw WolfMansionBusinessException("プロローグ中でなければ廃村できません")
        villageService.cancel(village.id)
    }

    fun extendEpilogue(villageId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        extendEpilogue(village)
    }

    fun extendEpilogue(village: Village) {
        if (!village.canExtendEpilogue()) throw WolfMansionBusinessException("エピローグ中でなければ延長できません")
        // エピローグを1日延長する
        villageService.extendDay(
            village.id,
            village.days.latestDay().day,
            village.days.latestDay().dayChangeDatetime.plusDays(1L)
        )
    }

    fun shortenEpilogue(villageId: Int) {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        shortenEpilogue(village)
    }

    fun shortenEpilogue(village: Village) {
        if (!village.canShortenEpilogue()) throw WolfMansionBusinessException("エピローグ中でなければ短縮できません")
        // エピローグを1日短縮する
        villageService.shortenDay(
            village.id,
            village.days.latestDay().day,
            village.days.latestDay().dayChangeDatetime.minusDays(1L)
        )
    }

    fun saveSettings(village: Village) {
        // 設定変更
        village.assertModifySetting()
        villageService.updateSetting(village)
        // 設定変更メッセージ登録
        messageCoordinator.registerMessage(village.id, messageDomainService.createModifySettingMessage())
    }
}